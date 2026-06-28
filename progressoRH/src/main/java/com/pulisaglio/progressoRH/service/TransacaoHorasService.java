package com.pulisaglio.progressoRH.service;

import com.pulisaglio.progressoRH.exception.PeriodoConsolidadoException;
import com.pulisaglio.progressoRH.model.TransacaoHoras;
import com.pulisaglio.progressoRH.model.enums.TransacaoStatus;
import com.pulisaglio.progressoRH.model.enums.TransacaoTipo;
import com.pulisaglio.progressoRH.repository.BancoHorasRepository;
import com.pulisaglio.progressoRH.repository.ContratoRepository;
import com.pulisaglio.progressoRH.repository.TransacaoHorasRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransacaoHorasService {

    private final TransacaoHorasRepository transacaoHorasRepository;
    private final ContratoRepository contratoRepository;
    private final BancoHorasRepository bancoHorasRepository;

    public TransacaoHorasService(
        TransacaoHorasRepository transacaoHorasRepository,
        ContratoRepository contratoRepository,
        BancoHorasRepository bancoHorasRepository
    ) {
        this.transacaoHorasRepository = transacaoHorasRepository;
        this.contratoRepository = contratoRepository;
        this.bancoHorasRepository = bancoHorasRepository;
    }

    @Transactional
    public void save(TransacaoHoras transacao) {
        validarCampos(transacao);
        validarContrato(transacao.getContratoId());
        validarPeriodoAberto(
            transacao.getContratoId(),
            transacao.getDataReferencia()
        );

        transacao.setStatus(resolverStatusPadrao(transacao.getTipo()));
        transacaoHorasRepository.save(transacao);
    }

    public Optional<TransacaoHoras> findById(Long id) {
        return transacaoHorasRepository.findById(id);
    }

    public List<TransacaoHoras> findAll() {
        return transacaoHorasRepository.findAll();
    }

    public List<TransacaoHoras> findByContratoId(Integer contratoId) {
        return transacaoHorasRepository.findByContratoId(contratoId);
    }

    public List<TransacaoHoras> findByPeriodo(
        LocalDate dataInicial,
        LocalDate dataFinal
    ) {
        if (dataFinal == null) {
            throw new IllegalArgumentException("dataFinal must not be null");
        }
        LocalDate efectiveDataInicial =
            dataInicial != null ? dataInicial : LocalDate.now();
        return transacaoHorasRepository.findByPeriodo(
            efectiveDataInicial,
            dataFinal
        );
    }

    /**
     * Atualiza uma transação existente.
     * Valida período aberto com base na nova data_referencia informada.
     */
    @Transactional
    public void update(TransacaoHoras transacao) {
        validarCampos(transacao);
        validarContrato(transacao.getContratoId());
        validarPeriodoAberto(
            transacao.getContratoId(),
            transacao.getDataReferencia()
        );
        transacaoHorasRepository.update(transacao);
    }

    @Transactional
    public void deleteById(Long id) {
        TransacaoHoras existing = transacaoHorasRepository
            .findById(id)
            .orElseThrow(() ->
                new NoSuchElementException(
                    "TransacaoHoras não encontrada: " + id
                )
            );
        validarPeriodoAberto(
            existing.getContratoId(),
            existing.getDataReferencia()
        );
        transacaoHorasRepository.deleteById(id);
    }

    private void validarCampos(TransacaoHoras transacao) {
        if (transacao.getDataReferencia() == null) {
            throw new IllegalArgumentException(
                "dataReferencia must not be null"
            );
        }
        if (transacao.getQuantidadeHoras() == null) {
            throw new IllegalArgumentException(
                "quantidadeHoras must not be null"
            );
        }
        if (
            transacao.getJustificativa() == null ||
            transacao.getJustificativa().trim().isEmpty()
        ) {
            throw new IllegalArgumentException(
                "justificativa must not be empty"
            );
        }
        if (transacao.getTipo() == null) {
            throw new IllegalArgumentException("tipo must not be null");
        }
        if (transacao.getContratoId() == null) {
            throw new IllegalArgumentException("contratoId must not be null");
        }
    }

    private void validarContrato(Integer contratoId) {
        if (contratoRepository.findById(contratoId).isEmpty()) {
            throw new IllegalArgumentException("Contrato does not exist");
        }
    }

    private void validarPeriodoAberto(
        Integer contratoId,
        java.time.LocalDate dataReferencia
    ) {
        int year = dataReferencia.getYear();
        int month = dataReferencia.getMonthValue();
        if (
            bancoHorasRepository.existsConsolidadoForContratoAndMonth(
                contratoId,
                year,
                month
            )
        ) {
            throw new PeriodoConsolidadoException(
                "Não é possível alterar transações de um período com banco de horas já consolidado"
            );
        }
    }

    private TransacaoStatus resolverStatusPadrao(TransacaoTipo tipo) {
        return switch (tipo) {
            case FALTA_INJUSTIFICADA, ATRASO -> TransacaoStatus.APROVADO;
            case ATESTADO, HORA_EXTRA_MANUAL -> TransacaoStatus.PENDENTE;
        };
    }
}
