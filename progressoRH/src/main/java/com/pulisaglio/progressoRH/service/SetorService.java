package com.pulisaglio.progressoRH.service;

import com.pulisaglio.progressoRH.model.Setor;
import com.pulisaglio.progressoRH.repository.SetorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SetorService {

    private final SetorRepository setorRepository;

    public SetorService(SetorRepository setorRepository) {
        this.setorRepository = setorRepository;
    }

    public void save(Setor setor) {
        if (setor.getNome() == null || setor.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Setor nome must not be empty");
        }
        setorRepository.save(setor);
    }

    public Optional<Setor> findById(Integer id) {
        return setorRepository.findById(id);
    }

    public List<Setor> findAll() {
        return setorRepository.findAll();
    }

    public void update(Setor setor) {
        if (setor.getNome() == null || setor.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Setor nome must not be empty");
        }
        setorRepository.update(setor);
    }

    public void deleteById(Integer id) {
        setorRepository.deleteById(id);
    }
}
