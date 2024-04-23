package PointOfSales.ProjectPOS.Exception;

import PointOfSales.ProjectPOS.Utils.CategoryNotFoundException;
import PointOfSales.ProjectPOS.Utils.ResponseMessage;
import PointOfSales.ProjectPOS.Utils.SpecialCharacterException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    public ResponseEntity<ResponseMessage> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
//        ResponseMessage response = new ResponseMessage("error", "Tidak sesuai format data");
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//    }
//
//    @ExceptionHandler(CategoryNotFoundException.class)
//    public ResponseEntity<ResponseMessage> handleCategoryNotFoundException(CategoryNotFoundException ex) {
//        ResponseMessage response = new ResponseMessage("error", ex.getMessage());
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ResponseMessage> handleException(Exception ex) {
//        ResponseMessage response = new ResponseMessage("error", "Terdapat kesalahan dalam permintaan");
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseMessage handleValidationExceptions(MethodArgumentNotValidException ex) {
        ResponseMessage response = new ResponseMessage();
        Map<String, String> errorMessage = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessage.put(error.getField(), error.getDefaultMessage());
        });

        response.setStatus("error");
        response.setMessage(errorMessage.toString());

        return response;
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseMessage illegalArgumentExceptions (IllegalArgumentException ex) {ResponseMessage errormsg = new ResponseMessage();
        errormsg.setStatus
                (String.valueOf(HttpStatus.BAD_REQUEST));
        errormsg.setMessage(ex.getMessage());
        return errormsg;
    }
    @ExceptionHandler(SpecialCharacterException.class)
    public ResponseMessage handleSpecialCharacterException(SpecialCharacterException ex){ResponseMessage errormsg = new ResponseMessage();
        errormsg.setStatus
                (String.valueOf(HttpStatus.BAD_REQUEST));
        errormsg.setMessage(ex.getMessage());
        return errormsg;
    }
    public static boolean isValidInput(String input) {
        // Pola yang hanya memperbolehkan karakter alfanumerik dan spasi
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\s]+$");
        // Menguji apakah input sesuai dengan pola
        return pattern.matcher(input).matches();
    }
}