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

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.model.Book;
import br.com.erudio.repositories.BookRepository;
import br.com.erudio.services.BookServices;
import br.com.erudio.unittests.mapper.mocks.MockBook;

@TestInstance(Lifecycle.PER_CLASS) //haverá uma instância por classe
@ExtendWith(MockitoExtension.class)//recebe como parâmetro o MockitoExtension
class BookServicesTest {

	//Mockar uma entrada do objeto book chamado input
	MockBook input;
	
	@InjectMocks //Injetar os mocks
	private BookServices service; //o Book Service não acessará o repositório real , e sim o mock
	
	//Mock pro repository
	@Mock
	BookRepository repository;
	
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockBook();
		MockitoAnnotations.openMocks(this);	//Abrir os mocks, setar os mocks em 
	}

	@Test
	//Nesse teste, ao mockar a expressão "repository.findById(id)", será recebido um link c um padrão específico.
	void testFindById() {
		Book entity = input.mockEntity(1); //retorna a book, mas não trás o Id, pois não foi persistida ainda
		entity.setId(1L); //Seta a entidade --> lembrar: 1L converte pra Long
		//abaixo ocorre o seguinte: qdo o repository.findById for chamado, não vai ao banco, o mockito interceptará e retornará um mock
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L); //vai executar o código e no lugar do repositorio vai injetar o mock
		assertNotNull(result);//garante q não será null
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		
		assertNotNull(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Some Author1", result.getAuthor());
		assertEquals("Some Title1", result.getTitle());
		assertEquals(25D, result.getPrice());
		assertNotNull(result.getLaunchDate()); //nesse teste apenas verificamos se a data não está nula
	}


	@Test
	void testCreate() {
		Book entity = input.mockEntity(1); //retorna a book, mas não trás o Id, pois não foi persistida ainda, mas a devolução dela já terá id simulando o acesso ao banco
		entity.setId(1L);
		
		Book persisted = entity; //Aqui já após buscar o id do repositório
		persisted.setId(1L);
		
		BookVO vo = input.mockVO(1);
		vo.setKey(1L);

		//abaixo ocorre o seguinte: qdo o repository.findById for chamado, não vai ao banco, o mockito intercepta e retornar um mock
		when(repository.save(entity)).thenReturn(persisted); //qdo salvar, retorna a minha entidade persistida no bd
	
		var result = service.create(vo); //vai executar o código e no lugar do repositorio vai injetar o mock
		assertNotNull(result);//garante q não será null
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		
		assertNotNull(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Some Author1", result.getAuthor());
		assertEquals("Some Title1", result.getTitle());
		assertEquals(25D, result.getPrice());
		assertNotNull(result.getLaunchDate()); //nesse teste apenas verificamos se a data não está nula
	
	}
	
	@Test
	void testCreateWithNullBook() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		String expectedMessage = "";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage)); //as mensagens serão comparadas e retornará a msg definida na classe RequiredObjectIsNullException
	}

	@Test
	void testUpdate() {
		Book entity = input.mockEntity(1); //retorna a book, mas não trás o Id, pois não foi persistida ainda, mas a devolução dela já terá id simulando o acesso ao banco
		
		Book persisted = entity; //Aqui já após buscar o id do repositório
		persisted.setId(1L);
		
		BookVO vo = input.mockVO(1);
		vo.setKey(1L);

		//abaixo ocorre o seguinte: qdo o repository.findById for chamado, não vai ao banco, o mockito intercepta e retornar um mock
		when(repository.findById(1L)).thenReturn(Optional.of(entity)); //retorna o valor para na sequencia persistir, ou seja permitir alterar
		when(repository.save(entity)).thenReturn(persisted); //qdo salvar, retorna a minha entidade persistida no bd
	
		var result = service.update(vo); //vai executar o código e no lugar do repositorio vai injetar o mock
		assertNotNull(result);//garante q não será null
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		
		assertNotNull(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Some Author1", result.getAuthor());
		assertEquals("Some Title1", result.getTitle());
		assertEquals(25D, result.getPrice());
		assertNotNull(result.getLaunchDate()); //nesse teste apenas verificamos se a data não está nula
	}

	@Test
	void testUpdateWithNullBook() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.update(null);
		});
		String expectedMessage = "";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage)); //as mensagens serão comparadas e retornará a msg definida na classe RequiredObjectIsNullException
	}
	
	@Test
	void testDelete() {
		Book entity = input.mockEntity(1); //retorna a book, mas não trás o Id, pois não foi persistida ainda, mocka o ById
		entity.setId(1L); //Seta a entidade --> lembrar: 1L converte pra Long
		//abaixo ocorre o seguinte: qdo o repository.findById for chamado, não vai ao banco, o mockito interceptar e retornar um mock
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		service.delete(1L);
	}
	
	@Test
	void testFindAll() {
		//A partir de uma lista de entidades, garantir q tenham id, não sejam nulos e tenham os valores esperados
		List<Book> list = input.mockEntityList(); //mocka uma lista de entidades, mas não trás o Id, pois não foi persistida ainda
		
		//abaixo ocorre o seguinte: qdo o repository.findByAll for chamado, não vai ao banco, o mockito intercepta e retorna um mock
		when(repository.findAll()).thenReturn(list);
		
		var people = service.findAll(); //vai executar o código e no lugar do repositorio vai injetar o mock
		
		assertNotNull(people);//garante q não será null;
		assertEquals(14, people.size()); //14 é o tamanho do for na classe MockBook
		
		var bookOne = people.get(1); //vai executar o código e no lugar do repositorio vai injetar o mock
		
		assertNotNull(bookOne);//garante q não será null
		assertNotNull(bookOne.getKey());
		assertNotNull(bookOne.getLinks());
		
		assertNotNull(bookOne.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Some Author1", bookOne.getAuthor());
		assertEquals("Some Title1", bookOne.getTitle());
		assertEquals(25D, bookOne.getPrice());
		assertNotNull(bookOne.getLaunchDate()); //nesse teste apenas verificamos se a data não está nula
		
		var bookFour = people.get(4); //vai executar o código e no lugar do repositorio vai injetar o mock
		
		assertNotNull(bookFour);//garante q não será null
		assertNotNull(bookFour.getKey());
		assertNotNull(bookFour.getLinks());
		
		assertNotNull(bookFour.toString().contains("links: [</api/book/v1/4>;rel=\"self\"]"));
		assertEquals("Some Author4", bookFour.getAuthor());
		assertEquals("Some Title4", bookFour.getTitle());
		assertEquals(25D, bookFour.getPrice());
		assertNotNull(bookFour.getLaunchDate()); //nesse teste apenas verificamos se a data não está nula
		
		var bookSeven = people.get(7); //vai executar o código e no lugar do repositorio vai injetar o mock
		
		assertNotNull(bookSeven);//garante q não será null
		assertNotNull(bookSeven.getKey());
		assertNotNull(bookSeven.getLinks());
		
		assertNotNull(bookSeven.toString().contains("links: [</api/book/v1/7>;rel=\"self\"]"));
		assertEquals("Some Author7", bookSeven.getAuthor());
		assertEquals("Some Title7", bookSeven.getTitle());
		assertEquals(25D, bookSeven.getPrice());
		assertNotNull(bookSeven.getLaunchDate()); //nesse teste apenas verificamos se a data não está nula
	}

}
