package tech.wendel.swap.github.issue.finder.domain.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceError {

    private final String message;
    private final HttpStatus status;

    public static final ServiceError USER_NOT_FOUND = new ServiceError(HttpStatus.NOT_FOUND, "user.not.found");
    public static final ServiceError ISSUES_NOT_FOUND = new ServiceError(HttpStatus.NOT_FOUND, "issues.not.found");
    public static final ServiceError CONTRIBUTORS_NOT_FOUND = new ServiceError(HttpStatus.NOT_FOUND, "contributors.not.found");
    public static final ServiceError RESOURCE_NOT_FOUND = new ServiceError(HttpStatus.NOT_FOUND, "contributors.not.found");
    public static final ServiceError POST_IN_WEBHOOK_FAILED = new ServiceError(HttpStatus.NOT_FOUND, "post.in.webhook.failed");

    ServiceError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ServiceError{" +
                "message='" + message + '\'' +
                ", code=" + status +
                '}';
    }
}
