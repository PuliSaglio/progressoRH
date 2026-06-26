package com.pulisaglio.progressoRH.service;

import com.pulisaglio.progressoRH.model.Telefone;
import com.pulisaglio.progressoRH.repository.FuncionarioRepository;
import com.pulisaglio.progressoRH.repository.TelefoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TelefoneService {

    private final TelefoneRepository telefoneRepository;
    private final FuncionarioRepository funcionarioRepository;

    public TelefoneService(TelefoneRepository telefoneRepository, FuncionarioRepository funcionarioRepository) {
        this.telefoneRepository = telefoneRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public void save(Telefone telefone) {
        validateTelefone(telefone);
        validarFuncionarioExiste(telefone.getFuncionarioCpf());
        telefoneRepository.save(telefone);
    }

    public Optional<Telefone> findById(String numero, String funcionarioCpf) {
        if (numero == null || numero.isBlank() || funcionarioCpf == null || funcionarioCpf.isBlank()) {
            return Optional.empty();
        }
        return telefoneRepository.findById(numero, funcionarioCpf);
    }

    public List<Telefone> findByFuncionarioCpf(String funcionarioCpf) {
        if (funcionarioCpf == null || funcionarioCpf.isBlank()) {
            return List.of();
        }
        return telefoneRepository.findByFuncionarioCpf(funcionarioCpf);
    }

    public List<Telefone> findAll() {
        return telefoneRepository.findAll();
    }

    public void update(Telefone telefone, String numeroAntigo) {
        validateTelefone(telefone);
        validarFuncionarioExiste(telefone.getFuncionarioCpf());
        telefoneRepository.update(telefone, numeroAntigo);
    }

    public void deleteById(String numero, String funcionarioCpf) {
        if (numero == null || numero.isBlank() || funcionarioCpf == null || funcionarioCpf.isBlank()) {
            throw new IllegalArgumentException("Número e CPF não podem ser vazios");
        }
        telefoneRepository.deleteById(numero, funcionarioCpf);
    }

    public void deleteByFuncionarioCpf(String funcionarioCpf) {
        if (funcionarioCpf == null || funcionarioCpf.isBlank()) {
            throw new IllegalArgumentException("CPF não pode ser vazio");
        }
        telefoneRepository.deleteByFuncionarioCpf(funcionarioCpf);
    }

    private void validateTelefone(Telefone telefone) {
        if (telefone == null) {
            throw new IllegalArgumentException("Telefone não pode ser nulo");
        }
        if (telefone.getNumero() == null || telefone.getNumero().isBlank()) {
            throw new IllegalArgumentException("Número do telefone não pode ser vazio");
        }
        if (telefone.getFuncionarioCpf() == null || telefone.getFuncionarioCpf().isBlank()) {
            throw new IllegalArgumentException("CPF do funcionário não pode ser vazio");
        }
    }

    private void validarFuncionarioExiste(String cpf) {
        if (funcionarioRepository.findById(cpf).isEmpty()) {
            throw new IllegalArgumentException("Funcionário com CPF " + cpf + " não existe");
        }
    }
}
