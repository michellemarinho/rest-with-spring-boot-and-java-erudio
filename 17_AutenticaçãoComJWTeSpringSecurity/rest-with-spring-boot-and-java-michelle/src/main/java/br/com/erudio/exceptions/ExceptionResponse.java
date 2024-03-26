package br.com.erudio.exceptions;

//Criando uma classe para tratar as exceções

import java.io.Serializable;
import java.util.Date;

public class ExceptionResponse implements Serializable{

	//Após criar a classe, clica no alert e clica em "Add default serial version ID" 
	
	private static final long serialVersionUID = 1L;

	//Agora define os atributos
	private Date timestamp; //Ao clicar para importar o Date, selecionar java.util.Date
	private String message;
	private String details;
	
	//Agora seleciona source/Generate Construtor using Fields
	public ExceptionResponse(Date timestamp, String message, String details) {
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}
	
	//Agora define os getters and setters

		public Date getTimestamp() {
				return timestamp;
		}

		public String getMessage() {
				return message;
		}

		public String getDetails() {
				return details;
		}

}
