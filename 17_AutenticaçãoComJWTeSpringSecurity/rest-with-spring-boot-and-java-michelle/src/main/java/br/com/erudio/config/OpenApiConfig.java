package br.com.erudio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
	
		@Bean //URL para testes: http://localhost:8080/v3/api-docs
		OpenAPI customOpenAPI() {
			return new OpenAPI()
				.info(new Info()
					.title("Restful MichelleAPI SpringBoot 3.2.3")
					.version("v1")
					.description("1a API da Michelle")
					.termsOfService("")
					.license(new License().name("Apache 2.0")
						.url("http://google.com")
						)
				);
		}

}
