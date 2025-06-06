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

public class Inserir extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            // Obtenção dos parâmetros do formulário
            String ds_nome = request.getParameter("nome");
            String dm_valor = request.getParameter("valor");
            String dt_validade = request.getParameter("validade");
            String int_quantidade = request.getParameter("quantidade");
            String id_categoria = request.getParameter("category");
            String ds_observacao = request.getParameter("observacao");
            String dt_cadastro = java.time.LocalDate.now().toString(); // Data atual para o cadastro

            // Novos parâmetros para edição
            String operacao = request.getParameter("operacao");
            String idProduto = request.getParameter("id");

            ArrayList<String> erros = new ArrayList<>();

            // Validação dos campos
            if (ds_nome == null || ds_nome.trim().isEmpty()) {
                erros.add("Preencha o campo Nome");
                request.setAttribute("ds_nome_msg", "Preencha com o Nome");
            }

            if (int_quantidade == null || int_quantidade.trim().isEmpty()) {
                erros.add("Preencha o campo Quantidade");
                request.setAttribute("int_quantidade_msg", "Preencha com a Quantidade");
            }

            if (dt_validade == null || dt_validade.trim().isEmpty()) {
                erros.add("Preencha o campo Validade");
                request.setAttribute("dt_validade_msg", "Preencha com a Data de Validade");
            }

            if (dt_cadastro == null || dt_cadastro.trim().isEmpty()) {
                erros.add("Preencha o campo Cadastro");
                request.setAttribute("dt_cadastro_msg", "Preencha com a Data de Cadastro");
            }

            if (dm_valor == null || dm_valor.trim().isEmpty()) {
                erros.add("Preencha o campo Valor");
                request.setAttribute("dm_valor_msg", "Preencha com o Valor");
            }

            if (id_categoria == null || id_categoria.trim().isEmpty() || id_categoria.equals("null")) {
                erros.add("Selecione uma Categoria");
                request.setAttribute("categoria_msg", "Selecione uma Categoria");
            }

            if (ds_observacao == null || ds_observacao.trim().isEmpty()) {
                erros.add("Preencha o campo Observação");
                request.setAttribute("ds_observacao_msg", "Preencha com a Observação");
            }

            // Se houver erros, retorna para a página de cadastro
            if (!erros.isEmpty()) {
                request.setAttribute("ds_nome", ds_nome);
                request.setAttribute("int_quantidade", int_quantidade);
                request.setAttribute("dt_validade", dt_validade);
                request.setAttribute("dt_cadastro", dt_cadastro);
                request.setAttribute("dm_valor", dm_valor);
                request.setAttribute("categoria", id_categoria);
                request.setAttribute("ds_observacao", ds_observacao);
                request.setAttribute("mensagem", erros);
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }

            int quantidade = Integer.parseInt(int_quantidade);
            LocalDate validade = LocalDate.parse(dt_validade);
            LocalDate cadastro = LocalDate.parse(dt_cadastro);
            BigDecimal valor = converterValorParaBigDecimal(dm_valor);
            int categoriaId = Integer.parseInt(id_categoria);

            
            Produto produto = new Produto();
            produto.setDs_nome(ds_nome);
            produto.setInt_quantidade(quantidade);
            produto.setDt_validade(validade);
            produto.setDt_cadastro(cadastro);
            produto.setDm_valor(valor);
            produto.setId_categoria(categoriaId);
            produto.setDs_observacao(ds_observacao);

            Model model = new Model();

            if ("editar".equalsIgnoreCase(operacao) && idProduto != null && !idProduto.trim().isEmpty()) {
                produto.setId_produto(Integer.parseInt(idProduto));
                model.editar(produto);
                request.setAttribute("mensagem_sucesso", "Produto editado com sucesso!");
            } else {
                model.inserir(produto);
                request.setAttribute("mensagem_sucesso", "Produto cadastrado com sucesso!");
            }

            request.setAttribute("mostrar_estoque", "true");
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } catch (SQLException | NumberFormatException ex) {
            request.setAttribute("mensagem_erro", "Erro ao salvar produto: " + ex.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
    
    private static BigDecimal converterValorParaBigDecimal(String valorFormatado) {
        if (valorFormatado == null || valorFormatado.isEmpty()) {
            return BigDecimal.ZERO;
        }

        String valorLimpo = valorFormatado
            .replaceAll("[^\\d,\\.]", "") 
            .replace(".", "")            
            .replace(",", ".")            

            .trim(); 

        try {
            return new BigDecimal(valorLimpo);
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter valor: [" + valorFormatado + "] => [" + valorLimpo + "]");
            return BigDecimal.ZERO;
        }
    }
}
