package br.com.nca.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TarefaPutRequest {

  private String nome;
  private String data;
  private String prioridade;
  private String categoriaId;
  private Boolean finalizada;
}
