package com.gmail.kuzmenk.yevhenii.yevhenii.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Trademark {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private List<Product> products = new ArrayList<Product>();

    private String name;

    public Trademark(String name) {
        this.name = name;
    }
}
