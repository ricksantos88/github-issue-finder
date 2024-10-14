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
import tech.wendel.swap.github.issue.finder.domain.model.Contributor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class GithubFindContributorClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GithubFindContributorClient githubFindContributorClient;

    @BeforeEach
    void setUp() {
        githubFindContributorClient = new GithubFindContributorClient(restTemplate, "http://example.com");
    }

    @Test
    void testExecuteRequestSuccessfully() {
        List<Contributor> contributors = List.of(new Contributor("Contributor 1"));
        ResponseEntity<List<Contributor>> responseEntity = ResponseEntity.ok(contributors);

        when(restTemplate.exchange(eq("http://example.com"), eq(HttpMethod.GET), any(), any(ParameterizedTypeReference.class)))
                .thenReturn(responseEntity);

        List<Contributor> result = githubFindContributorClient.getGithubResource(any(), any());

        assertEquals(contributors, result);
    }

    @Test
    void testExecuteRequestThrowsException() {
        when(restTemplate.exchange(eq("http://example.com"), eq(HttpMethod.GET), any(), any(ParameterizedTypeReference.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThrows(HttpClientErrorException.class, () -> githubFindContributorClient.getGithubResource(any(), any()));
    }
}