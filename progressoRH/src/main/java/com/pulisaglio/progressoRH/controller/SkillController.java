package com.pulisaglio.progressoRH.controller;

import com.pulisaglio.progressoRH.model.Skill;
import com.pulisaglio.progressoRH.service.SkillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Skill skill) {
        try {
            skillService.save(skill);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Skill>> getAll() {
        List<Skill> skills = skillService.findAll();
        return ResponseEntity.ok(skills);
    }

    @GetMapping("/{nome}")
    public ResponseEntity<Skill> getById(@PathVariable String nome) {
        Optional<Skill> skill = skillService.findById(nome);
        return skill.map(s -> ResponseEntity.ok(s)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{nome}")
    public ResponseEntity<Void> update(@PathVariable String nome, @RequestBody Skill skill) {
        try {
            Optional<Skill> existing = skillService.findById(nome);
            if (existing.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            skillService.update(skill, nome);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{nome}")
    public ResponseEntity<Void> delete(@PathVariable String nome) {
        Optional<Skill> existing = skillService.findById(nome);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        skillService.deleteById(nome);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
