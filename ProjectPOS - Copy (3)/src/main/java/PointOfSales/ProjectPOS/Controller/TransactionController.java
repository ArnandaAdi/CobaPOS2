package PointOfSales.ProjectPOS.Controller;

import PointOfSales.ProjectPOS.DTO.TransactionAddDTO;
import PointOfSales.ProjectPOS.DTO.TransactionDetailsDTO;
import PointOfSales.ProjectPOS.DTO.TransactionsDTO;
import PointOfSales.ProjectPOS.Service.TransactionDetailsService;
import PointOfSales.ProjectPOS.Service.TransactionService;
import PointOfSales.ProjectPOS.Utils.ResponseMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/pos/api")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionDetailsService transactionDetailsService;

    @Autowired
    public TransactionController(TransactionService transactionService, TransactionDetailsService transactionDetailsService) {
        this.transactionService = transactionService;
        this.transactionDetailsService = transactionDetailsService;
    }

    @GetMapping("/listtransactions")
    public ResponseEntity<List<TransactionsDTO>> getAllTransactions() {
        List<TransactionsDTO> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok().body(transactions);
    }

    @GetMapping("/listtransaksidetail/{id}")
    public ResponseEntity<?> getDetailTransaction(@PathVariable Long id) {
        List<TransactionDetailsDTO> transactionDetails = transactionDetailsService.getTransactionDetailsByTransactionId(id);
        if (!transactionDetails.isEmpty()) {
            return ResponseEntity.ok().body(transactionDetails);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("error", "Transaction details untuk Id tidak ditemukan"));
        }
    }

    @PostMapping("/addtransaction")
    public ResponseEntity<ResponseMessage> addTransaction(@Valid @RequestBody TransactionAddDTO transactionAddDTO) {
        ResponseMessage responseMessage = transactionService.addTransaction(transactionAddDTO);

        if (responseMessage.getStatus().equals("ok")) {
            return ResponseEntity.ok(responseMessage);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }
    }
}


