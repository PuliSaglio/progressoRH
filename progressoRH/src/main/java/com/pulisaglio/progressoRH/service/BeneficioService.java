package com.pulisaglio.progressoRH.service;

import com.pulisaglio.progressoRH.model.Beneficio;
import com.pulisaglio.progressoRH.repository.BeneficioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BeneficioService {

    private final BeneficioRepository beneficioRepository;

    public BeneficioService(BeneficioRepository beneficioRepository) {
        this.beneficioRepository = beneficioRepository;
    }

    public void save(Beneficio beneficio) {
        if (beneficio.getNome() == null || beneficio.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Beneficio nome must not be empty");
        }
        if (beneficio.getTipo() == null || beneficio.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("Beneficio tipo must not be empty");
        }
        if (beneficio.getValor() == null) {
            throw new IllegalArgumentException("Beneficio valor must not be null");
        }
        beneficioRepository.save(beneficio);
    }

    public Optional<Beneficio> findById(Integer id) {
        return beneficioRepository.findById(id);
    }

    public List<Beneficio> findAll() {
        return beneficioRepository.findAll();
    }

    public void update(Beneficio beneficio) {
        if (beneficio.getNome() == null || beneficio.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Beneficio nome must not be empty");
        }
        if (beneficio.getTipo() == null || beneficio.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("Beneficio tipo must not be empty");
        }
        if (beneficio.getValor() == null) {
            throw new IllegalArgumentException("Beneficio valor must not be null");
        }
        beneficioRepository.update(beneficio);
    }

    public void deleteById(Integer id) {
        beneficioRepository.deleteById(id);
    }
}
