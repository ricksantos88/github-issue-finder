package tech.wendel.swap.github.issue.finder.domain.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class ServiceException extends ResponseStatusException {
    private final HttpStatus httpStatus;

    public ServiceException(ServiceError serviceError) {
        super(serviceError.getStatus(), serviceError.getMessage());
        this.httpStatus = serviceError.getStatus();
    }

}
