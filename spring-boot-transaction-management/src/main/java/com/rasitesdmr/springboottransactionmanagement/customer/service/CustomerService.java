package com.rasitesdmr.springboottransactionmanagement.customer.service;

import com.rasitesdmr.springboottransactionmanagement.customer.Customer;
import com.rasitesdmr.springboottransactionmanagement.customer.model.request.CustomerCreateRequest;
import com.rasitesdmr.springboottransactionmanagement.customer.model.response.CustomerResponse;

public interface CustomerService {

    void saveCustomer(Customer customer);
    CustomerResponse createCustomer(CustomerCreateRequest customerCreateRequest);
    Customer getById(Long id);
    String getCustomerCardDataById(Long id);
}
