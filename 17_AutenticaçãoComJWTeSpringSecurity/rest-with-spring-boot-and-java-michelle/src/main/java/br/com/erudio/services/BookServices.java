package br.com.erudio.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.controllers.BookController;
import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Book;
import br.com.erudio.repositories.BookRepository;

//Para que serve a anotation @Service?
//serve para que o spring Boot encare esse cara como um objeto q será injetado em outras classes da nossa aplicação.
//Integrando a API ao banco

@Service
public class BookServices {
	
	
	private Logger logger = Logger.getLogger(BookServices.class.getName());
	
	@Autowired
	BookRepository repository;
	
	public List <BookVO> findAll(){
		
		logger.info("Finding all book!");
		
		var books = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);
		books
			.stream()
			.forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
		return books;
	}

	public BookVO findById(Long id){
		
		logger.info("Finding one book!");

		//Qdo acessamos o findbyid nossa classe vai ao bd e acessa o repositório e busca o objeto por id
		var entity = repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!")); //Msg qdo n achar o Id		
		var vo = DozerMapper.parseObject(entity, BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel()); //SelfRel auto-relacionamento
		return vo;
		
	}
	
	public BookVO create(BookVO book) { //o create recebe um BookVO que é convertido em entidade q vai ao banco e ao final é devolvido como vo
		
		if(book == null)throw new RequiredObjectIsNullException();
		logger.info("Creating one book!");
		var entity = DozerMapper.parseObject(book, Book.class);
		var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class); 
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel()); //SelfRel auto-relacionamento
		return vo;
	}
	
	public BookVO update(BookVO book) {
		
		if(book == null)throw new RequiredObjectIsNullException();
		
		logger.info("Updating one book!");
		var entity = repository.findById(book.getKey())
		.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!")); //Msg qdo n achar o Id
	
		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());
		
		var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class); 
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel()); //SelfRel auto-relacionamento
		return vo;
	}
	
	public void delete(Long id) { //void não tem retorno
		logger.info("Deleting one book!");
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!")); //Msg qdo n achar o Id
		
		repository.delete(entity);

	}
}
