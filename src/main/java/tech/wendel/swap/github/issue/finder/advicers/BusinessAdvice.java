package tech.wendel.swap.github.issue.finder.advicers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import tech.wendel.swap.github.issue.finder.domain.exceptions.GithubServiceErrors;
import tech.wendel.swap.github.issue.finder.domain.exceptions.ServiceException;

@RestControllerAdvice
public class BusinessAdvice {

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GithubServiceErrors handleServiceException(ServiceException serviceException) {
        return new GithubServiceErrors(serviceException);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public GithubServiceErrors handleHttpClientErrorException(HttpClientErrorException httpClientErrorException) {
        return new GithubServiceErrors(httpClientErrorException);
    }
}