package br.com.erudio.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.erudio.controllers.PersonController;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.repositories.UserRepository;

//Para que serve a anotation @Service?
//serve para que o spring Boot encare esse cara como um objeto q será injetado em outras classes da nossa aplicação.
//Integrando a API ao banco

@Service //Poderia ser @Componet tbm q daria no mesmo e funcionaria
public class UserServices implements UserDetailsService{

	private Logger logger = Logger.getLogger(UserServices.class.getName());
	
	@Autowired //Usaremos a injeção de dependência via propriedade e não por construtor
	UserRepository repository; //Aqui é a injeção do repository
	
	//Seleciona a opção Source/Construtor using Fields
	public UserServices(UserRepository repository) {
		this.repository = repository;		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Finding one user by name " + username + "!"); //apenas para questão de log, não obrigatório
		var user = repository.findByUsername(username);
		if (user != null) {
			return user;
		}else {
			throw new UsernameNotFoundException("Username " + username + "not found!");
		}
	}
}
