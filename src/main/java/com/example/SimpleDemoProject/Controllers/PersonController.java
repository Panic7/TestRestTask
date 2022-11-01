package com.example.SimpleDemoProject.Controllers;

import com.example.SimpleDemoProject.Models.DTOs.PersonResponse;
import com.example.SimpleDemoProject.Services.PersonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/person")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
public class PersonController {

    PersonService personService;

    @GetMapping("/{id}")
    ResponseEntity<PersonResponse> person(@PathVariable Integer id) {
        PersonResponse person = personService.findById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(person);
    }
}
