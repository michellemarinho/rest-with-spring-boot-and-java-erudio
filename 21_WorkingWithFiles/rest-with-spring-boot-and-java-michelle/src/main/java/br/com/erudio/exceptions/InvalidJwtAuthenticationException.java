package br.com.erudio.exceptions;
//Classe para criar as próprias exceções

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

//Sempre que ocorre uma "UnsupportedMathOperationException" será retornada uma ResponseStatus que é um código de erro
@ResponseStatus(HttpStatus.FORBIDDEN)//proibido acesso
public class InvalidJwtAuthenticationException  extends AuthenticationException{

	private static final long serialVersionUID = 1L;	
	
	public InvalidJwtAuthenticationException(String ex) {
		super(ex);
	}
}