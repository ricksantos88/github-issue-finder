package tech.wendel.swap.github.issue.finder.domain.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.wendel.swap.github.issue.finder.domain.exceptions.ServiceError;
import tech.wendel.swap.github.issue.finder.domain.exceptions.ServiceException;
import tech.wendel.swap.github.issue.finder.domain.model.Contributor;
import tech.wendel.swap.github.issue.finder.domain.model.Issue;
import tech.wendel.swap.github.issue.finder.domain.model.RepositoryInfo;
import tech.wendel.swap.github.issue.finder.integrations.github.GithubFindContributorClient;
import tech.wendel.swap.github.issue.finder.integrations.github.GithubFindIssuerClient;
import tech.wendel.swap.github.issue.finder.integrations.webhook.WebhookPostResourceClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class GitHubServiceImplTest {

    @Mock
    private GithubFindIssuerClient githubFindIssuerClient;

    @Mock
    private GithubFindContributorClient githubFindContributorClient;

    @Mock
    private WebhookPostResourceClient webhookPostResourceClient;

    @InjectMocks
    private GitHubServiceImpl gitHubServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetGithubRepositoryInfos() throws Exception {
        String owner = "owner";
        String repo = "repo";
        List<Issue> issues = List.of(new Issue("Issue 1", "Description 1"));
        List<Contributor> contributors = List.of(new Contributor("Contributor 1"));

        when(githubFindIssuerClient.getGithubResource(owner, repo)).thenReturn(issues);
        when(githubFindContributorClient.getGithubResource(owner, repo)).thenReturn(contributors);

        RepositoryInfo repositoryInfo = gitHubServiceImpl.getGithubRepositoryInfos(owner, repo);

        assertEquals(owner, repositoryInfo.getUser());
        assertEquals(repo, repositoryInfo.getRepository());
        assertEquals(issues, repositoryInfo.getIssues());
        assertEquals(contributors, repositoryInfo.getContributors());
    }

    @Test
    void testGetIssuesThrowsException() throws Exception {
        String owner = "owner";
        String repo = "repo";

        doThrow(new ServiceException(ServiceError.ISSUES_NOT_FOUND)).when(githubFindIssuerClient).getGithubResource(owner, repo);

        assertThrows(ServiceException.class, () -> gitHubServiceImpl.getGithubRepositoryInfos(owner, repo));
    }

    @Test
    void testGetContributorsThrowsException() throws Exception {
        String owner = "owner";
        String repo = "repo";

        doThrow(new ServiceException(ServiceError.CONTRIBUTORS_NOT_FOUND)).when(githubFindContributorClient).getGithubResource(owner, repo);

        assertThrows(ServiceException.class, () -> gitHubServiceImpl.getGithubRepositoryInfos(owner, repo));
    }
}