package com.pulisaglio.progressoRH.service;

import com.pulisaglio.progressoRH.model.Ponto;
import com.pulisaglio.progressoRH.repository.ContratoRepository;
import com.pulisaglio.progressoRH.repository.PontoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PontoService {

    private final PontoRepository pontoRepository;
    private final ContratoRepository contratoRepository;

    public PontoService(PontoRepository pontoRepository, ContratoRepository contratoRepository) {
        this.pontoRepository = pontoRepository;
        this.contratoRepository = contratoRepository;
    }

    public void save(Ponto ponto) {
        if (ponto.getTipo() == null || ponto.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("Ponto tipo must not be empty");
        }
        if (ponto.getDataHora() == null) {
            throw new IllegalArgumentException("Ponto dataHora must not be null");
        }
        if (ponto.getContratoId() == null || contratoRepository.findById(ponto.getContratoId()).isEmpty()) {
            throw new IllegalArgumentException("Contrato does not exist");
        }
        pontoRepository.save(ponto);
    }

    public Optional<Ponto> findById(Integer id) {
        return pontoRepository.findById(id);
    }

    public List<Ponto> findAll() {
        return pontoRepository.findAll();
    }

    public List<Ponto> findByContratoId(Integer contratoId) {
        return pontoRepository.findByContratoId(contratoId);
    }

    public void update(Ponto ponto) {
        if (ponto.getTipo() == null || ponto.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("Ponto tipo must not be empty");
        }
        if (ponto.getDataHora() == null) {
            throw new IllegalArgumentException("Ponto dataHora must not be null");
        }
        if (ponto.getContratoId() == null || contratoRepository.findById(ponto.getContratoId()).isEmpty()) {
            throw new IllegalArgumentException("Contrato does not exist");
        }
        pontoRepository.update(ponto);
    }

    public void deleteById(Integer id) {
        pontoRepository.deleteById(id);
    }
}
