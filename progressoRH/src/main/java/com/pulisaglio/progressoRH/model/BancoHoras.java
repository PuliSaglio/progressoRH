package com.pulisaglio.progressoRH.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class BancoHoras {

    private Integer idBancoHoras;
    private LocalDate dataRegistro;
    private BigDecimal saldoAnterior;
    private BigDecimal saldoAtual;
    private BigDecimal horasExtrasMes;
    private Integer contratoId;

    public BancoHoras() {}

    public BancoHoras(
        Integer idBancoHoras,
        LocalDate dataRegistro,
        BigDecimal saldoAnterior,
        BigDecimal saldoAtual,
        BigDecimal horasExtrasMes,
        Integer contratoId
    ) {
        this.idBancoHoras = idBancoHoras;
        this.dataRegistro = dataRegistro;
        this.saldoAnterior = saldoAnterior;
        this.saldoAtual = saldoAtual;
        this.horasExtrasMes = horasExtrasMes;
        this.contratoId = contratoId;
    }

    public Integer getIdBancoHoras() {
        return idBancoHoras;
    }

    public void setIdBancoHoras(Integer idBancoHoras) {
        this.idBancoHoras = idBancoHoras;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public BigDecimal getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(BigDecimal saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public BigDecimal getSaldoAtual() {
        return saldoAtual;
    }

    public void setSaldoAtual(BigDecimal saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    public BigDecimal getHorasExtrasMes() {
        return horasExtrasMes;
    }

    public void setHorasExtrasMes(BigDecimal horasExtrasMes) {
        this.horasExtrasMes = horasExtrasMes;
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
        BancoHoras that = (BancoHoras) o;
        return Objects.equals(idBancoHoras, that.idBancoHoras);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBancoHoras);
    }

    @Override
    public String toString() {
        return (
            "BancoHoras{" +
            "idBancoHoras=" +
            idBancoHoras +
            ", dataRegistro=" +
            dataRegistro +
            ", saldoAnterior=" +
            saldoAnterior +
            ", saldoAtual=" +
            saldoAtual +
            ", horasExtrasMes=" +
            horasExtrasMes +
            ", contratoId=" +
            contratoId +
            '}'
        );
    }
}
