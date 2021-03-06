package com.autoexam.apiserver.controller.base;

import com.autoexam.apiserver.exception.AuthenticationException;
import com.autoexam.apiserver.exception.BadRequestException;
import com.autoexam.apiserver.exception.ResourceNotFoundException;
import com.autoexam.apiserver.model.response.ErrorJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.UnexpectedTypeException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity authenticationException(HttpServletRequest request, AuthenticationException exception) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorJson(exception.getMessage()));
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity resourceNotFoundException(HttpServletRequest request, ResourceNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorJson(exception.getMessage()));
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity badRequestException(HttpServletRequest request, BadRequestException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorJson(exception.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException exception) {
    String errorMsg = exception.getBindingResult().getFieldError().getDefaultMessage();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorJson(errorMsg));
  }

  @ExceptionHandler(TypeMismatchException.class)
  public ResponseEntity typeMismatchException(HttpServletRequest request, TypeMismatchException exception) {
    String errorMsg = exception.getErrorCode();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorJson(errorMsg));
  }

  @ExceptionHandler(UnexpectedTypeException.class)
  public ResponseEntity unexpectedTypeException(HttpServletRequest request, UnexpectedTypeException exception) {
    String errorMsg = exception.getLocalizedMessage();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorJson(errorMsg));
  }

  @ExceptionHandler(EmptyResultDataAccessException.class)
  public ResponseEntity emptyResultDataAccessException(HttpServletRequest request, EmptyResultDataAccessException exception) {
    String errorMsg = exception.getLocalizedMessage();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorJson(errorMsg));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity httpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException exception) {
    String errorMsg = exception.getLocalizedMessage();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorJson(errorMsg));
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity httpRequestMethodNotSupportException(HttpServletRequest request, HttpRequestMethodNotSupportedException exception) {
    String errorMsg = exception.getLocalizedMessage();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorJson(errorMsg));
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity httpRequestMethodDataIntegrityViolationException(HttpServletRequest request, DataIntegrityViolationException exception) {
    String errorMsg = exception.getCause().getCause().getMessage();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorJson(errorMsg));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity exception(HttpServletRequest request, Exception exception) {
    String errorMsg = exception.getLocalizedMessage();
    log.error("internal error", exception);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorJson(errorMsg));
  }
}
