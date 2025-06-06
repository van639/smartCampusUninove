package controller;

import bean.Produto;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import model.Model;

public class Editar extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            // Obtenção dos parâmetros do formulário
            String idProduto = request.getParameter("id");
            String ds_nome = request.getParameter("nome");
            String dm_valor = request.getParameter("valor");
            String dt_validade = request.getParameter("validade");
            String int_quantidade = request.getParameter("quantidade");
            String id_categoria = request.getParameter("category");
            String ds_observacao = request.getParameter("observacao");
            String dt_cadastro = java.time.LocalDate.now().toString(); // Data atualizada para edição

            ArrayList<String> erros = new ArrayList<>();

            // Validação dos campos
            if (idProduto == null || idProduto.trim().isEmpty()) {
                erros.add("ID do produto inválido para edição");
            }

            if (ds_nome == null || ds_nome.trim().isEmpty()) {
                erros.add("Preencha o campo Nome");
            }

            if (int_quantidade == null || int_quantidade.trim().isEmpty()) {
                erros.add("Preencha o campo Quantidade");
            }

            if (dt_validade == null || dt_validade.trim().isEmpty()) {
                erros.add("Preencha o campo Validade");
            }

            if (dm_valor == null || dm_valor.trim().isEmpty()) {
                erros.add("Preencha o campo Valor");
            }

            if (id_categoria == null || id_categoria.trim().isEmpty() || id_categoria.equals("null")) {
                erros.add("Selecione uma Categoria");
            }

            if (ds_observacao == null || ds_observacao.trim().isEmpty()) {
                erros.add("Preencha o campo Observação");
            }

            if (!erros.isEmpty()) {
                request.setAttribute("mensagem", erros);
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }

            // Conversão dos parâmetros
            int quantidade = Integer.parseInt(int_quantidade);
            LocalDate validade = LocalDate.parse(dt_validade);
            LocalDate cadastro = LocalDate.parse(dt_cadastro);
            BigDecimal valor = converterValorParaBigDecimal(dm_valor);
            int categoriaId = Integer.parseInt(id_categoria);
            int produtoId = Integer.parseInt(idProduto);

            Produto produto = new Produto();
            produto.setId_produto(produtoId);
            produto.setDs_nome(ds_nome);
            produto.setInt_quantidade(quantidade);
            produto.setDt_validade(validade);
            produto.setDt_cadastro(cadastro);
            produto.setDm_valor(valor);
            produto.setId_categoria(categoriaId);
            produto.setDs_observacao(ds_observacao);

            Model model = new Model();
            model.editar(produto);

            request.setAttribute("mensagem_sucesso", "Produto editado com sucesso!");
            request.setAttribute("mostrar_estoque", "true");
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } catch (SQLException | NumberFormatException ex) {
            request.setAttribute("mensagem_erro", "Erro ao editar produto: " + ex.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    private static BigDecimal converterValorParaBigDecimal(String valorFormatado) {
        if (valorFormatado == null || valorFormatado.isEmpty()) {
            return BigDecimal.ZERO;
        }

        String valorLimpo = valorFormatado
            .replaceAll("[^\\d,\\.]", "") // remove R$, espaços, etc
            .replace(".", "")             // remove separador de milhar
            .replace(",", ".")            // transforma vírgula decimal em ponto
            .trim();

        try {
            return new BigDecimal(valorLimpo);
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter valor: [" + valorFormatado + "] => [" + valorLimpo + "]");
            return BigDecimal.ZERO;
        }
    }
}
