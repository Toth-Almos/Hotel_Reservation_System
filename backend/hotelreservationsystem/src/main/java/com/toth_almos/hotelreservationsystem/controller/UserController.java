package com.toth_almos.hotelreservationsystem.controller;

import com.toth_almos.hotelreservationsystem.dto.CustomerDTO;
import com.toth_almos.hotelreservationsystem.model.Customer;
import com.toth_almos.hotelreservationsystem.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerDetails(@PathVariable("customerId") Long customerId) {
        CustomerDTO customerDTO = userService.getCustomerDetails(customerId);
        return ResponseEntity.ok(customerDTO);
    }

    @PatchMapping("/update-profile/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomerDetails(@PathVariable("customerId") Long customerId, @RequestBody Customer customer) {
        CustomerDTO customerDTO = userService.updateCustomerDetails(customerId, customer);
        return ResponseEntity.ok(customerDTO);
    }
}
