package com.nsv.jsmbaba.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Customer {
    private int customerId;
    private String name;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}
