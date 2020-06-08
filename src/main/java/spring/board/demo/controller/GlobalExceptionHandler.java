package spring.board.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import spring.board.demo.domain.error.dto.ErrorResponse;
import spring.board.demo.exception.AccessDeniedException;
import spring.board.demo.exception.BusinessException;
import spring.board.demo.exception.EntityNotFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handlerArgument(MethodArgumentNotValidException e) {
        log.debug("======================");
        log.debug("Binding Validation Excetpion", e);
        log.debug("======================");

        BindingResult bindingResult = e.getBindingResult();
        List<String> messages = bindingResult.getAllErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(
                Collectors.toList());
        StringBuilder builder = new StringBuilder();
        messages.forEach(message -> builder.append(message).append("\n"));
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(builder.toString(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.debug("======================");
        log.debug("Access Denied Exception", e);
        log.debug("======================");

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(new ErrorResponse(e.getMessage(), HttpStatus.FORBIDDEN.value()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        log.debug("======================");
        log.debug("Entity Not Found Exception", e);
        log.debug("======================");

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.info("======================");
        log.info("Business Excetpion", e);
        log.info("======================");

        return ResponseEntity.badRequest()
            .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception e) {
        log.info("======================");
        log.info("UnExpected Excetpion", e);
        log.info("======================");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
