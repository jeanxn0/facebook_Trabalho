package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import model.ModelException;

public class MySQLConnectionFactory {

    private static final String DATABASE_URL =
            "jdbc:mysql://mysql-26d207b8-jeansantosc51-6a31.i.aivencloud.com:10001/facebook?sslMode=REQUIRED";

    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_6K0TqC6VBAGytQdL-q6";

    public static Connection getConnection() throws ModelException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                    DATABASE_URL,
                    USER,
                    PASSWORD
            );

        } catch (ClassNotFoundException e) {

            throw new ModelException(
                    "Driver MySQL não encontrado", e);

        } catch (SQLException e) {

            throw new ModelException(
                    "Falha na conexão com o banco de dados", e);
        }
    }
}