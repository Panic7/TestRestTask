package com.example.SimpleDemoProject;

import com.example.SimpleDemoProject.Common.StringConstants;
import com.example.SimpleDemoProject.Common.Utility;
import com.example.SimpleDemoProject.Converters.PersonMapper;
import com.example.SimpleDemoProject.Models.DTOs.PersonResponse;
import com.example.SimpleDemoProject.Models.Person;
import com.example.SimpleDemoProject.Services.PersonService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class SimpleDemoProjectApplicationTests {

	@Autowired
	MockMvc mvc;

	@Autowired
	PersonMapper mapper;

	@Autowired
	PersonService service;

	@Test
	void getAgeByDateOfBirth_returnCorrectAge(){
		Integer eighteenYears = Utility.getAgeByDateOfBirth(LocalDate.now().minusYears(18));
		Integer seventeenYears = Utility.getAgeByDateOfBirth(LocalDate.now().minusYears(18).plusDays(1));

		assertEquals(18, eighteenYears);
		assertEquals(17, seventeenYears);
	}

	@Test
	void convert_fromPerson_toPersonResponse(){
		Person person = new Person(
				1, "petro", "mostavchuk",
				LocalDate.of(2000,12,1));

		assertEquals(PersonResponse.class, mapper.toResponse(person).getClass());
	}

	@Test
	void convert_fromPersonToResponse_IsCorrectly(){
		Person person = new Person(
				1, "petro", "mostavchuk",
				LocalDate.of(2000,12,1));

		PersonResponse personResponse = mapper.toResponse(person);

		assertEquals(person.getId(), personResponse.getId());
		assertEquals(person.getName(), personResponse.getName());
		assertEquals(person.getSurname(), personResponse.getSurname());
		assertEquals(Utility.getAgeByDateOfBirth(person.getBirthDate()), personResponse.getAge());
	}

	@Test
	void whenGetPersonFound_thenReturnJson() throws Exception {
		PersonResponse person = service.findById(1);

		mvc.perform(get("/api/person/{id}", 1))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(person.getId()))
				.andExpect(jsonPath("$.name").value(person.getName()))
				.andExpect(jsonPath("$.surname").value(person.getSurname()))
				.andExpect(jsonPath("$.age").value(person.getAge()));
	}

	@Test
	void whenGetPersonNotFound_thenThrowException() throws Exception {
		Integer id = 0;
		String message = String.format(StringConstants.PERSON_NOT_FOUND, id);

		mvc.perform(get("/api/person/{id}", id))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
				.andExpect(result -> assertEquals(message, result.getResolvedException().getMessage()));

	}
}
