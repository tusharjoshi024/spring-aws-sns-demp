package com.example.springsnsdemo.repository;

import com.example.springsnsdemo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
