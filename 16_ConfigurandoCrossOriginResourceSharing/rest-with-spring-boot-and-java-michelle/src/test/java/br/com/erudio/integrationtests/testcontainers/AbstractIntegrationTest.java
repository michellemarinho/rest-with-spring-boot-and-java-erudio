package br.com.erudio.integrationtests.testcontainers;

import java.util.Map;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

@ContextConfiguration (initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext>{
		
		//Acessar o site https://hub.docker.com/_/mysql buscar a versão mais nova para utilizar na linha abaixo, pois a versão pode estar depreciada, e copiar sempre a última versão
		//do tópico "Supported tags and respective Dockerfile links" --8.0.36
		static MySQLContainer<?> mysql = new MySQLContainer<> ("mysql:8.0.36");
		
		private static void startContainers() {
			Startables.deepStart(Stream.of(mysql)).join();
		}
		
		private static Map<String, String> createConnectionConfiguration() {
			return Map.of(
				"spring.datasource.url", mysql.getJdbcUrl(),
				"spring.datasource.username", mysql.getUsername(),
				"spring.datasource.password", mysql.getPassword()
			);
		}
		
		@SuppressWarnings({"unchecked", "rawtypes"})
		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			startContainers(); 
			
		//Agora a gente vai configurar o nosso ambiente.
		/*A gente vai acessar o ambiente do spring, vai pegar as configuraçãoes do teste3 container e 
		 * vai setar essas configurações do teste container no contexto do spring. */	
			
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		
		//Aqui vai Acessar o teste Container e criar a conexão
		MapPropertySource testcontainers = new MapPropertySource(
				"testcontainers",
				(Map) createConnectionConfiguration());
			environment.getPropertySources().addFirst(testcontainers);

		}

	}

}
