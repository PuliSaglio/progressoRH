package com.pulisaglio.progressoRH.controller;

import com.pulisaglio.progressoRH.model.Desconto;
import com.pulisaglio.progressoRH.service.DescontoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/descontos")
public class DescontoController {

    private final DescontoService descontoService;

    public DescontoController(DescontoService descontoService) {
        this.descontoService = descontoService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Desconto desconto) {
        try {
            descontoService.save(desconto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Desconto>> getAll() {
        List<Desconto> list = descontoService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Desconto> getById(@PathVariable Integer id) {
        Optional<Desconto> opt = descontoService.findById(id);
        return opt.map(d -> ResponseEntity.ok(d)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody Desconto desconto) {
        try {
            Optional<Desconto> existing = descontoService.findById(id);
            if (existing.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            desconto.setIdDesconto(id);
            descontoService.update(desconto);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<Desconto> existing = descontoService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        descontoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
