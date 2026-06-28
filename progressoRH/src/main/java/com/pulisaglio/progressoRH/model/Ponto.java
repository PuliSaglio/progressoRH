package com.pulisaglio.progressoRH.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Ponto {

    private Integer idPonto;
    private String tipo;
    private LocalDateTime dataHora;
    private Integer contratoId;

    public Ponto() {
    }

    public Ponto(Integer idPonto, String tipo, LocalDateTime dataHora, Integer contratoId) {
        this.idPonto = idPonto;
        this.tipo = tipo;
        this.dataHora = dataHora;
        this.contratoId = contratoId;
    }

    public Integer getIdPonto() {
        return idPonto;
    }

    public void setIdPonto(Integer idPonto) {
        this.idPonto = idPonto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Integer getContratoId() {
        return contratoId;
    }

    public void setContratoId(Integer contratoId) {
        this.contratoId = contratoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ponto ponto = (Ponto) o;
        return Objects.equals(idPonto, ponto.idPonto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPonto);
    }

    @Override
    public String toString() {
        return "Ponto{" +
                "idPonto=" + idPonto +
                ", tipo='" + tipo + '\'' +
                ", dataHora=" + dataHora +
                ", contratoId=" + contratoId +
                '}';
    }
}
