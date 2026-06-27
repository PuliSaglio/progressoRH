package com.pulisaglio.progressoRH.model;

import java.util.Objects;

public class Cargo {
    private Integer idCargo;
    private String nome;
    private String descricao;
    private Integer limiteHorasExtras;
    private Integer setorId;

    public Cargo() {
    }

    public Cargo(Integer idCargo, String nome, String descricao, Integer limiteHorasExtras, Integer setorId) {
        this.idCargo = idCargo;
        this.nome = nome;
        this.descricao = descricao;
        this.limiteHorasExtras = limiteHorasExtras;
        this.setorId = setorId;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getLimiteHorasExtras() {
        return limiteHorasExtras;
    }

    public void setLimiteHorasExtras(Integer limiteHorasExtras) {
        this.limiteHorasExtras = limiteHorasExtras;
    }

    public Integer getSetorId() {
        return setorId;
    }

    public void setSetorId(Integer setorId) {
        this.setorId = setorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cargo cargo = (Cargo) o;
        return Objects.equals(idCargo, cargo.idCargo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCargo);
    }

    @Override
    public String toString() {
        return "Cargo{" +
                "idCargo=" + idCargo +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", limiteHorasExtras=" + limiteHorasExtras +
                ", setorId=" + setorId +
                '}';
    }
}
