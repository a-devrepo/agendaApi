package br.com.nca.factories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

  private static final String HOST = System.getenv().get("SPRING_DATASOURCE_URL");
  private static final String USER = System.getenv().get("POSTGRES_USER");
  private static final String PASSWORD = System.getenv().get("POSTGRES_PASSWORD");
  private static final String DRIVER = "org.postgresql.Driver";

  public static Connection getConnection() throws SQLException, ClassNotFoundException {
    Class.forName(DRIVER);
    return DriverManager.getConnection(HOST, USER, PASSWORD);
  }
}
