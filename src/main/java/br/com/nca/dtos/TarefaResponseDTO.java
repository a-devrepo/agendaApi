package br.com.nca.dtos;

import br.com.nca.enums.Prioridade;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TarefaResponseDTO {
  private UUID id;
  private String nome;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate data;

  private Prioridade prioridade;
  private UUID categoriaId;
  private Boolean finalizada;
}
