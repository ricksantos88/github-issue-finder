package tech.wendel.swap.github.issue.finder.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
public class RepositoryInfo {
    @JsonProperty("user")
    private String user;
    @JsonProperty("repository")
    private String repository;
    @JsonProperty("issues")
    private List<Issue> issues;
    @JsonProperty("contributors")
    private List<Contributor> contributors;
}