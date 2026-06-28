package com.pulisaglio.progressoRH.service;

import com.pulisaglio.progressoRH.exception.PeriodoConsolidadoException;
import com.pulisaglio.progressoRH.model.BancoHoras;
import com.pulisaglio.progressoRH.model.Cargo;
import com.pulisaglio.progressoRH.model.Contrato;
import com.pulisaglio.progressoRH.model.Ponto;
import com.pulisaglio.progressoRH.model.TransacaoHoras;
import com.pulisaglio.progressoRH.model.enums.TipoPonto;
import com.pulisaglio.progressoRH.repository.BancoHorasRepository;
import com.pulisaglio.progressoRH.repository.CargoRepository;
import com.pulisaglio.progressoRH.repository.ContratoRepository;
import com.pulisaglio.progressoRH.repository.PontoRepository;
import com.pulisaglio.progressoRH.repository.TransacaoHorasRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BancoHorasService {

    private final BancoHorasRepository bancoHorasRepository;
    private final PontoRepository pontoRepository;
    private final TransacaoHorasRepository transacaoHorasRepository;
    private final ContratoRepository contratoRepository;
    private final CargoRepository cargoRepository;

    public BancoHorasService(
        BancoHorasRepository bancoHorasRepository,
        PontoRepository pontoRepository,
        TransacaoHorasRepository transacaoHorasRepository,
        ContratoRepository contratoRepository,
        CargoRepository cargoRepository
    ) {
        this.bancoHorasRepository = bancoHorasRepository;
        this.pontoRepository = pontoRepository;
        this.transacaoHorasRepository = transacaoHorasRepository;
        this.contratoRepository = contratoRepository;
        this.cargoRepository = cargoRepository;
    }

    /**
     * Consolida o banco de horas de um contrato para o mês/ano informado.
     * Executa os seguintes passos dentro de uma única transação:
     *   1. Verifica Duplicação
     *   2. Verifica saldo anterior e add ao saldo atual
     *   3. Emparelha os pontos entre ENTRADA e SAÍDA
     *   4. Verifica as transações de horas aprovadas
     *   5. Cálculo de horas
     *
     * @param contratoId id do contrato a consolidar
     * @param mes        mês a consolidar (1-12)
     * @param ano        ano a consolidar
     * @return snapshot de BancoHoras persistido, com o ID gerado
     * @throws PeriodoConsolidadoException se o mês já foi consolidado
     */
    @Transactional
    public BancoHoras consolidarMes(Integer contratoId, int mes, int ano) {
        if (
            bancoHorasRepository.existsConsolidadoForContratoAndMonth(
                contratoId,
                ano,
                mes
            )
        ) {
            throw new PeriodoConsolidadoException(
                "Período " +
                    mes +
                    "/" +
                    ano +
                    " do contrato " +
                    contratoId +
                    " já está consolidado no banco de horas"
            );
        }

        BigDecimal saldoAnterior = bancoHorasRepository
            .findLatestByContratoId(contratoId)
            .map(BancoHoras::getSaldoAtual)
            .orElse(BigDecimal.ZERO);

        // Ordena pontos por data_hora ASC e emparelha ENTRADA com a próxima SAÍDA.
        List<Ponto> pontos = pontoRepository.findByContratoIdAndMonth(
            contratoId,
            ano,
            mes
        );
        BigDecimal horasDePontos = calcularHorasDePontos(pontos);

        // Aplica adições (HORA_EXTRA_MANUAL, ATESTADO) e subtrações (FALTA_INJUSTIFICADA, ATRASO).
        List<TransacaoHoras> transacoes =
            transacaoHorasRepository.findAprovadasByContratoIdAndMonth(
                contratoId,
                ano,
                mes
            );
        BigDecimal horasEfetivas = aplicarTransacoes(horasDePontos, transacoes);

        Contrato contrato = contratoRepository
            .findById(contratoId)
            .orElseThrow(() ->
                new NoSuchElementException(
                    "Contrato não encontrado: " + contratoId
                )
            );
        Cargo cargo = cargoRepository
            .findById(contrato.getCargoId())
            .orElseThrow(() ->
                new NoSuchElementException(
                    "Cargo não encontrado: " + contrato.getCargoId()
                )
            );

        int diasUteis = calcularDiasUteis(ano, mes);
        BigDecimal expectativaHoras = calcularExpectativaHoras(
            contrato.getCargaHorariaSemanal(),
            diasUteis
        );

        // diferença = horas efetivas trabalhadas - expectativa do mês
        BigDecimal diferenca = horasEfetivas
            .subtract(expectativaHoras)
            .setScale(2, RoundingMode.HALF_UP);

        BigDecimal horasExtrasMes = null;
        BigDecimal saldoAtual;

        if (diferenca.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal limiteHorasExtras = BigDecimal.valueOf(
                cargo.getLimiteHorasExtras()
            );
            if (diferenca.compareTo(limiteHorasExtras) > 0) {
                // Excedeu o limite: o excesso é pago em dinheiro (horas_extras_mes)
                // e apenas ate o limite é acumulado no saldo
                horasExtrasMes = diferenca
                    .subtract(limiteHorasExtras)
                    .setScale(2, RoundingMode.HALF_UP);
                saldoAtual = saldoAnterior
                    .add(limiteHorasExtras)
                    .setScale(2, RoundingMode.HALF_UP);
            } else {
                // Dentro do limite: toda a diferença positiva é acumulada no saldo
                saldoAtual = saldoAnterior
                    .add(diferenca)
                    .setScale(2, RoundingMode.HALF_UP);
            }
        } else {
            //equilibrio ou saldo pode ficar negativo (débito no banco de horas)
            saldoAtual = saldoAnterior
                .add(diferenca)
                .setScale(2, RoundingMode.HALF_UP);
        }

        LocalDate dataRegistro = YearMonth.of(ano, mes).atEndOfMonth();

        BancoHoras bancoHoras = new BancoHoras();
        bancoHoras.setDataRegistro(dataRegistro);
        bancoHoras.setSaldoAnterior(
            saldoAnterior.setScale(2, RoundingMode.HALF_UP)
        );
        bancoHoras.setSaldoAtual(saldoAtual);
        bancoHoras.setHorasExtrasMes(horasExtrasMes);
        bancoHoras.setContratoId(contratoId);

        bancoHorasRepository.save(bancoHoras);
        return bancoHoras;
    }

    public List<BancoHoras> findByContratoId(Integer contratoId) {
        return bancoHorasRepository.findByContratoId(contratoId);
    }

    /**
     * Funcoes auxiliares
     */

    /**
     * Percorre a lista de pontos (ordenada por data_hora ASC) emparelhando
     * cada ENTRADA com a próxima SAÍDA e acumulando a duração em horas decimais.
     *
     * TODO: considerar casos de ENTRADA sem SAÍDA (ex.: esquecimento do registro) e vice-versa.
     */
    private BigDecimal calcularHorasDePontos(List<Ponto> pontos) {
        BigDecimal totalMinutos = BigDecimal.ZERO;
        Ponto ultimaEntrada = null;

        for (Ponto ponto : pontos) {
            if (ponto.getTipo() == TipoPonto.ENTRADA) {
                ultimaEntrada = ponto;
            } else if (
                ponto.getTipo() == TipoPonto.SAIDA && ultimaEntrada != null
            ) {
                long minutos = Duration.between(
                    ultimaEntrada.getDataHora(),
                    ponto.getDataHora()
                ).toMinutes();
                if (minutos > 0) {
                    totalMinutos = totalMinutos.add(
                        BigDecimal.valueOf(minutos)
                    );
                }
                ultimaEntrada = null;
            }
        }

        return totalMinutos.divide(
            BigDecimal.valueOf(60),
            2,
            RoundingMode.HALF_UP
        );
    }

    /**
     * Aplica a aritmética das transações aprovadas sobre as horas de ponto:
     *   HORA_EXTRA_MANUAL / ATESTADO → soma (abono de ausência ou hora extra registrada)
     *   FALTA_INJUSTIFICADA / ATRASO → subtrai (débito formal)
     */
    private BigDecimal aplicarTransacoes(
        BigDecimal horasBase,
        List<TransacaoHoras> transacoes
    ) {
        BigDecimal total = horasBase;
        for (TransacaoHoras t : transacoes) {
            total = switch (t.getTipo()) {
                case HORA_EXTRA_MANUAL, ATESTADO -> total.add(
                    t.getQuantidadeHoras()
                );
                case FALTA_INJUSTIFICADA, ATRASO -> total.subtract(
                    t.getQuantidadeHoras()
                );
            };
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calcula a expectativa de horas de trabalho para o mês com base nos
     * dias úteis (segunda a sexta) e na carga horária semanal do contrato.
     *
     * Fórmula: (cargaHorariaSemanal × diasUteis) / 5
     * TODO: Levar em consideracao escalas diferentes de trabalho (ex.: 12x36, 6x1, etc.) e feriados regionais/nacionais.
     */
    private BigDecimal calcularExpectativaHoras(
        int cargaHorariaSemanal,
        int diasUteis
    ) {
        return BigDecimal.valueOf(cargaHorariaSemanal)
            .multiply(BigDecimal.valueOf(diasUteis))
            .divide(BigDecimal.valueOf(5), 2, RoundingMode.HALF_UP);
    }

    /**
     * Conta os dias úteis (segunda a sexta-feira) de um mês/ano.
     * Não considera feriados nacionais/regionais (extensão futura).
     */
    private int calcularDiasUteis(int ano, int mes) {
        LocalDate inicio = YearMonth.of(ano, mes).atDay(1);
        LocalDate fim = YearMonth.of(ano, mes).atEndOfMonth();
        int diasUteis = 0;
        LocalDate dia = inicio;
        while (!dia.isAfter(fim)) {
            DayOfWeek dow = dia.getDayOfWeek();
            if (dow != DayOfWeek.SATURDAY && dow != DayOfWeek.SUNDAY) {
                diasUteis++;
            }
            dia = dia.plusDays(1);
        }
        return diasUteis;
    }
}
