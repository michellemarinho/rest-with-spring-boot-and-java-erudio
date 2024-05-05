package br.com.erudio.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

//Site para leitura de spring Content Negotiation-json.xml

import org.springframework.context.annotation.Configuration; //Esse contexto diz q em @Configuration tem o comportamento da app
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.erudio.serialization.converter.YamlJackson2HttpMesageConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	//Incluir uma propriedade privada
	private static final MediaType MEDIA_TYPE_APPLICATION_YML = MediaType.valueOf("application/x-yaml");

	//MesageConverter que foi incluído na classe YamlJackson2HttpMesageConverter
	//Para incluir clicar em source/Orride/Implement Methods e selecionar extendMessageConverter(List...)
	@Value("${cors.originPatterns:default}")
	private String corsOriginPatterns = "";
	
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new YamlJackson2HttpMesageConverter());
	}
	
	//source/Override/Implement Methods, seleciona a opção addCorsMapping(CorsRegistry)
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		var allowedOrigins = corsOriginPatterns.split(",");
		registry.addMapping("/**")
			//.allowedMethods("GET", "POST", "PUT")
			.allowedMethods("*")
			.allowedOrigins(allowedOrigins)
		.allowCredentials(true);
	}
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		//https://www.baeldung.com/spring-mvc-content-negotiation-json-xml
		//Via Extension. http://localhost:8080/api/person/v1.xml DEPRECATED on SpringBoot 2.
		/*(Deprecated) Using URL suffixes (extensions) in the request (eg .xml/.json)
			Using URL parameter in the request (eg ?format=json)*/
		
		//No Postman:
		//Via QUERY PARAM http://localhost:8080/api/person/v1?mediaType=xml
		//Via QUERY PARAM http://localhost:8080/api/person/v1/1?mediaType=xml
		/*
		configurer.favorParameter(true)
		.parameterName("mediaType").ignoreAcceptHeader(true)
		.useRegisteredExtensionsOnly(false)
		.defaultContentType(MediaType.APPLICATION_JSON)
			.mediaType("json", MediaType.APPLICATION_JSON)
			.mediaType("xml", MediaType.APPLICATION_XML);
		*/
		
		//Via HEADER PARAM http://localhost:8080/api/person/v1
		
		configurer.favorParameter(false)
		.ignoreAcceptHeader(false)
		.useRegisteredExtensionsOnly(false)
		.defaultContentType(MediaType.APPLICATION_JSON)
			.mediaType("json", MediaType.APPLICATION_JSON)
			.mediaType("xml", MediaType.APPLICATION_XML)
			.mediaType("x-yaml", MEDIA_TYPE_APPLICATION_YML)
			;
	}
	
}
