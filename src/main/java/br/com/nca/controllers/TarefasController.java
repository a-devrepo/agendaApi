 package br.com.nca.controllers;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nca.dtos.TarefaPostRequest;
import br.com.nca.dtos.TarefaPutRequest;
import br.com.nca.entities.Categoria;
import br.com.nca.entities.Tarefa;
import br.com.nca.enums.Prioridade;
import br.com.nca.repositories.TarefaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/tarefas")
@Tag(name = "Tarefas", description = "Endpoint para operações relacionadas a tarefas")
public class TarefasController {

  private TarefaRepository tarefaRepository;

  public TarefasController(TarefaRepository tarefaRepository) {
    this.tarefaRepository = tarefaRepository;
  }

  @Operation(
      summary = "Cadastrar nova tarefa",
      description = "Cria uma nova tarefa no sistema com os dados fornecidos")
  @PostMapping
  @Parameter(
      description = "Dados da tarefa a ser criada",
      required = true,
      content = @Content(schema = @Schema(implementation = TarefaPostRequest.class)))
  public ResponseEntity<?> post(@RequestBody TarefaPostRequest request, HttpServletRequest httpRequest) {
    try {
        
      var usuarioID = (UUID) httpRequest.getAttribute("userId");
      
      var tarefa = new Tarefa();

      tarefa.setId(UUID.randomUUID());
      tarefa.setNome(request.getNome());
      tarefa.setData(LocalDate.parse(request.getData()));
      tarefa.setPrioridade(Prioridade.valueOf(request.getPrioridade()));
      tarefa.setFinalizada(false);

      tarefa.setCategoria(new Categoria());
      tarefa.getCategoria().setId(UUID.fromString(request.getCategoriaId()));

      tarefaRepository.insert(tarefa);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Erro ao inserir tarefa: " + e.getMessage());
    }
  }

  @Operation(
      summary = "Atualizar tarefa existente",
      description = "Atualiza os dados de uma tarefa existente no sistema")
  @PutMapping("/{id}")
  public ResponseEntity<?> put(
      @PathVariable(required = true) UUID id, @RequestBody TarefaPutRequest request, HttpServletRequest httpRequest) {

    try {
      var usuarioID = (UUID) httpRequest.getAttribute("userId");  
      var tarefa = new Tarefa();

      tarefa.setId(id);
      tarefa.setNome(request.getNome());
      tarefa.setData(LocalDate.parse(request.getData()));
      tarefa.setPrioridade(Prioridade.valueOf(request.getPrioridade()));
      tarefa.setFinalizada(request.getFinalizada());

      tarefa.setCategoria(new Categoria());
      tarefa.getCategoria().setId(UUID.fromString(request.getCategoriaId()));

      var alteracaoRealizada = tarefaRepository.update(tarefa);

      if (alteracaoRealizada) {
        return ResponseEntity.status(HttpStatus.OK).build();
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Erro ao alterar tarefa: " + e.getMessage());
    }
  }

  @Operation(summary = "Excluir tarefa", description = "Remove uma tarefa do sistema")
  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable UUID id, HttpServletRequest httpRequest) {

    try {
      var usuarioID = (UUID) httpRequest.getAttribute("userId");
      var exclusaoRealizada = tarefaRepository.delete(id);

      if (exclusaoRealizada) {
        return ResponseEntity.status(HttpStatus.OK).build();
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Erro ao excluir tarefa: " + e.getMessage());
    }
  }

  @Operation(
      summary = "Consultar tarefas",
      description = "Retorna uma lista de tarefas cadastradas no sistema, com opções de filtro")
  @GetMapping("/{dataMin}/{dataMax}")
  public ResponseEntity<?> findAll(
      @PathVariable LocalDate dataMin, @PathVariable LocalDate dataMax, HttpServletRequest httpRequest) {

    try {
      var usuarioID = (UUID) httpRequest.getAttribute("userId");
      var tarefas = tarefaRepository.findAll(dataMin, dataMax);

      return ResponseEntity.ok(tarefas);
    } catch (Exception e) {
      return ResponseEntity.internalServerError()
          .body("Erro ao consultar tarefas: " + e.getMessage());
    }
  }

  @Operation(
      summary = "Quantidade de tarefas por prioridade",
      description = "Retorna uma lista com a quantidade de tarefas por prioridade")
  @GetMapping("/groupby-prioridade")
  public ResponseEntity<?> getTarefaPrioridade(HttpServletRequest httpRequest) {

    try {
      var usuarioID = (UUID) httpRequest.getAttribute("userId");
      var tarefasPrioridade = tarefaRepository.groupByTarefaPrioridade();

      return ResponseEntity.ok(tarefasPrioridade);
    } catch (Exception e) {
      return ResponseEntity.internalServerError()
          .body("Erro ao consultar tarefas: " + e.getMessage());
    }
  }

  @Operation(
      summary = "Quantidade de tarefas por categoria",
      description = "Retorna uma lista com a quantidade de tarefas por categoria")
  @GetMapping("/groupby-categoria")
  public ResponseEntity<?> getTarefaCategoria(HttpServletRequest httpRequest) {

    try {
      var usuarioID = (UUID) httpRequest.getAttribute("userId");
      var tarefasCategoria = tarefaRepository.groupByTarefaCategoria();

      return ResponseEntity.ok(tarefasCategoria);
    } catch (Exception e) {
      return ResponseEntity.internalServerError()
          .body("Erro ao consultar tarefas: " + e.getMessage());
    }
  }
}
