package br.com.nca.factories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

  private static final String HOST = "jdbc:postgresql://localhost:5434/bd_apiagenda";
  private static final String USER = "postgresuser";
  private static final String PASSWORD = "postgrespassword";
  private static final String DRIVER = "org.postgresql.Driver";

  public static Connection getConnection() throws SQLException, ClassNotFoundException {
    Class.forName(DRIVER);
    return DriverManager.getConnection(HOST, USER, PASSWORD);
  }
}
