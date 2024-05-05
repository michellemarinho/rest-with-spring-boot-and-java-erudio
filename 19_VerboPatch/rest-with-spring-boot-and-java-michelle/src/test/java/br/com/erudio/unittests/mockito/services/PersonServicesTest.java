package br.com.erudio.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;
import br.com.erudio.services.PersonServices;
import br.com.erudio.unittests.mapper.mocks.MockPerson;

@TestInstance(Lifecycle.PER_CLASS) //haverá uma instância por classe
@ExtendWith(MockitoExtension.class)//recebe como parÂmetro o MockitoExtension
class PersonServicesTest {

	//Mockar uma entrada do objeto person chamado input
	MockPerson input;
	
	@InjectMocks //Injetar os mocks
	private PersonServices service; //o PErson Service não acessará o repositório real , e sim o mock
	
	//Mock pro repository
	@Mock
	PersonRepository repository;
	
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockPerson();
		MockitoAnnotations.openMocks(this);	//Abrir os mocks, setar os mocks em 
	}

	@Test
	//Nesse teste, ao mockar a expressão "repository.findById(id)", será recbido um link c um padrão específico.
	void testFindById() {
		Person entity = input.mockEntity(1); //retorna a person, mas não trás o Id, pois não foi persistida ainda
		entity.setId(1L); //Seta a entidade --> lembrar: 1L converte pra Long
		//abaixo ocorre o seguinte: qdo o repository.findById for chamado, não vai ao banco, o mockito interceptar e retornar um mock
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L); //vai executar o código e no lugar do repositorio vai injetar o mock
		assertNotNull(result);//garante q não será null
		assertNotNull(result.getKey());
		assertNotNull(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
	}


	@Test
	void testCreate() {
		Person entity = input.mockEntity(1); //retorna a person, mas não trás o Id, pois não foi persistida ainda, mas a devolução dela já terá id simulando o acesso ao banco
		
		Person persisted = entity; //Aqui já após buscar o id do repositório
		persisted.setId(1L);
		
		PersonVO vo = input.mockVO(1);
		vo.setKey(1L);

		//abaixo ocorre o seguinte: qdo o repository.findById for chamado, não vai ao banco, o mockito intercepta e retornar um mock
		when(repository.save(entity)).thenReturn(persisted); //qdo salvar, retorna a minha entidade persistida no bd
	
		var result = service.create(vo); //vai executar o código e no lugar do repositorio vai injetar o mock
		assertNotNull(result);//garante q não será null
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertNotNull(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
	
	}
	
	void testCreatWithNullPerson() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		String expectedMessage = "";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage)); //as mensagens serão comparadas e retornará a msg definida na classe RequiredObjectIsNullException
	}

	@Test
	void testUpdate() {
		Person entity = input.mockEntity(1); //retorna a person, mas não trás o Id, pois não foi persistida ainda, mas a devolução dela já terá id simulando o acesso ao banco
		entity.setId(1L);
		
		Person persisted = entity; //Aqui já após buscar o id do repositório
		persisted.setId(1L);
		
		PersonVO vo = input.mockVO(1);
		vo.setKey(1L);

		//abaixo ocorre o seguinte: qdo o repository.findById for chamado, não vai ao banco, o mockito intercepta e retornar um mock
		when(repository.findById(1L)).thenReturn(Optional.of(entity)); //retorna o valor para na sequencia persistir, ou seja permitir alterar
		when(repository.save(entity)).thenReturn(persisted); //qdo salvar, retorna a minha entidade persistida no bd
	
		var result = service.update(vo); //vai executar o código e no lugar do repositorio vai injetar o mock
		assertNotNull(result);//garante q não será null
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertNotNull(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
	}

	void testUpdateWithNullPerson() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.update(null);
		});
		String expectedMessage = "";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage)); //as mensagens serão comparadas e retornará a msg definida na classe RequiredObjectIsNullException
	}
	
	@Test
	void testDelete() {
		Person entity = input.mockEntity(1); //retorna a person, mas não trás o Id, pois não foi persistida ainda, mocka o ById
		entity.setId(1L); //Seta a entidade --> lembrar: 1L converte pra Long
		//abaixo ocorre o seguinte: qdo o repository.findById for chamado, não vai ao banco, o mockito interceptar e retornar um mock
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		service.delete(1L);
	}
	
	@Test
	void testFindAll() {
		//A partir de uma lista de entidades, garantir q tenham id, não sejam nulos e tenham os valores esperados
		List<Person> list = input.mockEntityList(); //mocka uma lista de entidades, mas não trás o Id, pois não foi persistida ainda
		
		//abaixo ocorre o seguinte: qdo o repository.findByAll for chamado, não vai ao banco, o mockito intercepta e retorna um mock
		when(repository.findAll()).thenReturn(list);
		
		var people = service.findAll(); //vai executar o código e no lugar do repositorio vai injetar o mock
		assertNotNull(people);//garante q não será null;
		assertEquals(14, people.size()); //14 é o tamanho do for na classe MockPerson
		
		var personOne = people.get(1); //vai executar o código e no lugar do repositorio vai injetar o mock
		assertNotNull(personOne);//garante q não será null
		assertNotNull(personOne.getKey());
		assertNotNull(personOne.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test1", personOne.getAddress());
		assertEquals("First Name Test1", personOne.getFirstName());
		assertEquals("Last Name Test1", personOne.getLastName());
		assertEquals("Female", personOne.getGender());
		
		var personFour = people.get(4); //vai executar o código e no lugar do repositorio vai injetar o mock
		assertNotNull(personFour);//garante q não será null
		assertNotNull(personFour.getKey());
		assertNotNull(personFour.toString().contains("links: [</api/person/v1/4>;rel=\"self\"]"));
		assertEquals("Addres Test4", personFour.getAddress());
		assertEquals("First Name Test4", personFour.getFirstName());
		assertEquals("Last Name Test4", personFour.getLastName());
		assertEquals("Male", personFour.getGender());
		
		var personSeven = people.get(7); //vai executar o código e no lugar do repositorio vai injetar o mock
		assertNotNull(personSeven);//garante q não será null
		assertNotNull(personSeven.getKey());
		assertNotNull(personSeven.toString().contains("links: [</api/person/v1/7>;rel=\"self\"]"));
		assertEquals("Addres Test7", personSeven.getAddress());
		assertEquals("First Name Test7", personSeven.getFirstName());
		assertEquals("Last Name Test7", personSeven.getLastName());
		assertEquals("Female", personSeven.getGender());
	}

}
