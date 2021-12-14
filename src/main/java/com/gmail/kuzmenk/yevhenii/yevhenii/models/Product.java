package com.gmail.kuzmenk.yevhenii.yevhenii.models;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trademark_id")
    private Trademark trademark;
    @ManyToOne
    @JoinColumn(name = "appointments_id")
    private Appointments appointments;
    @OneToMany(mappedBy = "product")
    private Set<ProductNumber> numbers;
    @OneToMany(mappedBy = "product")
    private Set<Orderr> numberss;


    private String name;
    private double weight;
    private double price;
    private String photoName;
    private String specification;

    public Product(Appointments appointments, Trademark trademark, String name, double weight, double price, String specification) {
        this.appointments = appointments;
        this.trademark = trademark;
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.specification = specification;
    }

    public Product(String name, double weight, double price) {
        this.name = name;
        this.weight = weight;
        this.price = price;
    }

    public Product(String name, double weight, double price, String photoName, String specification) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.photoName = photoName;
        this.specification = specification;

    }

    public Product(String name, double weight, double price, String specification) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.specification = specification;
    }
}
