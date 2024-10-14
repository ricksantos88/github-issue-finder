package tech.wendel.swap.github.issue.finder.integrations.github;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tech.wendel.swap.github.issue.finder.domain.model.Issue;

import java.util.List;

@Component
public class GithubFindIssuerClient extends GithubClientComponent<List<Issue>> {

    public GithubFindIssuerClient(RestTemplate restTemplate, @Value("${github.api.url-issues}")String gitHubIssueUrl) {
        super(gitHubIssueUrl, restTemplate);
    }

    @Override
    protected ParameterizedTypeReference<List<Issue>> getResponseType() {
        return new ParameterizedTypeReference<>() {};
    }
}
