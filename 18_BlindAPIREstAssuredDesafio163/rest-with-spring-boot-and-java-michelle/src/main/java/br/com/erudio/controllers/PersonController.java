package br.com.erudio.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.services.PersonServices;
import br.com.erudio.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

//contém vários métodos manipuladores
//@CrossOrigin
@RestController
@RequestMapping("/api/person/v1")//Essa prática de versionar prevê futuras mudanças
@Tag(name = "People", description = "Endpoints for Managing People")
public class PersonController {
	
	@Autowired //Com a inclusão do Autowired, o spring boot vai cuidar da instanciação de forma dinamica em tempo de execução
	private PersonServices service; //injeção de dependencias
	//private PersonServices service = new PersonServices();
	
	//Pode-se usar o browser para acessar o GET, mas o POST, PUT e DELETE só podem ser acessados pelo Postman. 
	
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
	public List<PersonVO> findAll () {
			return service.findAll();
		}
	
	@CrossOrigin(origins = "http://localhost:8080")
	
	//"http://localhost:8080/person/1" me devolve os dados do boneco q tá descrito na classe "PersonServices"
	@GetMapping(value = "/{id}",
			produces = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,MediaType.APPLICATION_YML})
	@Operation(summary = "Finds a Person", description = "Finds a Person",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "Sucess", responseCode = "200", 
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
	@Operation(summary = "Add a new People", 
	description = "Adds a new Person by passing in a JSON, XML or YML",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "Sucess", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = PersonVO.class))
							),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
				}
	)
	
	public PersonVO create (@RequestBody PersonVO person) {
			return service.create(person); //cria
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
	description = "Updates a Person by passing in a JSON, XML or YML",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "Sucess", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = PersonVO.class))
							),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
				}
	)
	
	public PersonVO update (@RequestBody PersonVO person) {
				return service.update(person); //atualiza
		}
	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Deletes a Person", 
	description = "Deletes a Person by passing in a JSON, XML or YML",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "No content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
				}
	)
	public ResponseEntity<?> delete (@PathVariable(value = "id")Long id) {
				service.delete(id); //apaga
				return ResponseEntity.noContent().build(); //para retornar o status code de erro, correto
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

