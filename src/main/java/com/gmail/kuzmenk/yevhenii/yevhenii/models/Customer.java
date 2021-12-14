package com.gmail.kuzmenk.yevhenii.yevhenii.models;

import com.gmail.kuzmenk.yevhenii.yevhenii.CustomerRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "customer")
    private Set<ProductNumber> numbers;
    @OneToMany(mappedBy = "customer")
    private Set<Orderr> numberss;

    private String phoneNumber;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;

    @Enumerated(EnumType.STRING)
    private CustomerRole role;

    public Customer(String login, String password, CustomerRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Customer(String phoneNumber, String login, String password) {

        this.phoneNumber = phoneNumber;
        this.login = login;
        this.password = password;
    }

    public Customer(String phoneNumber, String login, String password, String firstName, String lastName, String email, CustomerRole role) {
        this.phoneNumber = phoneNumber;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }
}
