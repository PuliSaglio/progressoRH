package com.pulisaglio.progressoRH.controller;

import com.pulisaglio.progressoRH.model.Telefone;
import com.pulisaglio.progressoRH.service.TelefoneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/telefones")
public class TelefoneController {

    private final TelefoneService telefoneService;

    public TelefoneController(TelefoneService telefoneService) {
        this.telefoneService = telefoneService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Telefone telefone) {
        try {
            telefoneService.save(telefone);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{numero}/{cpf}")
    public ResponseEntity<Telefone> getById(@PathVariable String numero, @PathVariable String cpf) {
        Optional<Telefone> telefone = telefoneService.findById(numero, cpf);
        return telefone
            .map(t -> ResponseEntity.status(HttpStatus.OK).body(t))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/funcionario/{cpf}")
    public ResponseEntity<List<Telefone>> getByFuncionarioCpf(@PathVariable String cpf) {
        List<Telefone> telefones = telefoneService.findByFuncionarioCpf(cpf);
        return ResponseEntity.status(HttpStatus.OK).body(telefones);
    }

    @GetMapping
    public ResponseEntity<List<Telefone>> getAll() {
        List<Telefone> telefones = telefoneService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(telefones);
    }

    @PutMapping("/{numero}/{cpf}")
    public ResponseEntity<Void> update(@PathVariable String numero, @PathVariable String cpf,
                                       @RequestBody Telefone telefone) {
        try {
            Optional<Telefone> existing = telefoneService.findById(numero, cpf);
            if (existing.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            telefone.setFuncionarioCpf(cpf);
            telefoneService.update(telefone, numero);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{numero}/{cpf}")
    public ResponseEntity<Void> delete(@PathVariable String numero, @PathVariable String cpf) {
        try {
            Optional<Telefone> existing = telefoneService.findById(numero, cpf);
            if (existing.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            telefoneService.deleteById(numero, cpf);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
