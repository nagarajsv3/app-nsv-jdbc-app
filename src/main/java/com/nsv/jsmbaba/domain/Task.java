package com.nsv.jsmbaba.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Task {
    private int taskId;
    private String taskName;
    private List<Reviewer> reviewers = new ArrayList<>();
}
