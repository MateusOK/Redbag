package br.com.redbag.api.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final HttpServletRequest request;

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ExceptionResponseBody responseBody = new ExceptionResponseBody();

        responseBody.setTimeStamp(new Date());
        responseBody.setStatus(HttpStatus.BAD_REQUEST.value());
        responseBody.setMessage("Invalid format requested");
        responseBody.setPath(request.getDescription(false));

        return new ResponseEntity<>(responseBody, status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponseBody> handleBadRequestException(ConstraintViolationException ex){
        ExceptionResponseBody responseBody = new ExceptionResponseBody();
        responseBody.setStatus(HttpStatus.BAD_REQUEST.value());
        responseBody.setMessage(ex.getMessage());
        responseBody.setTimeStamp(new Date());
        responseBody.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseBody> handleBadaRequestException(MethodArgumentNotValidException ex){
        ExceptionResponseBody responseBody = new ExceptionResponseBody();
        responseBody.setStatus(HttpStatus.BAD_REQUEST.value());
        responseBody.setMessage(ex.getMessage());
        responseBody.setTimeStamp(new Date());
        responseBody.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }


//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ExceptionResponseBody> handleResourceNotFoundException(ResourceNotFoundException ex){
//        ExceptionResponseBody responseBody = new ExceptionResponseBody();
//        responseBody.setStatus(HttpStatus.NOT_FOUND.value());
//        responseBody.setMessage(ex.getMessage());
//        responseBody.setTimeStamp(new Date());
//        responseBody.setPath(request.getRequestURI());
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
//    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ExceptionResponseBody> handleResourceNotFoundException(IllegalStateException ex){
        ExceptionResponseBody responseBody = new ExceptionResponseBody();
        responseBody.setStatus(HttpStatus.BAD_REQUEST.value());
        responseBody.setMessage(ex.getMessage());
        responseBody.setTimeStamp(new Date());
        responseBody.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseBody> handleGenericException(Exception ex){
        ExceptionResponseBody responseBody = new ExceptionResponseBody();

        responseBody.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseBody.setMessage("An unexpected error occurred");
        responseBody.setTimeStamp(new Date());
        responseBody.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }

//    @ExceptionHandler(PostAlreadyExistsException.class)
//    public ResponseEntity<ExceptionResponseBody> handlePostAlreadyExistsException(PostAlreadyExistsException ex) {
//        ExceptionResponseBody responseBody = new ExceptionResponseBody();
//        responseBody.setStatus(HttpStatus.BAD_REQUEST.value());
//        responseBody.setMessage(ex.getMessage());
//        responseBody.setTimeStamp(new Date());
//        responseBody.setPath(request.getRequestURI());
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
//    }

}