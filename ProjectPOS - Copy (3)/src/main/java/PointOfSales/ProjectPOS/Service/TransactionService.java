package PointOfSales.ProjectPOS.Service;

import PointOfSales.ProjectPOS.DTO.TransactionAddDTO;
import PointOfSales.ProjectPOS.DTO.TransactionDetailsDTO;
import PointOfSales.ProjectPOS.DTO.TransactionsDTO;
import PointOfSales.ProjectPOS.Entity.Products;
import PointOfSales.ProjectPOS.Entity.TransactionDetails;
import PointOfSales.ProjectPOS.Entity.Transactions;
import PointOfSales.ProjectPOS.Repository.ProductRepository;
import PointOfSales.ProjectPOS.Repository.TransactionDetailsRepository;
import PointOfSales.ProjectPOS.Repository.TransactionRepository;
import PointOfSales.ProjectPOS.Utils.ResponseMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, TransactionDetailsRepository transactionDetailsRepository, ProductRepository productRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionDetailsRepository = transactionDetailsRepository;
        this.productRepository = productRepository;
    }

    public List<TransactionsDTO> getAllTransactions() {
        List<Transactions> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ResponseMessage addTransaction(TransactionAddDTO transactionAddDTO) {
        Transactions transaction = new Transactions();
        int totalAmount = transactionAddDTO.getTotal_amount();
        int totalPay = transactionAddDTO.getTotal_pay();
        List<TransactionDetailsDTO> detailsDTOList = transactionAddDTO.getTransaction_details();
        int totalSubTotal = 0;

        if (totalPay < totalAmount) {
            throw new IllegalArgumentException("Total pay harus lebih besar atau sama dengan total amount");
        }

        for (TransactionDetailsDTO detailsDTO : detailsDTOList) {
            if (detailsDTO.getQuantity() == null || detailsDTO.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity tidak boleh null pada product Id: " + detailsDTO.getProduct_id());
            }
            if (detailsDTO.getSub_total() == null) {
                throw new IllegalArgumentException("Subtotal tidak boleh null pada product Id: " + detailsDTO.getProduct_id());
            }
            if (detailsDTO.getProduct_id() == null) {
                throw new IllegalArgumentException("Product Id tidak boleh null pada transaction details");
            }

            Long productId = detailsDTO.getProduct_id();
            Products product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product tidak ditemukan untuk id: " + productId));
            int quantity = detailsDTO.getQuantity();
            int price = product.getPrice();
            int subTotal = detailsDTO.getSub_total();
            if (subTotal != price * quantity) {
                throw new IllegalArgumentException("Sub total tidak sesuai dengan price dikali quantity");
            }
            totalSubTotal += subTotal;
        }

        if (totalSubTotal != totalAmount) {
            throw new IllegalArgumentException("Total amount harus sama dengan penjumlahan sub total");
        }

        transaction.setTotalAmount(totalAmount);
        transaction.setTotalPay(totalPay);
        transaction.setTransactionDate(LocalDate.now());
        transaction = transactionRepository.save(transaction);

        for (TransactionDetailsDTO detailsDTO : detailsDTOList) {
            Products product = productRepository.findById(detailsDTO.getProduct_id())
                    .orElseThrow(() -> new IllegalArgumentException("Produk dengan ID " + detailsDTO.getProduct_id() + " tidak ditemukan"));

            TransactionDetails detail = new TransactionDetails();
            detail.setTransaction(transaction);
            detail.setProduct(product);
            detail.setQuantity(detailsDTO.getQuantity());
            detail.setSubtotal(detailsDTO.getSub_total());

            transactionDetailsRepository.save(detail);
        }

        return new ResponseMessage("ok", "succes");
    }


    private TransactionsDTO convertToDTO(Transactions transaction) {
        TransactionsDTO dto = new TransactionsDTO();
        dto.setTotal_amount(transaction.getTotalAmount());
        dto.setTotal_pay(transaction.getTotalPay());
        dto.setTransaction_date(transaction.getTransactionDate());
        return dto;
    }
}




