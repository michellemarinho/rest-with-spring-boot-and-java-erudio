package br.com.erudio.data.vo.v1;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;

@JsonPropertyOrder({"id", "bookName", "descName", "preco"}) //ordenar
public class BookVO extends RepresentationModel<BookVO> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty ("id") //anotation para exibir a palavra id ao invés de Key no retorno no postman
	@Mapping ("id") //anotation para q a aplicação reconheça que id é uma chamada para a chave Key
	private Long key;
	private String bookName;
	private String descName;
	private String preco;
	
	//Seleciona a opção Source/Construtor using Fields
	public BookVO() {}

	//Seleciona a opção Source/Generate Getters and Setters
	public Long key() {
		return key;
	}
	public void setKey(Long key) {
		this.key = key;
	}
	
	public Long getKey() {
		return key;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getDescName() {
		return descName;
	}

	public void setDescName(String descName) {
		this.descName = descName;
	}

	public String getPreco() {
		return preco;
	}

	public void setPreco(String preco) {
		this.preco = preco;
	}



	//Seleciona a opção Source/Generate hashCode() and equals()...
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((preco == null) ? 0 : preco.hashCode());
		result = prime * result + ((bookName == null) ? 0 : bookName.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((descName == null) ? 0 : descName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookVO other = (BookVO) obj;
		if (preco == null) {
			if (other.preco != null)
				return false;
		} else if (!preco.equals(other.preco))
			return false;
		if (bookName == null) {
			if (other.bookName != null)
				return false;
		} else if (!bookName.equals(other.bookName))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (descName == null) {
			if (other.descName != null)
				return false;
		} else if (!descName.equals(other.descName))
			return false;
		return true;
	}
}
