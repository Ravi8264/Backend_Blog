package com.blog.blog.exceptions;

import com.blog.blog.payloads.ApiResponce;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Specific handler for ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponce> handleResourceNotFoundException(ResourceNotFoundException ex) {
        String message = ex.getMessage();
        ApiResponce apiResponce = new ApiResponce(message, false);
        return new ResponseEntity<>(apiResponce, HttpStatus.NOT_FOUND);
    }

    // Generic handler for other RuntimeExceptions
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponce> handleRuntimeException(RuntimeException ex) {
        ApiResponce apiResponce = new ApiResponce(ex.getMessage(), false);
        return new ResponseEntity<>(apiResponce, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> MethodArgumentNotValidException(MethodArgumentNotValidException ex){
      Map<String,String> resp=new HashMap<>();
      ex.getBindingResult().getAllErrors().forEach((error)->{
         String fieldName= ((FieldError)error).getField();
        String messege= error.getDefaultMessage();
        resp.put(fieldName,messege);
      });
      return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
    }
}