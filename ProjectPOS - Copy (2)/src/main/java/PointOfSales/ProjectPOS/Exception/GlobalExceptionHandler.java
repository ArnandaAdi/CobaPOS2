package PointOfSales.ProjectPOS.Exception;

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


import java.util.*;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler {
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
    public ResponseEntity<ResponseMessage> handleIllegalArgumentExceptions(IllegalArgumentException ex) {
        ResponseMessage response = new ResponseMessage();
        response.setStatus("error");
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(SpecialCharacterException.class)
    public ResponseMessage handleSpecialCharacterException(SpecialCharacterException ex){ResponseMessage errormsg = new ResponseMessage();
        errormsg.setStatus("error");
        errormsg.setMessage(ex.getMessage());
        return errormsg;
    }

    public static boolean isValidInput(String input) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\s]+$");
        return pattern.matcher(input).matches();
    }
}