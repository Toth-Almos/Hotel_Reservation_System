package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.dto.CustomerDTO;

public interface UserService {
    public CustomerDTO getCustomerDetails(Long customerId);
}
