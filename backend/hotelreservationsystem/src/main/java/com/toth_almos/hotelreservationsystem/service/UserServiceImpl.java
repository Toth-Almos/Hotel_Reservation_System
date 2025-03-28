package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.model.Customer;
import com.toth_almos.hotelreservationsystem.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Customer getCustomerDetails(Long customerId) {
        return userRepository.findByCustomerId(customerId).orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + customerId));
    }

    @Override
    public Customer updateCustomerDetails(Long customerId, Customer customer) {
        Customer existingCustomer = userRepository.findByCustomerId(customerId).orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + customerId));
        existingCustomer.setUsername(customer.getUsername());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPhoneNumber(customer.getPhoneNumber());
        existingCustomer.setAddress(customer.getAddress());
        userRepository.save(existingCustomer);
        return existingCustomer;
    }
}
