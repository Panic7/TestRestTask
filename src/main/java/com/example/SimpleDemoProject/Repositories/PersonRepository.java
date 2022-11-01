package com.example.SimpleDemoProject.Repositories;

import com.example.SimpleDemoProject.Models.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {
}
