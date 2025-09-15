package br.com.nca.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TarefaPostRequest {

  private String nome;
  private String data;
  private String prioridade;
  private String categoriaId;
}
