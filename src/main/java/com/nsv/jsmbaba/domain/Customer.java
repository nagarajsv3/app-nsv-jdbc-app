package com.nsv.jsmbaba.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private int customerId;
    private String name;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private PhoneInformation phoneInformation;
    private List<Order> orders = new ArrayList<>();

}
