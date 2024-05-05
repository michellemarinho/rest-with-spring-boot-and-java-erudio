package br.com.erudio.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.erudio.controllers.PersonController;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
//import br.com.erudio.mapper.custom.PersonMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;
import jakarta.transaction.Transactional;

//Para que serve a anotation Service?
//serve para que o spring Boot encare esse cara como um objeto q sera injetado em outras classes da nossa aplicacao.
//Integrando a API ao banco

@Service //Poderia ser anotation Componet tbm q daria no mesmo e funcionaria
public class PersonServices {
	
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;
	
//	@Autowired
//	PersonMapper mapper;
	
	public List <PersonVO> findAll(){
		
		logger.info("Finding all people!");
		
		var persons = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
		persons
			.stream()
			.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		return persons;
	}

	public PersonVO findById(Long id){
		
		logger.info("Finding one person!");

		//Qdo acessamos o findbyid nossa classe vai ao bd e acessa o repositorio e busca o objeto por id
		var entity = repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!")); //Msg qdo n achar o Id		
		var vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel()); //SelfRel auto relacionamento
		return vo;	
	}
	
	//o create recebe um PersonVO que eh convertido em entidade q vai ao banco e ao final eh devolvido como vo
	public PersonVO create(PersonVO person) { 
		
		if(person == null)throw new RequiredObjectIsNullException();
		
		logger.info("Creating one person!");
		
		var entity = DozerMapper.parseObject(person, Person.class);
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class); 
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
//	public PersonVOV2 createV2(PersonVOV2 person) {
//		
//		logger.info("Creating one person V2!");	
//		var entity = mapper.convertVoTOEntity(person);
//		var vo = mapper.convertEntityToVo(repository.save(entity)); 
//		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel()); //SelfRel auto-relacionamento
//		return vo;
//		
//	}
	
	public PersonVO update(PersonVO person) {
		
		if(person == null)throw new RequiredObjectIsNullException();
		
		logger.info("Updating one person!");
		
		var entity = repository.findById(person.getKey())
		.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
	
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class); 
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	//Na aula 181 foi incluido o metodo de desabilitar a pessoa a partir de um parametro passado (id)
	@Transactional
	public PersonVO disablePerson(Long id) {
		
		logger.info("Disabling one person!");
		
		repository.disablePerson(id);
		
		var entity = repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		var vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
	}
		
	public void delete(Long id) { //void não tem retorno
		
		logger.info("Deleting one person!");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!")); 
		repository.delete(entity);
	}
}
