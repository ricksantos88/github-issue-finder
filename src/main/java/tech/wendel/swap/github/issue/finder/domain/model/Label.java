package tech.wendel.swap.github.issue.finder.domain.model;

import lombok.Data;

@Data
public class Label {
    private Long id;
    private String name;
    private String color;
}