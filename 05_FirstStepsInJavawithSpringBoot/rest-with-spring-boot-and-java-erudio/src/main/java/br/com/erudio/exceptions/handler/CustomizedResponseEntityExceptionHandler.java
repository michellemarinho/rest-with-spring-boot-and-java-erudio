package br.com.erudio.exceptions.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//Classe criada para incluir o manipulador de exceções

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.erudio.exceptions.ExceptionResponse;
import br.com.erudio.exceptions.UnsupportedMathOperationException;

// O que essas anotations fazem, são usadas para concentrar algum tratamento q está espalhado.
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

	//Criar o primeiro método responsável por tratar as exceções mais genéricas do Java que são as ExceptionHandler.
	//no REST a exceção mais genérica é a "Internal Server Error" ou "Error 500".
	
	//A anotation "@ExceptionHandler" foi incluída para filtrar em qual tipo de exceção ele vai encaixar nesse tratamento
	@ExceptionHandler(Exception.class) //"Exception.class" trata as exceções mais genéricas q existem no Java
	public final ResponseEntity <ExceptionResponse> handleAllExceptions(
			Exception ex, WebRequest request) {
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(), 
				ex.getMessage(), 
				request.getDescription(false));
			return new ResponseEntity<> (exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UnsupportedMathOperationException.class) //trata as exceções..
	public final ResponseEntity <ExceptionResponse> handleBadRequestExceptions(
			Exception ex, WebRequest request) {
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(), 
				ex.getMessage(), 
				request.getDescription(false));
			return new ResponseEntity<> (exceptionResponse, HttpStatus.BAD_REQUEST);
		}
}




