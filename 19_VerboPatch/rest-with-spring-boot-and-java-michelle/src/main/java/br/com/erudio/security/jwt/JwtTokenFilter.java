package br.com.erudio.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class JwtTokenFilter extends GenericFilterBean{

	@Autowired
	private JwtTokenProvider tokenProvider; 
	
	//Seleciona a opção Source/Construtor using Fields
	public JwtTokenFilter(JwtTokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String token = tokenProvider.resolveToken((HttpServletRequest) request); //obtem o token a partir da request
		if (token != null && tokenProvider.validateToken(token)) { //se token diferente de null e token provider.validate token, então pode seguir e entrar no if
			Authentication auth = tokenProvider.getAuthentication(token); //recebendo o token como parâmetro
			if (auth != null) {  //se auth diferente de null, então acessa o contexto e vai setar a autenticação do usuário.
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		chain.doFilter(request, response);	//filtro	
	}
}