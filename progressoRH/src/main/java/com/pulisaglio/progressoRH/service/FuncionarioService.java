package com.pulisaglio.progressoRH.service;

import com.pulisaglio.progressoRH.model.Funcionario;
import com.pulisaglio.progressoRH.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public void save(Funcionario funcionario) {
        validateFuncionario(funcionario);
        funcionarioRepository.save(funcionario);
    }

    public Optional<Funcionario> findById(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            return Optional.empty();
        }
        return funcionarioRepository.findById(cpf);
    }

    public List<Funcionario> findAll() {
        return funcionarioRepository.findAll();
    }

    public void update(Funcionario funcionario) {
        validateFuncionario(funcionario);
        funcionarioRepository.update(funcionario);
    }

    public void deleteById(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("CPF cannot be null or empty");
        }
        funcionarioRepository.deleteById(cpf);
    }

    private void validateFuncionario(Funcionario funcionario) {
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionario cannot be null");
        }
        if (funcionario.getCpf() == null || funcionario.getCpf().isBlank()) {
            throw new IllegalArgumentException("CPF cannot be null or empty");
        }
        if (funcionario.getNome() == null || funcionario.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome cannot be null or empty");
        }
        if (funcionario.getSobrenome() == null || funcionario.getSobrenome().isBlank()) {
            throw new IllegalArgumentException("Sobrenome cannot be null or empty");
        }
        if (funcionario.getDataNascimento() == null) {
            throw new IllegalArgumentException("Data de nascimento cannot be null");
        }
        if (funcionario.getEmail() == null || funcionario.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (funcionario.getLogradouro() == null || funcionario.getLogradouro().isBlank()) {
            throw new IllegalArgumentException("Logradouro cannot be null or empty");
        }
        if (funcionario.getNumero() == null || funcionario.getNumero().isBlank()) {
            throw new IllegalArgumentException("Numero cannot be null or empty");
        }
        if (funcionario.getCep() == null || funcionario.getCep().isBlank()) {
            throw new IllegalArgumentException("CEP cannot be null or empty");
        }
        if (funcionario.getBairro() == null || funcionario.getBairro().isBlank()) {
            throw new IllegalArgumentException("Bairro cannot be null or empty");
        }
    }
}
