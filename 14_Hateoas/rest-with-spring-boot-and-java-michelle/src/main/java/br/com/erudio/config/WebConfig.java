package br.com.erudio.config;



import java.util.List;

//Site para leitura de spring Content Negotiation-json.xml

import org.springframework.context.annotation.Configuration; //Esse contexto diz q em @Confiration tem o comportamento da app
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import br.com.erudio.serialization.converter.YamlJackson2HttpMesageConverter;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{


	//Incluir uma propriedade privada
	private static final MediaType MEDIA_TYPE_APPLICATION_YML = MediaType.valueOf("application/x-yaml");

	//MesageConverter que foi incluído na classe YamlJackson2HttpMesageConverter
	//Para incluir clicar em source/Orride/Implement Methods e eselecionar extendMessageConverter(List...)
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new YamlJackson2HttpMesageConverter());
	}
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		//https://www.baeldung.com/spring-mvc-content-negotiation-json-xml
		//Via Extension. http://localhost:8080/api/person/v1.xml DEPRECATED on SpringBoot 2.
		/*(Deprecated) Using URL suffixes (extensions) in the request (eg .xml/.json)
			Using URL parameter in the request (eg ?format=json)*/
		
		//Via QUERY PARAM http://localhost:8080/api/person/v1?mediaType=xml
		//Via QUERY PARAM http://localhost:8080/api/person/v1/1?mediaType=xml
		
//		configurer.favorParameter(true)
//		.parameterName("mediaType").ignoreAcceptHeader(true)
//		.useRegisteredExtensionsOnly(false)
//		.defaultContentType(MediaType.APPLICATION_JSON)
//		.mediaType("json", MediaType.APPLICATION_JSON)
//		.mediaType("xml", MediaType.APPLICATION_XML);
		
		//Via HEDER PARAM http://localhost:8080/api/person/v1
		
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
