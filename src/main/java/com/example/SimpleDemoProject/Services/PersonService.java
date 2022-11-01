package com.example.SimpleDemoProject.Services;

import com.example.SimpleDemoProject.Common.StringConstants;
import com.example.SimpleDemoProject.Converters.PersonMapper;
import com.example.SimpleDemoProject.Models.DTOs.PersonResponse;
import com.example.SimpleDemoProject.Models.Person;
import com.example.SimpleDemoProject.Repositories.PersonRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class PersonService {

    PersonMapper mapper;
    PersonRepository personRepository;

    public PersonResponse findById(Integer id) throws EntityNotFoundException {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(StringConstants.PERSON_NOT_FOUND, id)));

        return mapper.toResponse(person);
    }
}
