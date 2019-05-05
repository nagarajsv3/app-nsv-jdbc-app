package com.nsv.jsmbaba.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Reviewer {
    private int reviewerId;
    private String reviewerName;
    private List<Task> tasks = new ArrayList<>();
}
