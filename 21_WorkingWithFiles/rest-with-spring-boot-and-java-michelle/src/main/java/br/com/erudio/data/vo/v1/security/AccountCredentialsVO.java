package br.com.erudio.data.vo.v1.security;

import java.io.Serializable;

import jakarta.xml.bind.annotation.XmlRootElement;

public class AccountCredentialsVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	
	public AccountCredentialsVO() {} //construtor default
	
	//Seleciona a opção Source/Construtor using Fields (Omit call to default construtor super())
	public AccountCredentialsVO(String username, String password) {
		this.username = username;
		this.password = password;
	}

	//Seleciona a opção Source/Generate Getters and Setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	//Seleciona a opção Source/Generate hashCode() and equals()...
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		AccountCredentialsVO other = (AccountCredentialsVO) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}