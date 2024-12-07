package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.domain.CustomerDetails;
import com.example.spacecatsmarket.dto.customer.CustomerDetailsDto;
import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<CustomerDetails> getAllCustomerDetails();

    CustomerDetails getCustomerDetailsById(UUID customerId);

    CustomerDetails createCustomerDetails(CustomerDetailsDto customerDetailsDto);

    void deleteCustomerDetailsById(UUID customerId);
}
