package tech.wendel.swap.github.issue.finder.integrations.github;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import tech.wendel.swap.github.issue.finder.domain.exceptions.ServiceError;
import tech.wendel.swap.github.issue.finder.domain.exceptions.ServiceException;

@Slf4j
public abstract class GithubClientComponent<T> {
    private final String gitHubIssueUrl;
    private final RestTemplate restTemplate;

    protected GithubClientComponent(String gitHubIssueUrl, RestTemplate restTemplate) {
        this.gitHubIssueUrl = gitHubIssueUrl;
        this.restTemplate = restTemplate;
    }

    protected abstract ParameterizedTypeReference<T> getResponseType();

    public T getGithubResource(String owner, String repo) throws ServiceException {
        String issueUrl = createResourceUrl(repo, owner);
        T issues = restTemplate.exchange(issueUrl, HttpMethod.GET, null, getResponseType()).getBody();

        if (issues == null) throw new ServiceException(ServiceError.RESOURCE_NOT_FOUND);
        return issues;
    }

    private String createResourceUrl(String repo, String owner) {
        return gitHubIssueUrl.replace("{owner}", owner).replace("{repo}", repo);
    }
}
