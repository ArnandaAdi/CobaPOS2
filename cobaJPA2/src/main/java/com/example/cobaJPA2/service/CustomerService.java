package com.example.cobaJPA2.service;

import com.example.cobaJPA2.dto.CustomerRequest;
import com.example.cobaJPA2.entity.Customer;
import com.example.cobaJPA2.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
@Autowired
private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findCustomersByCustomerNameJPQL(String name) {
        return customerRepository.findCustomersByCustomerNameJPQL(name);
    }

    public List<Customer> findCustomersByCustomerNameNative() {
        return customerRepository.findCustomersByCustomerNameNative();
    }

    public void insertCustomerJPQL(String name, Integer phoneNumber) {
        customerRepository.insertCustomerJPQL(name, phoneNumber);
    }

    public void insertCustomerNative(String name, Integer phoneNumber) {
        customerRepository.insertCustomerNative(name, phoneNumber);
    }

}
