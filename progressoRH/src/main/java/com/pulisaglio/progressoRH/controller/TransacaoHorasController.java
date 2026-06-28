package com.pulisaglio.progressoRH.controller;

import com.pulisaglio.progressoRH.exception.PeriodoConsolidadoException;
import com.pulisaglio.progressoRH.model.TransacaoHoras;
import com.pulisaglio.progressoRH.service.TransacaoHorasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/transacoes-horas")
public class TransacaoHorasController {

    private final TransacaoHorasService transacaoHorasService;

    public TransacaoHorasController(TransacaoHorasService transacaoHorasService) {
        this.transacaoHorasService = transacaoHorasService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody TransacaoHoras transacao) {
        try {
            transacaoHorasService.save(transacao);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (PeriodoConsolidadoException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<TransacaoHoras>> getAll() {
        List<TransacaoHoras> list = transacaoHorasService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransacaoHoras> getById(@PathVariable Long id) {
        Optional<TransacaoHoras> opt = transacaoHorasService.findById(id);
        return opt.map(t -> ResponseEntity.ok(t))
                  .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/contrato/{contratoId}")
    public ResponseEntity<List<TransacaoHoras>> getByContratoId(@PathVariable Integer contratoId) {
        List<TransacaoHoras> list = transacaoHorasService.findByContratoId(contratoId);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody TransacaoHoras transacao) {
        try {
            Optional<TransacaoHoras> existing = transacaoHorasService.findById(id);
            if (existing.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            transacao.setIdTransacao(id);
            transacaoHorasService.update(transacao);
            return ResponseEntity.ok().build();
        } catch (PeriodoConsolidadoException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            // A busca + validação de período acontecem atomicamente no service (@Transactional)
            transacaoHorasService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (PeriodoConsolidadoException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
