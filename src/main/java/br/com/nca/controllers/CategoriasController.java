package br.com.nca.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nca.entities.Categoria;
import br.com.nca.repositories.CategoriaRepository;

@RestController
@RequestMapping("api/v1/categorias")
public class CategoriasController {

	private CategoriaRepository categoriaRepository;

	public CategoriasController(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}

	@GetMapping
	public ResponseEntity<List<Categoria>> get() throws Exception {
		return ResponseEntity.ok(categoriaRepository.findAll());
	}
}
