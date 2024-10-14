package tech.wendel.swap.github.issue.finder.domain.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.validation.FieldError;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GithubServiceErrors {

    private final List<ErrorResponse> errors = new ArrayList<>();

    public GithubServiceErrors(List<FieldError> fieldErrors, HttpStatus httpStatus) {
        for (FieldError fieldError : fieldErrors) {
            Object rejectedValue = fieldError.getRejectedValue() != null ? fieldError.getRejectedValue() : "";
            String defaultMessage = fieldError.getField() + " " + rejectedValue + " " + fieldError.getDefaultMessage();
            errors.add(generateErrorResponse(httpStatus, defaultMessage));
        }
    }

    public GithubServiceErrors(ResponseStatusException responseStatusException) {
        String reason = responseStatusException.getReason();
        if (reason != null) {
            errors.add(generateErrorResponse(responseStatusException.getStatusCode(), reason));
        }
    }

    public GithubServiceErrors(HttpClientErrorException httpClientErrorException) {
        String reason = httpClientErrorException.getMessage();
        if (reason != null) {
            errors.add(generateErrorResponse(httpClientErrorException.getStatusCode(), reason));
        }
    }

    private ErrorResponse generateErrorResponse(HttpStatus httpStatus, String message) {
        return new ErrorResponse(httpStatus.value(), message);
    }

    private ErrorResponse generateErrorResponse(HttpStatusCode httpStatusCode, String message) {
        return new ErrorResponse(httpStatusCode.value(), message);
    }
}