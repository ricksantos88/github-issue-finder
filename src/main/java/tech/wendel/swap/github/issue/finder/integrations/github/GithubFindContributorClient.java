package tech.wendel.swap.github.issue.finder.integrations.github;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tech.wendel.swap.github.issue.finder.domain.model.Contributor;

import java.util.List;

@Component
public class GithubFindContributorClient extends GithubClientComponent<List<Contributor>> {

    public GithubFindContributorClient(RestTemplate restTemplate, @Value("${github.api.url-contributors}")String gitHubContributorUrl) {
        super(gitHubContributorUrl, restTemplate);
    }

    @Override
    protected ParameterizedTypeReference<List<Contributor>> getResponseType() {
        return new ParameterizedTypeReference<>() {};
    }
}
