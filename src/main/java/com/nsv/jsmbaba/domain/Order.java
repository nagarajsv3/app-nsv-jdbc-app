package com.nsv.jsmbaba.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Order {
    private int orderId;
    private String item;
    private OrderStatus orderStatus;
    private Customer customer;
}
