package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.dto.CustomerDTO;
import com.toth_almos.hotelreservationsystem.model.Customer;

public interface UserService {
    public CustomerDTO getCustomerDetails(Long customerId);
    public CustomerDTO updateCustomerDetails(Long customerId, Customer customer);
}
