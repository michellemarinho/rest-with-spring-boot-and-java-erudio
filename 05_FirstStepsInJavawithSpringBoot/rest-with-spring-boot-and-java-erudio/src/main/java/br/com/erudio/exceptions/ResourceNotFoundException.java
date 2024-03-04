package br.com.erudio.exceptions;
//Classe para criar as próprias exceções

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Sempre que ocorre uma "UnsupportedMathOperationException" será retornada uma @ResponseStatus que é um código de erro
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsupportedMathOperationException  extends RuntimeException{

	
	public UnsupportedMathOperationException(String ex) {
		super(ex);
	}

	private static final long serialVersionUID = 1L;	
}
