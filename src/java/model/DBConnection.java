package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Driver para MySQL
    private final String driver = "com.mysql.cj.jdbc.Driver";

    // Configurações de conexão
    private final String url = "jdbc:mysql://localhost:3306/smartcampus?useSSL=false&allowPublicKeyRetrieval=true";
    private final String user = "root";
    private final String password = "12345678";

    private static DBConnection conexao = null;

    private DBConnection() throws SQLException {
         try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado", e);
        }
    }

    public Connection getConnection() throws SQLException {
        Connection conn;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new SQLException("Falha ao conectar ao banco de dados MySQL: " + e.getMessage(), e);
        }
        return conn;
    }

    public static DBConnection getInstance() throws SQLException {
        if (conexao == null) {
            conexao = new DBConnection();
        }
        return conexao;
    }
}
