package com.example.cobaJPA2.controller;

import com.example.cobaJPA2.dto.CustomerRequest;
import com.example.cobaJPA2.entity.Customer;
import com.example.cobaJPA2.repository.CustomerRepository;
import com.example.cobaJPA2.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping("/jpql")
    public List<Customer> findCustomersByCustomerNameJPQL(@RequestParam String name) {
        return customerService.findCustomersByCustomerNameJPQL(name);
    }

    @GetMapping("/native")
    public List<Customer> findCustomersByCustomerNameNative() {
        return customerService.findCustomersByCustomerNameNative();
    }

    @PostMapping("/jpql/insert")
    public void insertCustomerJPQL(@RequestParam String name, @RequestParam Integer phoneNumber) {
        customerService.insertCustomerJPQL(name, phoneNumber);
    }

    @PostMapping("/native/insert")
    public void insertCustomerNative(@RequestParam String name, @RequestParam Integer phoneNumber) {
        customerService.insertCustomerNative(name, phoneNumber);
    }
}
