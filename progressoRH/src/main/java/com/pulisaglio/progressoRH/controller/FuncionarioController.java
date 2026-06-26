package com.pulisaglio.progressoRH.controller;

import com.pulisaglio.progressoRH.model.Funcionario;
import com.pulisaglio.progressoRH.service.FuncionarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Funcionario funcionario) {
        try {
            funcionarioService.save(funcionario);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Funcionario> getById(@PathVariable String cpf) {
        Optional<Funcionario> funcionario = funcionarioService.findById(cpf);
        return funcionario
            .map(f -> ResponseEntity.status(HttpStatus.OK).body(f))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> getAll() {
        List<Funcionario> funcionarios = funcionarioService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(funcionarios);
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Void> update(@PathVariable String cpf, @RequestBody Funcionario funcionario) {
        try {
            Optional<Funcionario> existing = funcionarioService.findById(cpf);
            if (existing.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            funcionario.setCpf(cpf);
            funcionarioService.update(funcionario);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> delete(@PathVariable String cpf) {
        try {
            Optional<Funcionario> existing = funcionarioService.findById(cpf);
            if (existing.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            funcionarioService.deleteById(cpf);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
