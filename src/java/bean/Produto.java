package bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Produto implements Serializable {

    private int id_produto;
    private String ds_nome;
    private int int_quantidade;
    private LocalDate dt_validade;
    private LocalDate dt_cadastro;
    private BigDecimal dm_valor;
    private String ds_observacao;
    private int id_categoria;

    // Construtor padrão
    public Produto() {}

    // Construtor com parâmetros
    public Produto(int id_produto, String ds_nome, int int_quantidade, String dt_validade, String dt_cadastro, double dm_valor, int id_categoria, String ds_observacao) {
        this.id_produto = id_produto;
        this.ds_nome = ds_nome;
        this.int_quantidade = int_quantidade;
        this.dt_validade = LocalDate.parse(dt_validade);
        this.dt_cadastro = LocalDate.parse(dt_cadastro);
        this.dm_valor = BigDecimal.valueOf(dm_valor);
        this.id_categoria = id_categoria;
        this.ds_observacao = ds_observacao;
    }

    // Getters e Setters
    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public String getDs_nome() {
        return ds_nome;
    }

    public void setDs_nome(String ds_nome) {
        this.ds_nome = ds_nome;
    }

    public int getInt_quantidade() {
        return int_quantidade;
    }

    public void setInt_quantidade(int int_quantidade) {
        this.int_quantidade = int_quantidade;
    }

    public LocalDate getDt_validade() {
        return dt_validade;
    }

    public void setDt_validade(LocalDate dt_validade) {
        this.dt_validade = dt_validade;
    }

    public LocalDate getDt_cadastro() {
        return dt_cadastro;
    }

    public void setDt_cadastro(LocalDate dt_cadastro) {
        this.dt_cadastro = dt_cadastro;
    }

    public BigDecimal getDm_valor() {
        return dm_valor;
    }

    public void setDm_valor(BigDecimal dm_valor) {
        this.dm_valor = dm_valor;
    }

    public String getDs_observacao() {
        return ds_observacao;
    }

    public void setDs_observacao(String ds_observacao) {
        this.ds_observacao = ds_observacao;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }
}
