package br.com.erudio.exceptions;
//Classe para criar as próprias exceções

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Sempre que ocorre uma exceção sera retornada uma ResponseStatus
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyFileNotFoundException  extends RuntimeException{

	private static final long serialVersionUID = 1L;	
	
	public MyFileNotFoundException(String ex) {
		super(ex);
	}
	
	public MyFileNotFoundException(String ex, Throwable cause) {
		super(ex, cause);
	}
}