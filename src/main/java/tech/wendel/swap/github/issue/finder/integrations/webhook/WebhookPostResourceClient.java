package tech.wendel.swap.github.issue.finder.integrations.webhook;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.wendel.swap.github.issue.finder.domain.model.RepositoryInfo;

@Service
public class WebhookPostResourceClient extends WebhookClientComponent<RepositoryInfo> {

    public WebhookPostResourceClient(RestTemplate restTemplate, @Value("${webhook.site.url}")String webhookUrl) {
        super(webhookUrl, restTemplate);
    }

    @Override
    protected ParameterizedTypeReference<RepositoryInfo> getResponseType() {
        return new ParameterizedTypeReference<>() {};
    }
}
