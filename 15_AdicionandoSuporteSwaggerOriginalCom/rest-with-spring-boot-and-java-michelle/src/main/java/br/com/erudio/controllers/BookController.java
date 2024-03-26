package br.com.erudio.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.services.BookServices;
import br.com.erudio.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

//contém vários métodos manipuladores
@RestController
@RequestMapping("api/book/v1")//Essa prática de versionar prevê futuras mudanças
@Tag(name = "Book", description = "Endpoints for Managing Book")
public class BookController {
	
	@Autowired //Com a inclusão do Autowired, o spring boot vai cuidar da instanciação de forma dinamica em tempo de execução
	private BookServices service; //injeção de dependencias
	//private BookServices service = new BookServices();
	
	//Pode-se usar o browser para acessar o GET, mas o POST, PUT e DELETE só podem ser acessados pelo Postman. 
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,MediaType.APPLICATION_YML})
	@Operation(summary = "Finds all Book", description = "Finds all Book",
	tags = {"Book"},
	responses = {
			@ApiResponse(description = "Sucess", responseCode = "200", 
					content = {
						@Content(
							mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = BookVO.class))
							)
					}),
			//@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
				}
	)
	public List<BookVO> findAll () {
			return service.findAll();
		}
	
	//"http://localhost:8080/book/1" me devolve os dados do boneco q tá descrito na classe "BookServices"
	@GetMapping(value = "/{id}",
			produces = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,MediaType.APPLICATION_YML})
	@Operation(summary = "Finds a Book", description = "Finds all Book",
	tags = {"Book"},
	responses = {
			@ApiResponse(description = "Sucess", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = BookVO.class))
							),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
				}
	)
	public BookVO findById (@PathVariable(value = "id")Long id) {
			return service.findById(id);
		}
	
	
	@PostMapping(
			consumes = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,MediaType.APPLICATION_YML},
			produces = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,MediaType.APPLICATION_YML})
	@Operation(summary = "Add a new Book", 
	description = "Adds a new Book by passing in a JSON, XML or YML",
	tags = {"Book"},
	responses = {
			@ApiResponse(description = "Sucess", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = BookVO.class))
							),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
				}
	)
	
	public BookVO create (@RequestBody BookVO book) {
			return service.create(book); //cria
		}
	//Foi incluído esse novo PostMapping por conta da inclusão do campo Bday
//	@PostMapping(value = "/v2", 
//			consumes = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,
//					MediaType.APPLICATION_YML},
//			produces = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,
//					MediaType.APPLICATION_YML})
//	public BookVOV2 createV2 (@RequestBody BookVOV2 book) {
//			return service.createV2(book); //cria
//		}
	
	@PutMapping(
			consumes = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,MediaType.APPLICATION_YML},	
			produces = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,MediaType.APPLICATION_YML})
	@Operation(summary = "Updates a Book", 
	description = "Updates a Book by passing in a JSON, XML or YML",
	tags = {"Book"},
	responses = {
			@ApiResponse(description = "Sucess", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = BookVO.class))
							),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
				}
	)
	
	public BookVO update (@RequestBody BookVO book) {
				return service.update(book); //atualiza
		}
	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Deletes a Book", 
	description = "Deletes a Book by passing in a JSON, XML or YML",
	tags = {"Book"},
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

