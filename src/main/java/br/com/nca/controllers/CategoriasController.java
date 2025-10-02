package br.com.nca.controllers;

import br.com.nca.entities.Categoria;
import br.com.nca.repositories.CategoriaRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/categorias")
@SecurityRequirement(name = "bearerAuth")
public class CategoriasController {

  private CategoriaRepository categoriaRepository;

  public CategoriasController(CategoriaRepository categoriaRepository) {
    this.categoriaRepository = categoriaRepository;
  }

  @GetMapping
  public ResponseEntity<List<Categoria>> get(HttpServletRequest httpRequest) throws Exception {
    var usuarioID = httpRequest.getAttribute("userId");
    return ResponseEntity.ok(categoriaRepository.findAll());
  }
}
