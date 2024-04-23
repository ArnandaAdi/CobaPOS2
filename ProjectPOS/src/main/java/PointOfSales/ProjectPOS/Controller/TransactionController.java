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
    public ResponseEntity<?> listTransactionDetailsByTransactionId(@PathVariable Long id) {
        try {
            List<TransactionDetailsDTO> transactionDetails = transactionDetailsService.getTransactionDetailsByTransactionId(id);
            if (transactionDetails.isEmpty()) {
                return ResponseEntity.ok().body(Collections.emptyList());
            }
            return ResponseEntity.ok().body(transactionDetails);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @PostMapping("/addtransaction")
    public ResponseEntity<ResponseMessage> addTransaction(@Valid @RequestBody TransactionAddDTO transactionAddDTO) {
        try {
            transactionService.addTransaction(transactionAddDTO);
            return ResponseEntity.ok(new ResponseMessage("ok", "succes"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseMessage("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("error", "Gagal menambahkan transaksi: " + e.getMessage()));
        }
    }
}


