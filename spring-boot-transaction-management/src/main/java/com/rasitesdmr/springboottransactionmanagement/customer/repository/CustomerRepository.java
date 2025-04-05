package com.rasitesdmr.springboottransactionmanagement.customer.repository;

import com.rasitesdmr.springboottransactionmanagement.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
