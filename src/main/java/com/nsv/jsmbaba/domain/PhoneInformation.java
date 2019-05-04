package com.nsv.jsmbaba.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PhoneInformation {
    private String home;
    private String work;
    private String cell;
    private Customer customer;
}
