package br.com.nca.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.com.nca.entities.Categoria;
import br.com.nca.factories.ConnectionFactory;

@Component
public class CategoriaRepository {

	public List<Categoria> findAll() throws Exception {

		var sql = """
				SELECT id, nome from categoria
				order by nome ASC
				""";
		
		var connection = ConnectionFactory.getConnection();
		
		var statement = connection.prepareStatement(sql);
		
		var result = statement.executeQuery();
		
		var categorias = new ArrayList<Categoria>();
		
		while (result.next()) {
			var id = UUID.fromString(result.getString("id"));
			var nome = result.getString("nome");
			var categoria = new Categoria(id, nome);
			categorias.add(categoria);
		}
		return categorias;
	}
}
