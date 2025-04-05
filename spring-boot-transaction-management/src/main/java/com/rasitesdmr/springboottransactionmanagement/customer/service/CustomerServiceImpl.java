package com.rasitesdmr.springboottransactionmanagement.customer.service;

import com.rasitesdmr.springboottransactionmanagement.customer.Customer;
import com.rasitesdmr.springboottransactionmanagement.customer.model.request.CustomerCreateRequest;
import com.rasitesdmr.springboottransactionmanagement.customer.model.response.CustomerResponse;
import com.rasitesdmr.springboottransactionmanagement.customer.repository.CustomerRepository;
import com.rasitesdmr.springboottransactionmanagement.exception.exceptions.NotAvailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ApplicationContext applicationContext;


    public CustomerServiceImpl(CustomerRepository customerRepository, ApplicationContext applicationContext) {
        this.customerRepository = customerRepository;
        this.applicationContext = applicationContext;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveCustomer(Customer customer) {
        log.info("saveCustomer - Transaction active: {}", TransactionSynchronizationManager.isActualTransactionActive());
        log.info("saveCustomer - Current transaction name: {}", TransactionSynchronizationManager.getCurrentTransactionName());
        customerRepository.save(customer);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerResponse createCustomer(CustomerCreateRequest customerCreateRequest) {
        log.info("createCustomer - Transaction active: {}", TransactionSynchronizationManager.isActualTransactionActive());
        log.info("createCustomer - Current transaction name: {}", TransactionSynchronizationManager.getCurrentTransactionName());

        final String firstName = customerCreateRequest.getFirstName();
        final String lastName = customerCreateRequest.getLastName();
        final String email = customerCreateRequest.getEmail();

        Customer customer = Customer.builder().firstName(firstName).lastName(lastName).email(email).build();

        CustomerService proxyCustomerService = getProxyCustomerService();
        proxyCustomerService.saveCustomer(customer);

        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .build();
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotAvailableException("Customer not found."));
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public String getCustomerCardDataById(Long id) {
        Customer customer = getById(id);
        // Müşterinin sistemdeki kart bilgilerini getirme süreci..
        return "11111-1111-111-11";
    }

    private CustomerService getProxyCustomerService() {
        return applicationContext.getBean(CustomerService.class);
    }
}
