package tech.wendel.swap.github.issue.finder.domain.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.wendel.swap.github.issue.finder.domain.exceptions.ServiceException;
import tech.wendel.swap.github.issue.finder.domain.model.Contributor;
import tech.wendel.swap.github.issue.finder.domain.model.Issue;
import tech.wendel.swap.github.issue.finder.domain.model.RepositoryInfo;
import tech.wendel.swap.github.issue.finder.domain.service.GitHubService;
import tech.wendel.swap.github.issue.finder.integrations.github.GithubFindContributorClient;
import tech.wendel.swap.github.issue.finder.integrations.github.GithubFindIssuerClient;
import tech.wendel.swap.github.issue.finder.integrations.webhook.WebhookPostResourceClient;

import java.util.List;

@Slf4j
@Service
public class GitHubServiceImpl implements GitHubService {

    private final GithubFindIssuerClient githubFindIssuerClient;
    private final GithubFindContributorClient githubFindContributorClient;
    private final WebhookPostResourceClient webhookPostResourceClient;

    public GitHubServiceImpl(GithubFindIssuerClient githubFindIssuerClient, GithubFindContributorClient githubFindContributorClient, WebhookPostResourceClient webhookPostResourceClient) {
        this.githubFindIssuerClient = githubFindIssuerClient;
        this.githubFindContributorClient = githubFindContributorClient;
        this.webhookPostResourceClient = webhookPostResourceClient;
    }

    @Override
    public RepositoryInfo getGithubRepositoryInfos(String owner, String repo) throws ServiceException {
        List<Issue> issues = getIssues(owner, repo);
        List<Contributor> contributors = getContributors(owner, repo);

        RepositoryInfo repositoryInfo = new RepositoryInfo(owner, repo, issues, contributors);
        sendResourceToWebhook(repositoryInfo);
        return repositoryInfo;
    }

    private List<Contributor> getContributors(String owner, String repo) throws ServiceException {
        log.info("Getting contributors from GitHub repository: {}/{}", owner, repo);
        return githubFindContributorClient.getGithubResource(owner, repo);
    }

    private List<Issue> getIssues(String owner, String repo) throws ServiceException {
        log.info("Getting issues from GitHub repository: {}/{}", owner, repo);
        return githubFindIssuerClient.getGithubResource(owner, repo);
    }

    private void sendResourceToWebhook(RepositoryInfo repositoryInfo) {
        log.info("Sending repository info to webhook");
        webhookPostResourceClient.postWebhookResource(repositoryInfo);
    }
}