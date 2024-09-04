package com.antonio.curso.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.antonio.curso.springboot.jpa.springboot_jpa.dto.PersonDto;
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

		personalizedQueriesBetween();
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesBetween() {

		System.out.println("============== consulta por rangos id  ==============");
		List<Person> persons = repository.findByIdBetweenOrderByNameAsc(2L,5L);
		persons.forEach(System.out::println);

		System.out.println("============== consulta por rangos letras nombre  ==============");
		persons = repository.findByNameBetweenOrderByNameDescLastnameDesc("J", "Q");
		persons.forEach(System.out::println);

		System.out.println("============== consulta ordenada por nombre  ==============");
		persons = repository.findAllByOrderByNameAscLastnameDesc();
		persons.forEach(System.out::println);
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesConcatUpperAndLowerCase() {

		System.out.println("============== consulta nombres y apellidos de personas ==============");
		List<String> names = repository.findAllFullNameConcat();
		names.forEach(System.out::println);

		System.out.println("============== consulta nombres y apellidos mayuscula ==============");
		names = repository.findAllFullNameConcatUpper();
		names.forEach(System.out::println);

		System.out.println("============== consulta nombres y apellidos minuscula ==============");
		names = repository.findAllFullNameConcatLower();
		names.forEach(System.out::println);

		System.out.println("============== consulta personalizada persona uper y lower case ==============");
		List<Object[]> regs = repository.findAllPersonDataListCase();
		regs.forEach(reg -> System.out
				.println("id=" + reg[0] + ", nombre=" + reg[1] + ", apellido" + reg[2] + ", lenguaje=" + reg[3]));

	}

	@Transactional(readOnly = true)
	public void personalizedQueriesDistinct() {

		System.out.println("============== consultas con nombres de personas ==============");
		List<String> names = repository.findAllNames();
		names.forEach(System.out::println);

		System.out.println("============== consultas con nombres unicos de personas ==============");
		names = repository.findAllNamesDistinct();
		names.forEach(System.out::println);

		System.out.println("============== consultas con lenguaje de prgramacion unicas ==============");
		List<String> languages = repository.findAllProgrammingLanguageDistinct();
		languages.forEach(System.out::println);

		System.out.println("============== consultas con total de lenguajes de prgramacion unicas ==============");
		Long totalLanguages = repository.findAllProgrammingLanguageDistinctCount();
		System.out.println("total de lenguajes de programacion: " + totalLanguages);
	}

	@Transactional(readOnly = true)
	public void personalizedQueries2() {

		System.out.println("============== consulta por objeto persona y leguaje de programacion ==============");
		List<Object[]> personsReg = repository.findAllMixPerson();
		
		personsReg.forEach(reg -> {
			System.out.println("programmingLanguage=" + reg[1] + ", person=" + reg[0]);
		});

		System.out.println("============== consulta que puebla y devuelve objeto entity de una instacia personalizada ==============");
		List<Person> persons = repository.findAllObjectPersonPersonalized();
		persons.forEach(System.out::println);

		System.out.println("============== consulta que puebla y devuelve objeto dto de una clase personalizada ==============");
		List<PersonDto> personsDto = repository.findAllPersonDto();
		personsDto.forEach(System.out::println);
	}

	@Transactional(readOnly = true)
	public void personalizedQueries() {

		Scanner scanner = new Scanner(System.in);

		System.out.println("============== consulta solo el nombre por el id ==============");
		System.out.println("Ingrese el id:");
		Long id = scanner.nextLong();
		scanner.close();

		System.out.println("============== consulta el nombre ==============");
		String name = repository.getNameById(id);
		System.out.println(name);

		System.out.println("============== consulta el id ==============");
		Long idDb = repository.getIdById(id);
		System.out.println(idDb);

		System.out.println("============== consulta el nombre completo con concat ==============");
		String fullName = repository.getFullNameById(id);
		System.out.println(fullName);

		System.out.println("============== Consulta por campos personalizados por el id ==============");
		Optional<Object> optionalReg = repository.obtenerPersonDataById(id);
		if (optionalReg.isPresent()) {
			Object[] personReg = (Object[]) optionalReg.orElseThrow();
			System.out.println("id=" + personReg[0] + ", nombre=" + personReg[1] + ", apellido" + personReg[2]
					+ ", lenguaje=" + personReg[3]);
		}
		System.out.println("============== Consulta por campos personalizados lista ==============");
		List<Object[]> regs = repository.obtenerPersonDataList();
		regs.forEach(reg -> System.out
				.println("id=" + reg[0] + ", nombre=" + reg[1] + ", apellido" + reg[2] + ", lenguaje=" + reg[3]));
	}

	@Transactional
	public void delete2() {

		repository.findAll().forEach(System.out::println);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id a eliminar:");
		Long id = scanner.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);
		optionalPerson.ifPresentOrElse(repository::delete,
				() -> System.out.println("Lo sentimos no existe la persona con ese id!"));

		repository.findAll().forEach(System.out::println);

		scanner.close();
	}

	@Transactional
	public void delete() {

		repository.findAll().forEach(System.out::println);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id a eliminar:");
		Long id = scanner.nextLong();
		repository.deleteById(id);

		repository.findAll().forEach(System.out::println);

		scanner.close();
	}

	@Transactional
	public void update() {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona:");
		Long id = scanner.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);

		// optionalPerson.ifPresent(person -> {
		if (optionalPerson.isPresent()) {
			Person personDb = optionalPerson.orElseThrow();

			System.out.println(personDb);
			System.out.println("Ingrese el lenguaje de programacion:");
			String programmingLanguage = scanner.next();
			personDb.setProgrammingLanguage(programmingLanguage);
			Person personUpdate = repository.save(personDb);
			System.out.println(personUpdate);
		} else {
			System.out.println("El usuario no esta presente! no existe!");
		}
		// });

		scanner.close();

	}

	@Transactional
	public void create() {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el nombre:");
		String name = scanner.next();
		System.out.println("Ingrese el apellido:");
		String lastName = scanner.next();
		System.out.println("Ingrese el lenguaje de programacion: ");
		String programmingLanguage = scanner.next();
		scanner.close();

		Person person = new Person(null, name, lastName, programmingLanguage);

		Person personNew = repository.save(person);
		System.out.println(personNew);

		repository.findById(personNew.getId()).ifPresent(p -> System.out.println(p));
	}

	@Transactional(readOnly = true)
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

		// Cuando se pasa un solo argumento el sout se puede abreviar
		repository.findByNameContaining("hn").ifPresent(System.out::println);
	}

	@Transactional(readOnly = true)
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
