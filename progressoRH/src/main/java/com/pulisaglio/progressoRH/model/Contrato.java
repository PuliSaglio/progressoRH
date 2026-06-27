package com.pulisaglio.progressoRH.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Contrato {

    private Integer idContrato;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private BigDecimal salarioContratual;
    private Integer cargaHorariaSemanal;
    private String funcionarioCpf;
    private Integer cargoId;

    public Contrato() {
    }

    public Contrato(Integer idContrato, LocalDate dataInicio, LocalDate dataFim,
                    BigDecimal salarioContratual, Integer cargaHorariaSemanal,
                    String funcionarioCpf, Integer cargoId) {
        this.idContrato = idContrato;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.salarioContratual = salarioContratual;
        this.cargaHorariaSemanal = cargaHorariaSemanal;
        this.funcionarioCpf = funcionarioCpf;
        this.cargoId = cargoId;
    }

    public Integer getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Integer idContrato) {
        this.idContrato = idContrato;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public BigDecimal getSalarioContratual() {
        return salarioContratual;
    }

    public void setSalarioContratual(BigDecimal salarioContratual) {
        this.salarioContratual = salarioContratual;
    }

    public Integer getCargaHorariaSemanal() {
        return cargaHorariaSemanal;
    }

    public void setCargaHorariaSemanal(Integer cargaHorariaSemanal) {
        this.cargaHorariaSemanal = cargaHorariaSemanal;
    }

    public String getFuncionarioCpf() {
        return funcionarioCpf;
    }

    public void setFuncionarioCpf(String funcionarioCpf) {
        this.funcionarioCpf = funcionarioCpf;
    }

    public Integer getCargoId() {
        return cargoId;
    }

    public void setCargoId(Integer cargoId) {
        this.cargoId = cargoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contrato contrato = (Contrato) o;
        return Objects.equals(idContrato, contrato.idContrato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idContrato);
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "idContrato=" + idContrato +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", salarioContratual=" + salarioContratual +
                ", cargaHorariaSemanal=" + cargaHorariaSemanal +
                ", funcionarioCpf='" + funcionarioCpf + '\'' +
                ", cargoId=" + cargoId +
                '}';
    }
}
