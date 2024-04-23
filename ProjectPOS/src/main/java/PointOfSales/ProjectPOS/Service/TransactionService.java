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
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
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

    public void addTransaction(TransactionAddDTO transactionAddDTO) {
        for (TransactionDetailsDTO details : transactionAddDTO.getTransaction_details()) {
            if (details.getQuantity() == null) {
                throw new IllegalArgumentException("Quantity tidak boleh null pada product Id: " + details.getProduct_id());
            }
            if (details.getSub_total() == null) {
                throw new IllegalArgumentException("Subtotal tidak boleh null pada product Id: " + details.getProduct_id());
            }
            if (details.getProduct_id() == null) {
                throw new IllegalArgumentException("Product Id tidak boleh null pada transaction details");
            }
        }

        if (transactionAddDTO.getTotal_amount() == null) {
            throw new IllegalArgumentException("Total amount tidak boleh null.");
        }
        if (transactionAddDTO.getTotal_pay() == null) {
            throw new IllegalArgumentException("Total pay tidak boleh null.");
        }
        if (transactionAddDTO.getTotal_pay() < transactionAddDTO.getTotal_amount()) {
            throw new IllegalArgumentException("Total pay harus lebih besar atau sama dengan total amount");
        }

        Transactions transaction = new Transactions();
        transaction.setTotalAmount(transactionAddDTO.getTotal_amount());
        transaction.setTotalPay(transactionAddDTO.getTotal_pay());
        transaction.setTransactionDate(LocalDate.now());

        Transactions savedTransaction = transactionRepository.save(transaction);

        List<TransactionDetailsDTO> detailsDTOList = transactionAddDTO.getTransaction_details();
        for (TransactionDetailsDTO detailsDTO : detailsDTOList) {
            Products product = productRepository.findById(detailsDTO.getProduct_id())
                    .orElseThrow(() -> new IllegalArgumentException("Produk dengan ID " + detailsDTO.getProduct_id() + " tidak ditemukan"));

            TransactionDetails detail = new TransactionDetails();
            detail.setTransaction(savedTransaction);
            detail.setProduct(product);
            detail.setQuantity(detailsDTO.getQuantity());
            detail.setSubtotal(detailsDTO.getSub_total());

            transactionDetailsRepository.save(detail);
        }
    }

    private TransactionsDTO convertToDTO(Transactions transaction) {
        TransactionsDTO dto = new TransactionsDTO();
        dto.setTotal_amount(transaction.getTotalAmount());
        dto.setTotal_pay(transaction.getTotalPay());
        dto.setTransaction_date(transaction.getTransactionDate());
        return dto;
    }
}




