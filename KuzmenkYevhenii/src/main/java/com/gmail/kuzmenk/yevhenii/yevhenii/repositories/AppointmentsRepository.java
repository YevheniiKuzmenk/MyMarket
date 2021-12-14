package com.gmail.kuzmenk.yevhenii.yevhenii.repositories;

import com.gmail.kuzmenk.yevhenii.yevhenii.models.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AppointmentsRepository extends JpaRepository<Appointments, Long> {

    @Query("SELECT u from Appointments u where u.name=:name")
    Optional<Appointments> findAppointmentsByName(@Param("name") String name);
}



