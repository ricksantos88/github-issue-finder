package tech.wendel.swap.github.issue.finder.domain.service;

import tech.wendel.swap.github.issue.finder.domain.model.RepositoryInfo;

public interface GitHubService {
    RepositoryInfo getGithubRepositoryInfos(String owner, String repo) throws Exception;
}
