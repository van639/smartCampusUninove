package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import model.DBConnection;

@WebServlet("/ListarProdutos")
public class ListarProdutos extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (
            PrintWriter out = response.getWriter();
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT p.id_produto, p.ds_nome, p.int_quantidade, p.dt_validade, p.dt_cadastro, " +
                "p.dm_valor, p.ds_observacao, p.id_categoria, c.ds_nome AS categoria " +
                "FROM tb_produto AS p INNER JOIN tb_categoria AS c ON c.id_categoria = p.id_categoria"
            );
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

                int id = rs.getInt("id_produto");
                String nome = rs.getString("ds_nome");
                String categoria = rs.getString("categoria");
                String validade = formatarData(rs.getString("dt_validade"));
                String quantidade = rs.getString("int_quantidade");
                double valorNumerico = rs.getDouble("dm_valor");
                String valorFormatado = formatarParaReais(valorNumerico);
                String observacao = rs.getString("ds_observacao");
                int idCategoria = rs.getInt("id_categoria");
                // Escapando aspas e tratando nulos
                nome = nome != null ? nome.replace("\"", "\\\"") : "";
                categoria = categoria != null ? categoria.replace("\"", "\\\"") : "";
                validade = validade != null ? validade.replace("\"", "\\\"") : "";
                quantidade = quantidade != null ? quantidade.replace("\"", "\\\"") : "";
                valorFormatado = valorFormatado != null ? valorFormatado.replace("\"", "\\\"") : "";
                observacao = observacao != null ? observacao.replace("\"", "\\\"") : "";

                json.append("{")
                    .append("\"id\":").append(id).append(",")
                    .append("\"nome\":\"").append(nome).append("\",")
                    .append("\"validade\":\"").append(validade).append("\",")
                    .append("\"quantidade\":\"").append(quantidade).append("\",")
                    .append("\"valor\":\"").append(valorFormatado).append("\",")
                    .append("\"categoria\":\"").append(categoria).append("\",")
                    .append("\"id_categoria\":").append(idCategoria).append(",")
                    .append("\"observacao\":\"").append(observacao).append("\"")
                    .append("}");
            }

            json.append("]");
            out.print(json.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private static String formatarData(String dataBruta) {
        if (dataBruta == null || dataBruta.isEmpty()) return "";

        DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatoSaida = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDateTime data = LocalDateTime.parse(dataBruta, formatoEntrada);
        return data.format(formatoSaida);
    }

    private static String formatarParaReais(double valor) {
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return formatoMoeda.format(valor);
    }
}
