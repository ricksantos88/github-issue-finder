package tech.wendel.swap.github.issue.finder.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Issue {

    private String url;

    @JsonProperty("html_url")
    private String htmlUrl;

    private Long id;

    private String title;

    private String state;

    private User user;

    private List<Label> labels;

    @JsonProperty("comments_url")
    private String commentsUrl;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("closed_at")
    private String closedAt;

    public Issue(String title, String state) {
        this.title = title;
        this.state = state;
    }
}