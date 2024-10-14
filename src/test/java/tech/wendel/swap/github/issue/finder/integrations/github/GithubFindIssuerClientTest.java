package tech.wendel.swap.github.issue.finder.integrations.github;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import tech.wendel.swap.github.issue.finder.domain.model.Issue;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class GithubFindIssuerClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GithubFindIssuerClient githubFindIssuerClient;

    @BeforeEach
    void setUp() {
        githubFindIssuerClient = new GithubFindIssuerClient(restTemplate, "http://example.com");
    }

    @Test
    void testExecuteRequestSuccessfully() {
        List<Issue> issues = List.of(new Issue("Issue 1", "Description 1"));
        ResponseEntity<List<Issue>> responseEntity = ResponseEntity.ok(issues);

        when(restTemplate.exchange(eq("http://example.com"), eq(HttpMethod.GET), any(), any(ParameterizedTypeReference.class)))
                .thenReturn(responseEntity);

        List<Issue> result = githubFindIssuerClient.getGithubResource(any(), any());

        assertEquals(issues, result);
    }

    @Test
    void testExecuteRequestThrowsException() {
        when(restTemplate.exchange(eq("http://example.com"), eq(HttpMethod.GET), any(), any(ParameterizedTypeReference.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThrows(HttpClientErrorException.class, () -> githubFindIssuerClient.getGithubResource(any(), any()));
    }
}