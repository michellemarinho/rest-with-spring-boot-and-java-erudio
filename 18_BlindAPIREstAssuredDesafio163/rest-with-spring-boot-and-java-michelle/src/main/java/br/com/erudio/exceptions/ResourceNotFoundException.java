package br.com.erudio.exceptions;
//Classe para criar as próprias exceções

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Sempre que ocorre uma "UnsupportedMathOperationException" será retornada uma @ResponseStatus que é um código de erro
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException  extends RuntimeException{

	private static final long serialVersionUID = 1L;	
	
	public ResourceNotFoundException(String ex) {
		super(ex);
	}
}