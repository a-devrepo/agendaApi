package br.com.nca.controllers;

import java.time.LocalDate;
import java.util.List;
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
import br.com.nca.entities.Categoria;
import br.com.nca.entities.Tarefa;
import br.com.nca.enums.Prioridade;
import br.com.nca.repositories.TarefaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

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
		    description = "Cria uma nova tarefa no sistema com os dados fornecidos"
		)
	@PostMapping
	@Parameter(
            description = "Dados da tarefa a ser criada",
            required = true,
            content = @Content(schema = @Schema(implementation = TarefaPostRequest.class))
        )
	public ResponseEntity<?> post(@RequestBody TarefaPostRequest request) {
		try {
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
			return ResponseEntity
					.internalServerError()
					.body("Erro ao inserir tarefa: " + e.getMessage());
		}
	}

	@Operation(
		    summary = "Atualizar tarefa existente",
		    description = "Atualiza os dados de uma tarefa existente no sistema"
		)
	@PutMapping
	public void put(@RequestBody TarefaPostRequest request) {
	}

	@Operation(
		    summary = "Excluir tarefa",
		    description = "Remove uma tarefa do sistema"
		)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable UUID id) {
	}

	@Operation(
		    summary = "Consultar tarefas",
		    description = "Retorna uma lista de tarefas cadastradas no sistema, com opções de filtro"
		)
	@GetMapping
	public List<Tarefa> findAll() {
		return null;
	}
}
