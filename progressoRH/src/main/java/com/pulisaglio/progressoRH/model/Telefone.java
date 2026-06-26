package com.pulisaglio.progressoRH.model;

import java.util.Objects;

public class Telefone {
    private String numero;
    private String funcionarioCpf;

    public Telefone() {
    }

    public Telefone(String numero, String funcionarioCpf) {
        this.numero = numero;
        this.funcionarioCpf = funcionarioCpf;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFuncionarioCpf() {
        return funcionarioCpf;
    }

    public void setFuncionarioCpf(String funcionarioCpf) {
        this.funcionarioCpf = funcionarioCpf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Telefone telefone = (Telefone) o;
        return Objects.equals(numero, telefone.numero) &&
               Objects.equals(funcionarioCpf, telefone.funcionarioCpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, funcionarioCpf);
    }

    @Override
    public String toString() {
        return "Telefone{" +
                "numero='" + numero + '\'' +
                ", funcionarioCpf='" + funcionarioCpf + '\'' +
                '}';
    }
}
