package com.antonio.curso.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.antonio.curso.springboot.jpa.springboot_jpa.entities.Person;
import com.antonio.curso.springboot.jpa.springboot_jpa.repositories.PersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		findOne();
	}

	public void findOne() {

		/*
		 * Person person = null;
		 * Optional<Person> optionalPerson = repository.findById(8L);
		 * * if(!optionalPerson.isEmpty()) {
		 * if(optionalPerson.isPresent()) {
		 * person = optionalPerson.get();
		 * }
		 * System.out.println(person);
		 */

		 // repository.findById(1L).ifPresent(System.out::println);

		 repository.findByNameContaining("hn").ifPresent(System.out::println);
	}

	public void list() {
		// List<Person> persons = (List<Person>) repository.findAll();
		// List<Person> persons = (List<Person>)
		// repository.findByProgrammingLanguage("javaScript");

		// List<Person> persons = (List<Person>)
		// repository.buscarByProgrammingLanguage("Python", "Pepe");
		List<Person> persons = (List<Person>) repository.findByProgrammingLanguageAndName("java", "Andres");

		persons.stream().forEach(person -> System.out.println(person));

		List<Object[]> personsValues = repository.obtenerPersonDataByProgrammingLanguage("java");
		personsValues.stream().forEach(person -> System.out.println(person[0] + " es experto en " + person[1]));

	}

}
