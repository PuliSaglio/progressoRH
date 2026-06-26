package com.pulisaglio.progressoRH.model;

import java.io.Serializable;
import java.util.Objects;

public class TelefoneId implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String numero;
    private String funcionarioCpf;

    public TelefoneId() {
    }

    public TelefoneId(String numero, String funcionarioCpf) {
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
        TelefoneId that = (TelefoneId) o;
        return Objects.equals(numero, that.numero) &&
               Objects.equals(funcionarioCpf, that.funcionarioCpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, funcionarioCpf);
    }

    @Override
    public String toString() {
        return "TelefoneId{" +
                "numero='" + numero + '\'' +
                ", funcionarioCpf='" + funcionarioCpf + '\'' +
                '}';
    }
}
