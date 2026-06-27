package com.pulisaglio.progressoRH.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Desconto {
    private Integer idDesconto;
    private String nome;
    private String tipo;
    private BigDecimal valor;
    private String descricao;

    public Desconto() {
    }

    public Desconto(Integer idDesconto, String nome, String tipo, BigDecimal valor, String descricao) {
        this.idDesconto = idDesconto;
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
        this.descricao = descricao;
    }

    public Integer getIdDesconto() {
        return idDesconto;
    }

    public void setIdDesconto(Integer idDesconto) {
        this.idDesconto = idDesconto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Desconto desconto = (Desconto) o;
        return Objects.equals(idDesconto, desconto.idDesconto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDesconto);
    }

    @Override
    public String toString() {
        return "Desconto{" +
                "idDesconto=" + idDesconto +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", valor=" + valor +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
