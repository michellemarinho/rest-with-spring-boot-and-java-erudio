package br.com.erudio.exceptions;
//Classe para criar as próprias exceções

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Sempre que ocorre uma "UnsupportedMathOperationException" será retornada uma @ResponseStatus que é um código de erro
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectIsNullException  extends RuntimeException{

	private static final long serialVersionUID = 1L;	
	
	
	public RequiredObjectIsNullException() {
		super("It is not allowed tpersist a null object");
	}
	
	public RequiredObjectIsNullException(String ex) {
		super(ex);
	}
	
}
