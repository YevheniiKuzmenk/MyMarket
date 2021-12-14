package com.gmail.kuzmenk.yevhenii.yevhenii.models;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Data
public class Orderr {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int number;
    private Date invoice;

    public Orderr(Customer customer, Product product, int number, Date invoice) {
        this.invoice = invoice;
        this.customer = customer;
        this.product = product;
        this.number = number;
    }
}
