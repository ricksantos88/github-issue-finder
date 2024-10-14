package tech.wendel.swap.github.issue.finder.domain.model;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class Contributor {

    private String login;
    private Long id;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("html_url")
    private String htmlUrl;

    private String type;

    @JsonProperty("site_admin")
    private boolean siteAdmin;

    private int contributions;

    public Contributor(String login) {
        this.login = login;
    }
}