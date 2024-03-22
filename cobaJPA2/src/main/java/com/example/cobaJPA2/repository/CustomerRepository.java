package com.example.cobaJPA2.repository;

import com.example.cobaJPA2.entity.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.customerName = :name")
    List<Customer> findCustomersByCustomerNameJPQL(String name);

    @Query(value = "SELECT * FROM customers", nativeQuery = true)
    List<Customer> findCustomersByCustomerNameNative();

    @Modifying
    @Query("INSERT INTO Customer (customerName, phoneNumber) VALUES (:name, :phoneNumber)")
    void insertCustomerJPQL(String name, Integer phoneNumber);

    @Modifying
    @Query(value = "INSERT INTO customers (customer_name, phone_number) VALUES (:name, :phoneNumber)", nativeQuery = true)
    void insertCustomerNative(String name, Integer phoneNumber);
}



