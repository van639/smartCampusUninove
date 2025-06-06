package model;

import bean.Produto;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Model implements Serializable {

    private Connection connection = null;
    private String statusMessage;

    public Model() throws SQLException {
        this.connection = DBConnection.getInstance().getConnection();
    }

    // Inserir produto
    public void inserir(Produto produto) {
        String sql = "INSERT INTO tb_produto (ds_nome, int_quantidade, dt_validade, dt_cadastro, dm_valor, id_categoria, ds_observacao) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, produto.getDs_nome());
            ps.setInt(2, produto.getInt_quantidade());
            ps.setDate(3, java.sql.Date.valueOf(produto.getDt_validade()));
            ps.setDate(4, java.sql.Date.valueOf(produto.getDt_cadastro()));
            ps.setBigDecimal(5, produto.getDm_valor());
            ps.setInt(6, produto.getId_categoria());
            ps.setString(7, produto.getDs_observacao());

            ps.executeUpdate();
            this.statusMessage = "Produto inserido com sucesso!";
        } catch (SQLException ex) {
            this.statusMessage = "Erro ao inserir: " + ex.getMessage();
        }
    }
    
    public List<String> listarCategorias() throws SQLException {
        List<String> categorias = new ArrayList<>();
        String sql = "SELECT ds_categoria FROM tb_categoria";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                categorias.add(rs.getString("ds_categoria"));
            }
        }

        return categorias;
    }
    public boolean excluir(int idProduto) {
    String sql = "DELETE FROM tb_produto WHERE id_produto = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, idProduto);
        int linhasAfetadas = ps.executeUpdate();
        if (linhasAfetadas > 0) {
            this.statusMessage = "Produto excluído com sucesso.";
            return true;
        } else {
            this.statusMessage = "Produto não encontrado.";
            return false;
        }
    } catch (SQLException ex) {
        this.statusMessage = "Erro ao excluir produto: " + ex.getMessage();
        return false;
    }
}
    
public void editar(Produto produto) {
        String sql = "UPDATE tb_produto SET ds_nome = ?, int_quantidade = ?, dt_validade = ?, dt_cadastro = ?, dm_valor = ?, id_categoria = ?, ds_observacao = ? WHERE id_produto = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, produto.getDs_nome());
            ps.setInt(2, produto.getInt_quantidade());
            ps.setDate(3, java.sql.Date.valueOf(produto.getDt_validade()));
            ps.setDate(4, java.sql.Date.valueOf(produto.getDt_cadastro()));
            ps.setBigDecimal(5, produto.getDm_valor());
            ps.setInt(6, produto.getId_categoria());
            ps.setString(7, produto.getDs_observacao());
            ps.setInt(8, produto.getId_produto());

            ps.executeUpdate();
            this.statusMessage = "Produto atualizado com sucesso!";
        } catch (SQLException ex) {
            this.statusMessage = "Erro ao atualizar: " + ex.getMessage();
        }
    }

    

    @Override
    public String toString() {
        return this.statusMessage;
    }
}
