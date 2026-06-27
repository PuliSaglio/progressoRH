package com.pulisaglio.progressoRH.service;

import com.pulisaglio.progressoRH.model.Desconto;
import com.pulisaglio.progressoRH.repository.DescontoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DescontoService {

    private final DescontoRepository descontoRepository;

    public DescontoService(DescontoRepository descontoRepository) {
        this.descontoRepository = descontoRepository;
    }

    public void save(Desconto desconto) {
        if (desconto.getNome() == null || desconto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Desconto nome must not be empty");
        }
        if (desconto.getTipo() == null || desconto.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("Desconto tipo must not be empty");
        }
        if (desconto.getValor() == null) {
            throw new IllegalArgumentException("Desconto valor must not be null");
        }
        descontoRepository.save(desconto);
    }

    public Optional<Desconto> findById(Integer id) {
        return descontoRepository.findById(id);
    }

    public List<Desconto> findAll() {
        return descontoRepository.findAll();
    }

    public void update(Desconto desconto) {
        if (desconto.getNome() == null || desconto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Desconto nome must not be empty");
        }
        if (desconto.getTipo() == null || desconto.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("Desconto tipo must not be empty");
        }
        if (desconto.getValor() == null) {
            throw new IllegalArgumentException("Desconto valor must not be null");
        }
        descontoRepository.update(desconto);
    }

    public void deleteById(Integer id) {
        descontoRepository.deleteById(id);
    }
}
