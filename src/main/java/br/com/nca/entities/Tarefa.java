package br.com.nca.entities;

import br.com.nca.enums.Prioridade;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Tarefa {

  private UUID id;
  private String nome;
  private LocalDate data;
  private Prioridade prioridade;
  private Categoria categoria;
  private Boolean finalizada;
}
