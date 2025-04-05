package com.rasitesdmr.springboottransactionmanagement.customer.controller;

import com.rasitesdmr.springboottransactionmanagement.customer.model.request.CustomerCreateRequest;
import com.rasitesdmr.springboottransactionmanagement.customer.model.response.CustomerResponse;
import com.rasitesdmr.springboottransactionmanagement.customer.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(path = "")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerCreateRequest customerCreateRequest){
        return new ResponseEntity<>(customerService.createCustomer(customerCreateRequest), HttpStatus.CREATED);
    }

    @GetMapping(path = "/card-data")
    public ResponseEntity<String> getCustomerCardDataById(@RequestParam Long id){
        return new ResponseEntity<>(customerService.getCustomerCardDataById(id), HttpStatus.OK);
    }
}
