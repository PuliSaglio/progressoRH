package com.pulisaglio.progressoRH.service;

import com.pulisaglio.progressoRH.model.Skill;
import com.pulisaglio.progressoRH.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public void save(Skill skill) {
        if (skill.getNome() == null || skill.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Skill nome must not be empty");
        }
        skillRepository.save(skill);
    }

    public Optional<Skill> findById(String nome) {
        return skillRepository.findById(nome);
    }

    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    public void update(Skill skill, String nomeAntigo) {
        if (skill.getNome() == null || skill.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Skill nome must not be empty");
        }
        skillRepository.update(skill, nomeAntigo);
    }

    public void deleteById(String nome) {
        skillRepository.deleteById(nome);
    }
}
