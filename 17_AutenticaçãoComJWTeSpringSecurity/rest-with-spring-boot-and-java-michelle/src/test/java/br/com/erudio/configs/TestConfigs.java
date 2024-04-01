package br.com.erudio.configs;

public class TestConfigs {
	
	//A 8888 é porta padrão q o REST ASSURED vai utilizar para se conectar com nossos testes.
	public static final int SERVER_PORT = 8888;
	
	//Será usada no futuro, na seção de autenticação
	public static final String HEADER_PARAM_AUTHORIZATION = "Authorization";
	
	//Para evitar erro de digitação
	public static final String HEADER_PARAM_ORIGIN = "Origin";
	
	//Constantes de tipos de conteúdos
	public static final String CONTENT_TYPE_JSON = "application/json";
	public static final String CONTENT_TYPE_XML = "application/xml";
	public static final String CONTENT_TYPE_YML = "application/x-yaml";

	public static final String ORIGIN_ERUDIO = "https://erudio.com.br";
	public static final String ORIGIN_SEMERU = "https://semeru.com.br";
}
