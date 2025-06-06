package model;

import java.sql.Connection;
import java.sql.SQLException;

public class ConexaoTeste {
    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            System.out.println("✅ Conexão realizada com sucesso!");
            conn.close();
        } catch (SQLException e) {
            System.out.println("❌ Erro ao conectar: " + e.getMessage());
        }
    }
}
