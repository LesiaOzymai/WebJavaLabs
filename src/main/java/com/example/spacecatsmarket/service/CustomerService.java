package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.domain.CustomerDetails;
import com.example.spacecatsmarket.dto.customer.CustomerDetailsDto;
import java.util.List;

public interface CustomerService {

    List<CustomerDetails> getAllCustomerDetails();

    CustomerDetails getCustomerDetailsById(Long customerId);

    CustomerDetails createCustomerDetails(CustomerDetailsDto customerDetailsDto);

    void deleteCustomerDetailsById(Long customerId);
}
