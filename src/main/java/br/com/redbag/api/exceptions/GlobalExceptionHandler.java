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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final HttpServletRequest request;
    private final ExceptionResponseBody responseBody;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        responseBody.setStatus(HttpStatus.BAD_REQUEST.value());
        responseBody.setMessage(ex.getBindingResult().getFieldError().getDefaultMessage());
        responseBody.setTimeStamp(getTimeStamp());
        responseBody.setPath(request.getDescription(false));
        return new ResponseEntity<>(responseBody, status);
    }

        @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        responseBody.setTimeStamp(getTimeStamp());
        responseBody.setStatus(HttpStatus.BAD_REQUEST.value());
        responseBody.setMessage("Invalid format requested");
        responseBody.setPath(request.getDescription(false));

        return new ResponseEntity<>(responseBody, status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponseBody> handleBadRequestException(ConstraintViolationException ex){
        responseBody.setStatus(HttpStatus.BAD_REQUEST.value());
        responseBody.setMessage(ex.getMessage());
        responseBody.setTimeStamp(getTimeStamp());
        responseBody.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponseBody> handleResourceNotFoundException(ResourceNotFoundException ex){
        ExceptionResponseBody responseBody = new ExceptionResponseBody();
        responseBody.setStatus(HttpStatus.NOT_FOUND.value());
        responseBody.setMessage(ex.getMessage());
        responseBody.setTimeStamp(getTimeStamp());
        responseBody.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponseBody> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex){
        responseBody.setStatus(HttpStatus.CONFLICT.value());
        responseBody.setMessage(ex.getMessage());
        responseBody.setTimeStamp(getTimeStamp());
        responseBody.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
    }

    @ExceptionHandler(ImageUploadException.class)
    public ResponseEntity<ExceptionResponseBody> handleImageUploadException(ImageUploadException ex){
        responseBody.setStatus(HttpStatus.BAD_REQUEST.value());
        responseBody.setMessage(ex.getMessage());
        responseBody.setTimeStamp(getTimeStamp());
        responseBody.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    @ExceptionHandler(ForbiddenUserRequestException.class)
    public ResponseEntity<ExceptionResponseBody> handleForbiddenUserRequestException(ForbiddenUserRequestException ex){
        responseBody.setStatus(HttpStatus.FORBIDDEN.value());
        responseBody.setMessage(ex.getMessage());
        responseBody.setTimeStamp(getTimeStamp());
        responseBody.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ExceptionResponseBody> handleResourceNotFoundException(IllegalStateException ex){
        responseBody.setStatus(HttpStatus.BAD_REQUEST.value());
        responseBody.setMessage(ex.getMessage());
        responseBody.setTimeStamp(getTimeStamp());
        responseBody.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseBody> handleGenericException(Exception ex){
        responseBody.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseBody.setMessage("An unexpected error occurred");
        responseBody.setTimeStamp(getTimeStamp());
        responseBody.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }

    private ZonedDateTime getTimeStamp() {
        ZoneId brazilZoneId = ZoneId.of("America/Sao_Paulo");
        return ZonedDateTime.now(brazilZoneId);
    }
}