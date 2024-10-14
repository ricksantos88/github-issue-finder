package tech.wendel.swap.github.issue.finder.integrations.webhook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestTemplate;
import tech.wendel.swap.github.issue.finder.domain.model.RepositoryInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebhookPostResourceClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Value("${webhook.site.url}")
    private String webhookUrl;

    @InjectMocks
    private WebhookPostResourceClient webhookPostResourceClient;

    @BeforeEach
    void setUp() {
        webhookPostResourceClient = new WebhookPostResourceClient(restTemplate, webhookUrl);
    }

    @Test
    void testWebhookUrlConfiguration() {
        assertEquals(webhookUrl, webhookPostResourceClient.getResponseType());
    }

    @Test
    void testGetResponseType() {
        ParameterizedTypeReference<RepositoryInfo> responseType = webhookPostResourceClient.getResponseType();
        assertEquals(new ParameterizedTypeReference<RepositoryInfo>() {}, responseType);
    }
}