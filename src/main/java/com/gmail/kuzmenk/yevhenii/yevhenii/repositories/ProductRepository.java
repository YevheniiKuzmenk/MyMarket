package com.gmail.kuzmenk.yevhenii.yevhenii.repositories;

import com.gmail.kuzmenk.yevhenii.yevhenii.models.Appointments;
import com.gmail.kuzmenk.yevhenii.yevhenii.models.Product;
import com.gmail.kuzmenk.yevhenii.yevhenii.models.Trademark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT c FROM Product c WHERE c.trademark = :trademark")
    Iterable<Product> findByTrademark(@Param("trademark") Optional<Trademark> trademark);

    @Query("SELECT c FROM Product c WHERE c.appointments = :appointments")
    Iterable<Product> findByAppointments(@Param("appointments") Optional<Appointments> appointments);

    @Query("SELECT c FROM Product c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :pattern, '%'))")
    Iterable<Product> findProductByPattern(@Param("pattern") String pattern);
}
