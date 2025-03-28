package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.model.Customer;

public interface UserService {
    public Customer getCustomerDetails(Long customerId);
    public Customer updateCustomerDetails(Long customerId, Customer customer);
}
