package br.com.erudio.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.data.vo.v2.PersonVOV2;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.mapper.custom.PersonMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;

//Para que serve a anotation @Service?
//serve para que o spring Boot encare esse cara como um objeto q será injetado em outras classes da nossa aplicação.
//Integrando a API ao banco

@Service //Poderia ser @Componet tbm q daria no mesmo e funcionaria
public class PersonServices {
	
	
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	@Autowired
	PersonMapper mapper;
	
	public List <PersonVO> findAll(){
		
		logger.info("Finding all people!");
		
		return DozerMapper.parseListObject(repository.findAll(), PersonVO.class);
	}

	public PersonVO findById(Long id){
		
		logger.info("Finding one person!");

		
		var entity = repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!")); //Msg qdo n achear o Id		
		return DozerMapper.parseObject(entity, PersonVO.class); 
	}
	
	public PersonVO create(PersonVO person) {
		
		logger.info("Creating one person!");
		var entity = DozerMapper.parseObject(person, Person.class);
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class); 
		return vo;
	}
	
	public PersonVOV2 createV2(PersonVOV2 person) {
		
		logger.info("Creating one person V2!");	
		var entity = mapper.convertVoTOEntity(person);
		var vo = mapper.convertEntityToVo(repository.save(entity)); 
		return vo;
	}
	
	public PersonVO update(PersonVO person) {
		
		logger.info("Updating one person!");
		var entity = repository.findById(person.getId())
		.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!")); //Msg qdo n achar o Id
	
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class); 
		return vo;
	}
	
	public void delete(Long id) {
		logger.info("Deleting one person!");
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!")); //Msg qdo n achar o Id
		
		repository.delete(entity);

	}
}
