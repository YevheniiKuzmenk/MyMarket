package com.gmail.kuzmenk.yevhenii.yevhenii.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class ProductNumber {

    @EmbeddedId
    private ProductNumberKey id = new ProductNumberKey();

    @ManyToOne
    @MapsId("customerId")
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;


    private int number;

    public ProductNumber(Customer customer, Product product, int number) {
        this.customer = customer;
        this.product = product;
        this.number = number;
    }


}
