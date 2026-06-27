package com.pulisaglio.progressoRH.service;

import com.pulisaglio.progressoRH.model.Cargo;
import com.pulisaglio.progressoRH.repository.CargoRepository;
import com.pulisaglio.progressoRH.repository.SetorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CargoService {

    private final CargoRepository cargoRepository;
    private final SetorRepository setorRepository;

    public CargoService(CargoRepository cargoRepository, SetorRepository setorRepository) {
        this.cargoRepository = cargoRepository;
        this.setorRepository = setorRepository;
    }

    public void save(Cargo cargo) {
        if (cargo.getNome() == null || cargo.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Cargo nome must not be empty");
        }
        if (cargo.getLimiteHorasExtras() == null) {
            throw new IllegalArgumentException("Cargo limiteHorasExtras must not be null");
        }
        // validate setor exists
        if (cargo.getSetorId() == null || setorRepository.findById(cargo.getSetorId()).isEmpty()) {
            throw new IllegalArgumentException("Setor does not exist");
        }
        cargoRepository.save(cargo);
    }

    public Optional<Cargo> findById(Integer id) {
        return cargoRepository.findById(id);
    }

    public List<Cargo> findAll() {
        return cargoRepository.findAll();
    }

    public void update(Cargo cargo) {
        if (cargo.getNome() == null || cargo.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Cargo nome must not be empty");
        }
        if (cargo.getLimiteHorasExtras() == null) {
            throw new IllegalArgumentException("Cargo limiteHorasExtras must not be null");
        }
        if (cargo.getSetorId() == null || setorRepository.findById(cargo.getSetorId()).isEmpty()) {
            throw new IllegalArgumentException("Setor does not exist");
        }
        cargoRepository.update(cargo);
    }

    public void deleteById(Integer id) {
        cargoRepository.deleteById(id);
    }
}
