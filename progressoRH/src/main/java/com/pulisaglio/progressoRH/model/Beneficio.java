package com.pulisaglio.progressoRH.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Beneficio {
    private Integer idBeneficio;
    private String nome;
    private String tipo;
    private BigDecimal valor;
    private String descricao;

    public Beneficio() {
    }

    public Beneficio(Integer idBeneficio, String nome, String tipo, BigDecimal valor, String descricao) {
        this.idBeneficio = idBeneficio;
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
        this.descricao = descricao;
    }

    public Integer getIdBeneficio() {
        return idBeneficio;
    }

    public void setIdBeneficio(Integer idBeneficio) {
        this.idBeneficio = idBeneficio;
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
        Beneficio that = (Beneficio) o;
        return Objects.equals(idBeneficio, that.idBeneficio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBeneficio);
    }

    @Override
    public String toString() {
        return "Beneficio{" +
                "idBeneficio=" + idBeneficio +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", valor=" + valor +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
