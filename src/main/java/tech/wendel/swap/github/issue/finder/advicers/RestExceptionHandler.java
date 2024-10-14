package tech.wendel.swap.github.issue.finder.advicers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import tech.wendel.swap.github.issue.finder.domain.exceptions.GithubServiceErrors;

import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public GithubServiceErrors handleValidationException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getFieldErrors();
        return new GithubServiceErrors(fieldErrors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<GithubServiceErrors> handleResponseStatusException(ResponseStatusException exception) {
        GithubServiceErrors apiErrors = new GithubServiceErrors(exception);
        return new ResponseEntity<>(apiErrors, exception.getStatusCode());
    }
}