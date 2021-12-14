package com.gmail.kuzmenk.yevhenii.yevhenii.repositories;

import com.gmail.kuzmenk.yevhenii.yevhenii.models.Customer;
import com.gmail.kuzmenk.yevhenii.yevhenii.models.Product;
import com.gmail.kuzmenk.yevhenii.yevhenii.models.ProductNumber;
import com.gmail.kuzmenk.yevhenii.yevhenii.models.ProductNumberKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductNumberRepository extends JpaRepository<ProductNumber, Long> {

    @Query("SELECT c FROM ProductNumber c WHERE c.customer = :customer")
    List<ProductNumber> findAllByCustomer(@Param("customer") Customer customer);

    @Query("SELECT c FROM ProductNumber c WHERE c.product = :product and c.customer = :customer")
    ProductNumber findProductNumberByProduct(@Param("product") Product product, @Param("customer") Customer customer);

    @Query("SELECT c FROM ProductNumber c WHERE c.id = :productNumberKey")
    ProductNumber findProductNumberById(@Param("productNumberKey") ProductNumberKey productNumberKey);


}
