package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import model.DBConnection;

@WebServlet("/ListarCategorias")
public class ListarCategorias extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (
            PrintWriter out = response.getWriter();
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id_categoria, ds_nome FROM tb_categoria");
            ResultSet rs = ps.executeQuery()
        ) {
            StringBuilder json = new StringBuilder("[");
            boolean first = true;

            while (rs.next()) {
                if (!first) {
                    json.append(",");
                } else {
                    first = false;
                }

                int id = rs.getInt("id_categoria");
                String nome = rs.getString("ds_nome").replace("\"", "\\\"");

                json.append("{")
                    .append("\"id\":").append(id).append(",")
                    .append("\"nome\":\"").append(nome).append("\"")
                    .append("}");
            }

            json.append("]");
            out.print(json.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
