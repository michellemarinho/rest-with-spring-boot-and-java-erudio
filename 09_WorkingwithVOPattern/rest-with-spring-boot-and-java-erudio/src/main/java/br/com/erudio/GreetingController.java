package br.com.erudio;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private static AtomicLong counter = new AtomicLong();
	
	
	@RequestMapping("/greeting")
	public Greeting greeting (@RequestParam(value = "name", defaultValue = "World")String name) {
		return new Greeting(0, String.format(template, name));
	}
	
	//O @RequestMapping mapeia uma requisição para um método e torna isso um endereço http para acessar via browser.
	//No browser http://localhost:8080/greeting
	//No broser http://localhost:8080/greeting?name=Erudio
}
