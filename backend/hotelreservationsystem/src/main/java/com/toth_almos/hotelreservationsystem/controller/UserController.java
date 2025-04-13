package com.toth_almos.hotelreservationsystem.controller;

import com.toth_almos.hotelreservationsystem.dto.CustomerDTO;
import com.toth_almos.hotelreservationsystem.mapper.CustomerMapper;
import com.toth_almos.hotelreservationsystem.model.Customer;
import com.toth_almos.hotelreservationsystem.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {
    private final UserServiceImpl userService;
    private final CustomerMapper customerMapper;

    @Autowired
    public UserController(UserServiceImpl userService, CustomerMapper customerMapper) {
        this.userService = userService;
        this.customerMapper = customerMapper;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerDetails(@PathVariable("customerId") Long customerId) {
        Customer customer = userService.getCustomerDetails(customerId);
        return ResponseEntity.ok(customerMapper.toDTO(customer));
    }

    @PatchMapping("/update-profile/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomerDetails(@PathVariable("customerId") Long customerId, @RequestBody Customer customer) {
        Customer oldCustomer = userService.updateCustomerDetails(customerId, customer);
        return ResponseEntity.ok(customerMapper.toDTO(oldCustomer));
    }
}
