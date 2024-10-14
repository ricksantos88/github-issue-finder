package tech.wendel.swap.github.issue.finder.integrations.webhook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import tech.wendel.swap.github.issue.finder.domain.exceptions.ServiceError;
import tech.wendel.swap.github.issue.finder.domain.exceptions.ServiceException;

import java.util.Collections;

@Slf4j
public abstract class WebhookClientComponent<T> {
    private final String webhookUrl;
    private final RestTemplate restTemplate;

    protected WebhookClientComponent(String webhookUrl, RestTemplate restTemplate) {
        this.webhookUrl = webhookUrl;
        this.restTemplate = restTemplate;
    }

    protected abstract ParameterizedTypeReference<T> getResponseType();

    public void postWebhookResource(T resource) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<T> requestEntity = new HttpEntity<>(resource, headers);

        ResponseEntity<String> response = restTemplate.exchange(webhookUrl, HttpMethod.POST, requestEntity, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ServiceException(ServiceError.POST_IN_WEBHOOK_FAILED);
        }
        log.info("Webhook sent successfully, url: {}", response.getBody());
    }
}
