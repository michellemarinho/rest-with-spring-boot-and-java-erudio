package br.com.erudio.model;

import java.io.Serializable;
import java.util.Date;

//selecionar sempre import jakarta
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity  //Essa anotation representa q essa classe se conecta com o banco
@Table(name = "books") //Essa anotation é para dizer ao hibernate que na pratica esse objeto 'Book' significa 'book' que é o nome da tabela 
public class Book implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//Estratégia usada para incrementar o Id
	private Long id;
	
	@Column (nullable = false, length = 180)//indica como será chamado no banco e tamanho do campo
	private String author;
	
	@Column (name = "launch_date", nullable = false) //indica como será chamado no banco
	@Temporal(TemporalType.DATE) //sempre do jakarta
	private Date launchDate; //java.util
	
	@Column (nullable = false)//garante q não terá um dado inválido
	private Double price;
	
	@Column (nullable = false, length = 250)//indica como será chamado no banco e o tamanho do campo
	private String title;
	
	public Book() {}

	//Seleciona a opção Source/Generate Getters and Setters (Obs: select all, menos a opção SerialVersionUID)
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getLaunchDate() {
		return launchDate;
	}

	public void setLaunchDate(Date launchDate) {
		this.launchDate = launchDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	//Seleciona a opção Source/Generate hashCode() and equals()...
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((launchDate == null) ? 0 : launchDate.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (launchDate == null) {
			if (other.launchDate != null)
				return false;
		} else if (!launchDate.equals(other.launchDate))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}	
}