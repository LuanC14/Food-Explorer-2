package com.foodexplorer.exceptions;

import com.foodexplorer.exceptions.custom.DataConflictException;
import com.foodexplorer.exceptions.custom.DataNotFoundException;
import com.foodexplorer.exceptions.custom.UnathourizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest rq) {
        logger.error("Ocorreu um erro interno inesperado! "
                + "Detalhes: "
                + ex.getMessage()
                + " Endpoint: "
                + rq.getDescription(false));
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(), rq.getDescription(false)), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataConflictException.class)
    public final ResponseEntity<ExceptionResponse> handleDataAlreadyExistsException(DataConflictException ex, WebRequest rq) {
        logger.error("Ocorreu uma tentativa de salvar um dado único já existente no banco de dados! "
                + "Detalhes: "
                + ex.getMessage()
                + " Endpoint: "
                + rq.getDescription(false));
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(), rq.getDescription(false)), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleDataNotFoundException(DataNotFoundException ex, WebRequest rq) {
        logger.error("Você buscou por um dado inexistente! "
                + "Detalhes: "
                + ex.getMessage()
                + " Endpoint: "
                + rq.getDescription(false));
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(), rq.getDescription(false)), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnathourizedException.class)
    public final ResponseEntity<ExceptionResponse> handleAllUnathourizedExceptions(UnathourizedException ex, WebRequest rq) {
        logger.error("Você não tem permissão para realizar esta operação! "
                + "Detalhes: "
                + ex.getMessage()
                + " Endpoint: "
                + rq.getDescription(false));
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(), rq.getDescription(false)), HttpStatus.NOT_FOUND);
    }
}

