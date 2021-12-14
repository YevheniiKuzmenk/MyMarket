package com.gmail.kuzmenk.yevhenii.yevhenii.repositories;

import com.gmail.kuzmenk.yevhenii.yevhenii.models.Trademark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TrademarkRepository extends JpaRepository<Trademark, Long> {

    @Query("SELECT u from Trademark u where u.name=:name")
    Optional<Trademark> findTrademarkByName(@Param("name") String name);
}
