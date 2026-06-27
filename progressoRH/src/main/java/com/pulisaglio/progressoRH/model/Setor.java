package com.pulisaglio.progressoRH.model;

import java.util.Objects;

public class Setor {
    private Integer idSetor;
    private String nome;

    public Setor() {
    }

    public Setor(Integer idSetor, String nome) {
        this.idSetor = idSetor;
        this.nome = nome;
    }

    public Integer getIdSetor() {
        return idSetor;
    }

    public void setIdSetor(Integer idSetor) {
        this.idSetor = idSetor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Setor setor = (Setor) o;
        return Objects.equals(idSetor, setor.idSetor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSetor);
    }

    @Override
    public String toString() {
        return "Setor{" +
                "idSetor=" + idSetor +
                ", nome='" + nome + '\'' +
                '}';
    }
}
