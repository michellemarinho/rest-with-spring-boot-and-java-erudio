package br.com.erudio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.services.PersonServices;
import br.com.erudio.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

//contem varios metodos manipuladores
@RestController
@RequestMapping("/api/person/v1")//Essa pratica de versionar preve futuras mudancas
@Tag(name = "People", description = "Endpoints for Managing People")
public class PersonController {
	
	@Autowired //Com a inclusão do Autowired o spring boot vai cuidar da instanciacao de forma dinamica em tempo de execucao
	private PersonServices service; //injecao de dependencias
	//private PersonServices service = new PersonServices();
	
	//Pode usar o browser para acessar o GET  mas o POST PUT e DELETE so podem ser acessados pelo Postman 
	
	@GetMapping(
				produces = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,MediaType.APPLICATION_YML})
	@Operation(summary = "Finds all People", description = "Finds all People",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", 
					content = {
						@Content(
							mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
							)
					}),
			//@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
				}
	)
	//public List<PersonVO> findAll () --> trecho alterado para não mais trazer uma lista e agora sim uma pagina
	public ResponseEntity<PagedModel<EntityModel<PersonVO>>> findAll(
			@RequestParam(value = "page", defaultValue = "0")Integer page,
			@RequestParam(value = "size", defaultValue = "12")Integer size,
			@RequestParam(value = "direction", defaultValue = "asc")String direction
			){
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? 
				Direction.DESC : Direction.ASC;
		
			Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
			return ResponseEntity.ok(service.findAll(pageable));
		}
	
	@GetMapping(value = "/findPersonByName/{firstName}",
			produces = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,MediaType.APPLICATION_YML})
	@Operation(summary = "Finds People by Name", description = "Finds People by Name",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", 
					content = {
							@Content(
									mediaType = "application/json",
									array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
									)
			}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	}
			)
	public ResponseEntity<PagedModel<EntityModel<PersonVO>>> findPersonByName(
			@PathVariable(value = "firstName")String firstName,
			@RequestParam(value = "page", defaultValue = "0")Integer page,
			@RequestParam(value = "size", defaultValue = "12")Integer size,
			@RequestParam(value = "direction", defaultValue = "asc")String direction
			){
		
		var sortDirection = "desc".equalsIgnoreCase(direction) 
				? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
		return ResponseEntity.ok(service.findPersonByName(firstName, pageable));
	}
	
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping(value = "/{id}",
			produces = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,MediaType.APPLICATION_YML})
	@Operation(summary = "Finds a Person", description = "Finds a Person",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = PersonVO.class))
							),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
				}
	)
	public PersonVO findById (@PathVariable(value = "id")Long id) {
			return service.findById(id);
		}
	
	@CrossOrigin(origins = {"http://localhost:8080", "https://erudio.com.br"})//definimos duas origens permitidas
	
	@PostMapping(
			consumes = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,MediaType.APPLICATION_YML},
			produces = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,MediaType.APPLICATION_YML})
	@Operation(summary = "Adds a new Person", 
	description = "Adds a new Person by passing in a JSON, XML or YML representation of the person!",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = PersonVO.class))
							),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
				}
	)
	
	public PersonVO create (@RequestBody PersonVO person) {
			return service.create(person);
		}
	//Foi incluído esse novo PostMapping por conta da inclusão do campo Bday
//	@PostMapping(value = "/v2", 
//			consumes = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,
//					MediaType.APPLICATION_YML},
//			produces = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,
//					MediaType.APPLICATION_YML})
//	public PersonVOV2 createV2 (@RequestBody PersonVOV2 person) {
//			return service.createV2(person); //cria
//		}
	
	@PutMapping(
			consumes = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,MediaType.APPLICATION_YML},	
			produces = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,MediaType.APPLICATION_YML})
	@Operation(summary = "Updates a Person", 
	description = "Updates a Person by passing in a JSON, XML or YML representation of the person!",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "Updated", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = PersonVO.class))
							),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
				}
	)
	
	public PersonVO update (@RequestBody PersonVO person) {
				return service.update(person);
		}
	
	@PatchMapping(value = "/{id}",
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
		@Operation(summary = "Disable a specific Person by your ID", description = "Disable a specific Person by your ID",
			tags = {"People"},
			responses = {
				@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = PersonVO.class))
				),
				@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
		)
		public PersonVO disablePerson(@PathVariable(value = "id") Long id) {
			return service.disablePerson(id);
		}
	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Deletes a Person", 
	description = "Deletes a Person by passing in a JSON, XML or YML representation of the person!",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
				}
	)
	public ResponseEntity<?> delete (@PathVariable(value = "id")Long id) {
				service.delete(id);
				return ResponseEntity.noContent().build();
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
//			//@PathVariable para retornar dados da url
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

