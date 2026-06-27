package com.pulisaglio.progressoRH.service;

import com.pulisaglio.progressoRH.model.Contrato;
import com.pulisaglio.progressoRH.repository.CargoRepository;
import com.pulisaglio.progressoRH.repository.ContratoRepository;
import com.pulisaglio.progressoRH.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContratoService {

    private final ContratoRepository contratoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final CargoRepository cargoRepository;

    public ContratoService(ContratoRepository contratoRepository,
                           FuncionarioRepository funcionarioRepository,
                           CargoRepository cargoRepository) {
        this.contratoRepository = contratoRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.cargoRepository = cargoRepository;
    }

    public void save(Contrato contrato) {
        if (contrato.getDataInicio() == null) {
            throw new IllegalArgumentException("Contrato dataInicio must not be null");
        }
        if (contrato.getSalarioContratual() == null) {
            throw new IllegalArgumentException("Contrato salarioContratual must not be null");
        }
        if (contrato.getCargaHorariaSemanal() == null) {
            throw new IllegalArgumentException("Contrato cargaHorariaSemanal must not be null");
        }
        if (contrato.getFuncionarioCpf() == null || contrato.getFuncionarioCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("Contrato funcionarioCpf must not be empty");
        }
        if (funcionarioRepository.findById(contrato.getFuncionarioCpf()).isEmpty()) {
            throw new IllegalArgumentException("Funcionario does not exist");
        }
        if (contrato.getCargoId() == null || cargoRepository.findById(contrato.getCargoId()).isEmpty()) {
            throw new IllegalArgumentException("Cargo does not exist");
        }
        contratoRepository.save(contrato);
    }

    public Optional<Contrato> findById(Integer id) {
        return contratoRepository.findById(id);
    }

    public List<Contrato> findAll() {
        return contratoRepository.findAll();
    }

    public List<Contrato> findByFuncionarioCpf(String cpf) {
        return contratoRepository.findByFuncionarioCpf(cpf);
    }

    public void update(Contrato contrato) {
        if (contrato.getDataInicio() == null) {
            throw new IllegalArgumentException("Contrato dataInicio must not be null");
        }
        if (contrato.getSalarioContratual() == null) {
            throw new IllegalArgumentException("Contrato salarioContratual must not be null");
        }
        if (contrato.getCargaHorariaSemanal() == null) {
            throw new IllegalArgumentException("Contrato cargaHorariaSemanal must not be null");
        }
        if (contrato.getFuncionarioCpf() == null || contrato.getFuncionarioCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("Contrato funcionarioCpf must not be empty");
        }
        if (funcionarioRepository.findById(contrato.getFuncionarioCpf()).isEmpty()) {
            throw new IllegalArgumentException("Funcionario does not exist");
        }
        if (contrato.getCargoId() == null || cargoRepository.findById(contrato.getCargoId()).isEmpty()) {
            throw new IllegalArgumentException("Cargo does not exist");
        }
        contratoRepository.update(contrato);
    }

    public void deleteById(Integer id) {
        contratoRepository.deleteById(id);
    }
}
