package br.com.erudio.controllers;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.exceptions.UnsupportedMathOperationException;
import br.com.erudio.model.Person;
import br.com.erudio.services.PersonServices;

//contém vários métodos manipuladores
@RestController
@RequestMapping("/person")
public class PersonController {

	//private static final String template = "Hello, %s!";
	//private PersonServices service = new PersonServices();
	
	@Autowired //Com a inclusão do Autowired, o spring boot vai cuidar da instanciação de forma dinamica em tempo de exeução
	private PersonServices service;
	
	@RequestMapping(method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	//@PathVariable para retornar dados da url
	public List<Person> findAll () {
				return service.findAll();
		}
	
	@RequestMapping(value = "/{id}",
			method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Person findById (@PathVariable(value = "id")String id) {
				return service.findById(id);
		}
	
	@RequestMapping(method=RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Person create (@RequestBody Person person) {
				return service.create(person);
		}
	
	@RequestMapping(method=RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Person update (@RequestBody Person person) {
				return service.update(person);
		}
	
	@RequestMapping(value = "/{id}",
			method=RequestMethod.DELETE)
	public void delete (@PathVariable(value = "id")String id) {
				service.delete(id);
		}
}

//	@RequestMapping(value = "/subtraction/{numberOne}/{numberTwo}",method=RequestMethod.GET)
//
//	public Double subtraction ( 
//			@PathVariable(value = "numberOne")String numberOne,
//			@PathVariable(value = "numberTwo")String numberTwo
//	)throws Exception {
//		
//		if(!NumberConverter.isNumeric(numberOne)|| !NumberConverter.isNumeric(numberTwo)) { 
//			throw new UnsupportedMathOperationException("Please set a numeric value!");
//		}
//		
//		return math.subtraction(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
//		}
//	
//	@RequestMapping(value = "/multiplication/{numberOne}/{numberTwo}",method=RequestMethod.GET)
//
//	public Double multiplication ( 
//			@PathVariable(value = "numberOne")String numberOne,
//			@PathVariable(value = "numberTwo")String numberTwo
//	)throws Exception {
//		
//		if(!NumberConverter.isNumeric(numberOne)|| !NumberConverter.isNumeric(numberTwo)) { 
//			throw new UnsupportedMathOperationException("Please set a numeric value!");
//		}
//		
//		return math.multiplication(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
//		}
//	
//	@RequestMapping(value = "/division/{numberOne}/{numberTwo}",method=RequestMethod.GET)
//
//	public Double division ( 
//			@PathVariable(value = "numberOne")String numberOne,
//			@PathVariable(value = "numberTwo")String numberTwo
//	)throws Exception {
//		
//		if(!NumberConverter.isNumeric(numberOne)|| !NumberConverter.isNumeric(numberTwo)) { 
//			throw new UnsupportedMathOperationException("Please set a numeric value!");
//		}
//		
//		return math.division(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
//		}
//
//
//		@RequestMapping(value = "/mean/{numberOne}/{numberTwo}",method=RequestMethod.GET)
//
//		public Double mean ( 
//				@PathVariable(value = "numberOne")String numberOne,
//				@PathVariable(value = "numberTwo")String numberTwo
//		)throws Exception {
//			
//			if(!NumberConverter.isNumeric(numberOne)|| !NumberConverter.isNumeric(numberTwo)) { 
//				throw new UnsupportedMathOperationException("Please set a numeric value!");
//			}
//			
//		return math.mean(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
//			}
//	
//		@RequestMapping(value = "/squareroot/{number}",method=RequestMethod.GET)
//
//		public Double squareroot ( 
//				@PathVariable(value = "number")String number
//		)throws Exception {
//			
//			if(!NumberConverter.isNumeric(number)) { 
//				throw new UnsupportedMathOperationException("Please set a numeric value!");
//			}
//			
//			return math.squareroot(NumberConverter.convertToDouble(number));
//			}
	
	
//	@RequestMapping("/greeting")
//	public Double greeting (@RequestParam(value = "name", defaultValue = "World")String name) {
//		return new Greeting(0, String.format(template, name));
//	}
	
	//O @RequestMapping mapeia uma requisição para um método e torna isso um endereço http para acessar via browser.
	//No browser http://localhost:8080/greeting
	//No broser http://localhost:8080/greeting?name=Michelle
	//CTRL+SHIFT+O organiza os imports

