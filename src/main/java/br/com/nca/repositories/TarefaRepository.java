package br.com.nca.repositories;

import br.com.nca.entities.Categoria;
import br.com.nca.entities.Tarefa;
import br.com.nca.enums.Prioridade;
import br.com.nca.factories.ConnectionFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class TarefaRepository {

  public void insert(Tarefa tarefa) throws Exception {

    var sql =
        """
				INSERT INTO tarefa
				(id,
				nome,
				data,
				prioridade,
				finalizado,
				categoria_id)
				values(?,?,?,?,?,?)
				""";

    var connection = ConnectionFactory.getConnection();

    var statement = connection.prepareStatement(sql);
    statement.setObject(1, tarefa.getId());
    statement.setString(2, tarefa.getNome());
    statement.setDate(3, java.sql.Date.valueOf(tarefa.getData()));
    statement.setString(4, tarefa.getPrioridade().toString());
    statement.setBoolean(5, tarefa.getFinalizada());
    statement.setObject(6, tarefa.getCategoria().getId());
    statement.execute();

    connection.close();
  }

  public boolean update(Tarefa tarefa) throws Exception {

    var sql =
        """
				UPDATE tarefa SET
				nome=?,
				data=?,
				prioridade=?,
				finalizado=?,
				categoria_id=?
				WHERE id=?
				            """;

    var connection = ConnectionFactory.getConnection();

    var statement = connection.prepareStatement(sql);

    statement.setString(1, tarefa.getNome());
    statement.setDate(2, java.sql.Date.valueOf(tarefa.getData()));
    statement.setString(3, tarefa.getPrioridade().toString());
    statement.setBoolean(4, tarefa.getFinalizada());
    statement.setObject(5, tarefa.getCategoria().getId());
    statement.setObject(6, tarefa.getId());
    var rowsAffected = statement.executeUpdate();

    connection.close();

    return rowsAffected > 0;
  }

  public boolean delete(UUID id) throws Exception {

    var sql = """
				DELETE FROM tarefa WHERE id=?
				            """;

    var connection = ConnectionFactory.getConnection();

    var statement = connection.prepareStatement(sql);

    statement.setObject(1, id);
    var rowsAffected = statement.executeUpdate();

    connection.close();

    return rowsAffected > 0;
  }

  public List<Tarefa> findAll(LocalDate dataMin, LocalDate dataMax) throws Exception {

    var sql =
        """
				SELECT
				t.id as id_tarefa,
                t.nome AS nome_tarefa,
                t.data,
                t.prioridade,
                t.finalizado,
                c.id AS id_categoria,
                c.nome AS nome_categoria
				FROM tarefa t
				INNER JOIN
				categoria c ON t.categoria_id = c.id
				WHERE t.data BETWEEN ? AND ?
				ORDER BY t.data
				            """;

    var connection = ConnectionFactory.getConnection();
    var statement = connection.prepareStatement(sql);

    statement.setDate(1, java.sql.Date.valueOf(dataMin));
    statement.setDate(2, java.sql.Date.valueOf(dataMax));

    var resultSet = statement.executeQuery();
    var lista = new ArrayList<Tarefa>();

    while (resultSet.next()) {
      var tarefa = new Tarefa();
      tarefa.setId((UUID) resultSet.getObject("id_tarefa"));
      tarefa.setNome(resultSet.getString("nome_tarefa"));
      tarefa.setData(resultSet.getDate("data").toLocalDate());
      tarefa.setPrioridade(Prioridade.valueOf(resultSet.getString("prioridade")));
      tarefa.setFinalizada(resultSet.getBoolean("finalizado"));

      var categoria = new Categoria();
      categoria.setId((UUID) resultSet.getObject("id_categoria"));
      categoria.setNome(resultSet.getString("nome_categoria"));
      tarefa.setCategoria(categoria);

      lista.add(tarefa);
    }

    connection.close();

    return lista;
  }
}
