package com.gmail.kuzmenk.yevhenii.yevhenii.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@Data
public class ProductNumberKey implements Serializable {


    @Column(name = "customer_id")
    private Long customerId;


    @Column(name = "product_id")
    private Long productId;
}
