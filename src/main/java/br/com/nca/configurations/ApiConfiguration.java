package br.com.nca.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.nca.repositories.CategoriaRepository;
import br.com.nca.repositories.TarefaRepository;

@Configuration
public class ApiConfiguration {

	@Bean
	public CategoriaRepository categoriaRepository() {
		return new CategoriaRepository();
	}
	
	@Bean
	public TarefaRepository tarefaRepository() {
		return new TarefaRepository();
	}
}
