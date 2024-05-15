package br.com.erudio.integrationtests.controller.withyaml;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.erudio.configs.TestConfigs;
import br.com.erudio.data.vo.v1.security.TokenVO;
import br.com.erudio.integrationtests.controller.withyaml.mapper.YMLMapper;
import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.erudio.integrationtests.vo.AccountCredentialsVO;
import br.com.erudio.integrationtests.vo.PersonVO;
import br.com.erudio.integrationtests.vo.pagedmodels.PagedModelPerson;
import br.com.erudio.integrationtests.vo.wrappers.WrapperPersonVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerYamlTest extends AbstractIntegrationTest {
	
	private static RequestSpecification specification;
	private static YMLMapper objectMapper;

	private static PersonVO person;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new YMLMapper();
		person = new PersonVO();
	}
	
	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");
		
		var accessToken = given()
				.config(
						RestAssuredConfig
							.config()
							.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(
									TestConfigs.CONTENT_TYPE_YML,
									ContentType.TEXT)))
				.basePath("/auth/signin")
					.port(TestConfigs.SERVER_PORT)
					.contentType(TestConfigs.CONTENT_TYPE_YML)
					.accept(TestConfigs.CONTENT_TYPE_YML)
				.body(user, objectMapper)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract()
							.body()
								.as(TokenVO.class, objectMapper)
							.getAccessToken();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		var persistedPerson = given().spec(specification)
				.config(
						RestAssuredConfig
							.config()
							.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(
									TestConfigs.CONTENT_TYPE_YML,
									ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.body(person, objectMapper)
					.when()
					.post()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.as(PersonVO.class, objectMapper);
		
		person = persistedPerson;
		
		assertNotNull(persistedPerson);
		
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		
		assertTrue(persistedPerson.getId() > 0);
		
		assertEquals("Nelson", persistedPerson.getFirstName());
		assertEquals("Piquet", persistedPerson.getLastName());
		assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
	}

	@Test
	@Order(2)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {
		person.setLastName("Piquet Souto Maior");
		
		var persistedPerson = given().spec(specification)
				.config(
						RestAssuredConfig
							.config()
							.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(
									TestConfigs.CONTENT_TYPE_YML,
									ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.body(person, objectMapper)
					.when()
					.post()
				.then()
					.statusCode(200)
						.extract()
						.body()
						.as(PersonVO.class, objectMapper);
		
		person = persistedPerson;
		
		assertNotNull(persistedPerson);
		
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		
		assertEquals(person.getId(), persistedPerson.getId());
		
		assertEquals("Nelson", persistedPerson.getFirstName());
		assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
		assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
	}

	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockPerson();
			
		var persistedPerson = given().spec(specification)
				.config(
						RestAssuredConfig
							.config()
							.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(
									TestConfigs.CONTENT_TYPE_YML,
									ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.pathParam("id", person.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
						.extract()
						.body()
						.as(PersonVO.class, objectMapper);
		
		person = persistedPerson;
		
		assertNotNull(persistedPerson);
		
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());

		assertEquals(person.getId(), persistedPerson.getId());
		
		assertEquals("Nelson", persistedPerson.getFirstName());
		assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
		assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
	}
	
	@Test
	@Order(4)
	public void testDelete() throws JsonMappingException, JsonProcessingException {

		given().spec(specification)
			.config(
				RestAssuredConfig
					.config()
					.encoderConfig(EncoderConfig.encoderConfig()
						.encodeContentTypeAs(
							TestConfigs.CONTENT_TYPE_YML,
							ContentType.TEXT)))
			.contentType(TestConfigs.CONTENT_TYPE_YML)
			.accept(TestConfigs.CONTENT_TYPE_YML)
				.pathParam("id", person.getId())
				.when()
				.delete("{id}")
			.then()
				.statusCode(204);
	}
	
	@Test
	@Order(5)
	public void testFindAll() throws JsonMappingException, JsonProcessingException {
		
		var wrapper = given().spec(specification)
				.config(
						RestAssuredConfig
							.config()
							.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(
									TestConfigs.CONTENT_TYPE_YML,
									ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
				.queryParams("page", 3,"size", 10, "direction", "asc")
					.when()
					.get()
				.then()
					.statusCode(200)
						.extract()
						.body()
						.as(PagedModelPerson.class, objectMapper);
		
		var people = wrapper.getContent();
		
		PersonVO foundPersonOne = people.get(0);
		
		assertNotNull(foundPersonOne.getId());
		assertNotNull(foundPersonOne.getFirstName());
		assertNotNull(foundPersonOne.getLastName());
		assertNotNull(foundPersonOne.getAddress());
		assertNotNull(foundPersonOne.getGender());

		assertTrue(foundPersonOne.getEnabled());
		
		assertEquals(455, foundPersonOne.getId());
		
		assertEquals("Amy", foundPersonOne.getFirstName());
		assertEquals("Hubane", foundPersonOne.getLastName());
		assertEquals("161 Knutson Center", foundPersonOne.getAddress());
		assertEquals("Female", foundPersonOne.getGender());
		
		PersonVO foundPersonSix = people.get(5);
		
		assertNotNull(foundPersonSix.getId());
		assertNotNull(foundPersonSix.getFirstName());
		assertNotNull(foundPersonSix.getLastName());
		assertNotNull(foundPersonSix.getAddress());
		assertNotNull(foundPersonSix.getGender());

		assertTrue(foundPersonSix.getEnabled());
		
		assertEquals(769, foundPersonSix.getId());
		
		assertEquals("Andria", foundPersonSix.getFirstName());
		assertEquals("Paybody", foundPersonSix.getLastName());
		assertEquals("68716 Brickson Park Hill", foundPersonSix.getAddress());
		assertEquals("Female", foundPersonSix.getGender());
	}

	@Test
	@Order(6)
	public void testFindByName() throws JsonMappingException, JsonProcessingException {
		mockPerson();
			
		var wrapper = given().spec(specification)
				.config(
						RestAssuredConfig
							.config()
							.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(
									TestConfigs.CONTENT_TYPE_YML,
									ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.pathParam("firstName", "mid")
					.queryParams("page", 0, "size", 6, "direction", "asc")
						.when()
						.get("findPersonByName/{firstName}")
					.then()
						.statusCode(200)
							.extract()
							.body()
							.as(PagedModelPerson.class, objectMapper);


		var people = wrapper.getContent();
		
		PersonVO foundPersonOne = people.get(0);
		
		assertNotNull(foundPersonOne.getId());
		assertNotNull(foundPersonOne.getFirstName());
		assertNotNull(foundPersonOne.getLastName());
		assertNotNull(foundPersonOne.getAddress());
		assertNotNull(foundPersonOne.getGender());

		assertTrue(foundPersonOne.getEnabled());
		
		assertEquals(551, foundPersonOne.getId());
		
		assertEquals("Hamid", foundPersonOne.getFirstName());
		assertEquals("Eagan", foundPersonOne.getLastName());
		assertEquals("21 Anthes Terrace", foundPersonOne.getAddress());
		assertEquals("Male", foundPersonOne.getGender());
}
	
	@Test
	@Order(7)
	public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {
		
		RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
			.setBasePath("/api/person/v1")
			.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();
		
		given().spec(specificationWithoutToken)
			.config(
				RestAssuredConfig
					.config()
					.encoderConfig(EncoderConfig.encoderConfig()
						.encodeContentTypeAs(
							TestConfigs.CONTENT_TYPE_YML,
							ContentType.TEXT)))
			.contentType(TestConfigs.CONTENT_TYPE_YML)
			.accept(TestConfigs.CONTENT_TYPE_YML)
				.when()
				.get()
			.then()
				.statusCode(403);
	}
	
	@Test
	@Order(8)
	public void testHATEOAS() throws JsonMappingException, JsonProcessingException {
		
		var unthreatedContent = given().spec(specification)
				.config(
						RestAssuredConfig
							.config()
							.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(
									TestConfigs.CONTENT_TYPE_YML,
									ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
				.queryParams("page", 3,"size", 10, "direction", "asc")
					.when()
					.get()
				.then()
					.statusCode(200)
						.extract()
						.body()
						.asString();
		
		var content = unthreatedContent.replace("\n", "").replace("\r", "");
				
		//No content do Postman alterei o Accept para application/xml
		assertTrue(content.contains("rel: \"self\" href: \"http://localhost:8888/api/person/v1/461\""));
		assertTrue(content.contains("rel: \"self\" href: \"http://localhost:8888/api/person/v1/478\""));
		assertTrue(content.contains("rel: \"self\" href: \"http://localhost:8888/api/person/v1/350\""));
		
		assertTrue(content.contains("rel: \"first\" href: \"http://localhost:8888/api/person/v1?direction=asc&page=0&size=10&sort=firstName,asc\""));
		assertTrue(content.contains("rel: \"prev\" href: \"http://localhost:8888/api/person/v1?direction=asc&page=2&size=10&sort=firstName,asc\""));
		assertTrue(content.contains("rel: \"self\" href: \"http://localhost:8888/api/person/v1?page=3&size=10&direction=asc\""));
		assertTrue(content.contains("rel: \"next\" href: \"http://localhost:8888/api/person/v1?direction=asc&page=4&size=10&sort=firstName,asc\""));
		assertTrue(content.contains("rel: \"last\" href: \"http://localhost:8888/api/person/v1?direction=asc&page=100&size=10&sort=firstName,asc\""));
	
		assertTrue(content.contains("page:  size: 10  totalElements: 1006  totalPages: 101  number: 3"));	
	}
	
	private void mockPerson() {
		person.setFirstName("Nelson");
		person.setLastName("Piquet");
		person.setAddress("Brasília - DF - Brasil");
		person.setGender("Male");
		person.setEnabled(true);
	}
}