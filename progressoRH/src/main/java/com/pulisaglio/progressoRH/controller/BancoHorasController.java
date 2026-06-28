package com.pulisaglio.progressoRH.controller;

import com.pulisaglio.progressoRH.exception.PeriodoConsolidadoException;
import com.pulisaglio.progressoRH.model.BancoHoras;
import com.pulisaglio.progressoRH.service.BancoHorasService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/banco-horas")
public class BancoHorasController {

    private final BancoHorasService bancoHorasService;

    public BancoHorasController(BancoHorasService bancoHorasService) {
        this.bancoHorasService = bancoHorasService;
    }

    @PostMapping("/consolidar")
    public ResponseEntity<BancoHoras> consolidar(
        @RequestParam Integer contratoId,
        @RequestParam int mes,
        @RequestParam int ano
    ) {
        try {
            BancoHoras bancoHoras = bancoHorasService.consolidarMes(
                contratoId,
                mes,
                ano
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(bancoHoras);
        } catch (PeriodoConsolidadoException e) {
            return ResponseEntity.status(
                HttpStatus.UNPROCESSABLE_ENTITY
            ).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(
                HttpStatus.INTERNAL_SERVER_ERROR
            ).build();
        }
    }

    @GetMapping("/contrato/{contratoId}")
    public ResponseEntity<List<BancoHoras>> getByContratoId(
        @PathVariable Integer contratoId
    ) {
        List<BancoHoras> historico = bancoHorasService.findByContratoId(
            contratoId
        );
        return ResponseEntity.ok(historico);
    }
}
