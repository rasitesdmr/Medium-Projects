package com.rasitesdmr.springboottransactionmanagement.customer.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCreateRequest {
    private String firstName;
    private String lastName;
    private String email;
}
