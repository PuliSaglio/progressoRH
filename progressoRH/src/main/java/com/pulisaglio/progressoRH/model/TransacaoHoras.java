package com.pulisaglio.progressoRH.model;

import com.pulisaglio.progressoRH.model.enums.TransacaoStatus;
import com.pulisaglio.progressoRH.model.enums.TransacaoTipo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class TransacaoHoras {

    private Long idTransacao;
    private LocalDate dataReferencia;
    private BigDecimal quantidadeHoras;
    private String justificativa;
    private TransacaoStatus status;
    private String comprovante;
    private TransacaoTipo tipo;
    private Integer contratoId;

    public TransacaoHoras() {
    }

    public TransacaoHoras(Long idTransacao, LocalDate dataReferencia, BigDecimal quantidadeHoras,
                          String justificativa, TransacaoStatus status, String comprovante,
                          TransacaoTipo tipo, Integer contratoId) {
        this.idTransacao = idTransacao;
        this.dataReferencia = dataReferencia;
        this.quantidadeHoras = quantidadeHoras;
        this.justificativa = justificativa;
        this.status = status;
        this.comprovante = comprovante;
        this.tipo = tipo;
        this.contratoId = contratoId;
    }

    public Long getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(Long idTransacao) {
        this.idTransacao = idTransacao;
    }

    public LocalDate getDataReferencia() {
        return dataReferencia;
    }

    public void setDataReferencia(LocalDate dataReferencia) {
        this.dataReferencia = dataReferencia;
    }

    public BigDecimal getQuantidadeHoras() {
        return quantidadeHoras;
    }

    public void setQuantidadeHoras(BigDecimal quantidadeHoras) {
        this.quantidadeHoras = quantidadeHoras;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public TransacaoStatus getStatus() {
        return status;
    }

    public void setStatus(TransacaoStatus status) {
        this.status = status;
    }

    public String getComprovante() {
        return comprovante;
    }

    public void setComprovante(String comprovante) {
        this.comprovante = comprovante;
    }

    public TransacaoTipo getTipo() {
        return tipo;
    }

    public void setTipo(TransacaoTipo tipo) {
        this.tipo = tipo;
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
        TransacaoHoras that = (TransacaoHoras) o;
        return Objects.equals(idTransacao, that.idTransacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTransacao);
    }

    @Override
    public String toString() {
        return "TransacaoHoras{" +
                "idTransacao=" + idTransacao +
                ", dataReferencia=" + dataReferencia +
                ", quantidadeHoras=" + quantidadeHoras +
                ", justificativa='" + justificativa + '\'' +
                ", status=" + status +
                ", comprovante='" + comprovante + '\'' +
                ", tipo=" + tipo +
                ", contratoId=" + contratoId +
                '}';
    }
}
