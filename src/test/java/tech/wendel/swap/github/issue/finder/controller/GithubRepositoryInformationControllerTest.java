package tech.wendel.swap.github.issue.finder.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.wendel.swap.github.issue.finder.domain.model.Contributor;
import tech.wendel.swap.github.issue.finder.domain.model.Issue;
import tech.wendel.swap.github.issue.finder.domain.model.RepositoryInfo;
import tech.wendel.swap.github.issue.finder.domain.service.impl.GitHubServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GithubRepositoryInformationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GitHubServiceImpl gitHubServiceImpl;

    @InjectMocks
    private GithubRepositoryInformationController gitHubRepositoryInformationController;

    @BeforeEach
    void setUp() {
        openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(gitHubRepositoryInformationController).build();
    }

    @Test
    void testGetIssues() throws Exception {
        String owner = "owner";
        String repo = "repo";
        List<Issue> issues = new ArrayList<>();
        List<Contributor> contributors = new ArrayList<>();

        RepositoryInfo repositoryInfo = new RepositoryInfo(owner, repo, issues, contributors);

        when(gitHubServiceImpl.getGithubRepositoryInfos(owner, repo)).thenReturn(repositoryInfo);

        mockMvc.perform(get("/api/github/issues/{owner}/{repo}", owner, repo)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void testGetIssuesWithIssues() throws Exception {
        String owner = "owner";
        String repo = "repo";
        List<Issue> issues = List.of(new Issue("Issue 1", "Description 1"), new Issue("Issue 2", "Description 2"));
        List<Contributor> contributors = new ArrayList<>();

        RepositoryInfo repositoryInfo = new RepositoryInfo(owner, repo, issues, contributors);

        when(gitHubServiceImpl.getGithubRepositoryInfos(owner, repo)).thenReturn(repositoryInfo);

        mockMvc.perform(get("/api/github/issues/{owner}/{repo}", owner, repo)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"owner\":\"owner\",\"repo\":\"repo\",\"issues\":[{\"title\":\"Issue 1\",\"description\":\"Description 1\"},{\"title\":\"Issue 2\",\"description\":\"Description 2\"}],\"contributors\":[]}"));
    }
}