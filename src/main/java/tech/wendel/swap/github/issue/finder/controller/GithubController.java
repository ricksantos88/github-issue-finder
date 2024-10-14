package tech.wendel.swap.github.issue.finder.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.wendel.swap.github.issue.finder.domain.model.RepositoryInfo;
import tech.wendel.swap.github.issue.finder.domain.service.GitHubService;

@Slf4j
@RestController
@RequestMapping("/api/github")
public class GithubController {

    private final GitHubService gitHubService;

    public GithubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/issues/{owner}/{repo}")
    public RepositoryInfo getIssues(@PathVariable String owner, @PathVariable String repo) throws Exception {
        log.info("Getting information from GitHub repository: {}/{}", owner, repo);
        return gitHubService.getGithubRepositoryInfos(owner, repo);
    }
}

