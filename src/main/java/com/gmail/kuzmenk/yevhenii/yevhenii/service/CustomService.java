package com.gmail.kuzmenk.yevhenii.yevhenii.service;

import com.gmail.kuzmenk.yevhenii.yevhenii.CustomerRole;
import com.gmail.kuzmenk.yevhenii.yevhenii.models.Customer;
import com.gmail.kuzmenk.yevhenii.yevhenii.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomService {
    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Customer getUserByLogin(String login) {
        return customerRepository.findCustomerByLogin(login);
    }

    @Transactional
    public void customerAdd(Customer customer) {
        customerRepository.save(customer);
    }

    @Transactional
    public void addCcustomer(String phoneNumber, String username, String password, String firstName, String lastName, String email) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String passHash = encoder.encode(password);
        Customer customer = new Customer(phoneNumber, username, passHash, firstName, lastName, email, CustomerRole.ADMIN);
        customerRepository.save(customer);
    }

    @Transactional
    public boolean existsByLogin(String login) {
        return customerRepository.existsByLogin(login);
    }

    @Transactional
    public Iterable<Customer> findAllCustomer() {
        return customerRepository.findAll();
    }

    @Transactional
    public Customer findCustomerById(long id) {
        return customerRepository.getById(id);
    }


    @Transactional
    public void customerDelete(long id) {
        customerRepository.deleteById(id);
    }


}
