package com.example.SimpleDemoProject.Converters;

import com.example.SimpleDemoProject.Common.Utility;
import com.example.SimpleDemoProject.Models.DTOs.PersonResponse;
import com.example.SimpleDemoProject.Models.Person;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonMapper {

    private final ModelMapper modelMapper;

    public PersonResponse toResponse(Person person) {
        PersonResponse personResponse = modelMapper.map(person, PersonResponse.class);
        personResponse.setAge(Utility.getAgeByDateOfBirth(person.getBirthDate()));

        return personResponse;
    }
}
