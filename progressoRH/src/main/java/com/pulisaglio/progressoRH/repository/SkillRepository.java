package com.pulisaglio.progressoRH.repository;

import com.pulisaglio.progressoRH.model.Skill;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class SkillRepository {

    private final JdbcTemplate jdbcTemplate;

    public SkillRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Skill> ROW_MAPPER = new RowMapper<>() {
        @Override
        public Skill mapRow(ResultSet rs, int rowNum) throws SQLException {
            Skill s = new Skill();
            s.setNome(rs.getString("nome"));
            s.setDescricao(rs.getString("descricao"));
            return s;
        }
    };

    public void save(Skill skill) {
        final String sql = "INSERT INTO skills (nome, descricao) VALUES (?, ?)";
        jdbcTemplate.update(sql, skill.getNome(), skill.getDescricao());
    }

    public Optional<Skill> findById(String nome) {
        final String sql = "SELECT nome, descricao FROM skills WHERE nome = ?";
        List<Skill> results = jdbcTemplate.query(sql, ROW_MAPPER, nome);
        if (results.isEmpty()) return Optional.empty();
        return Optional.of(results.get(0));
    }

    public List<Skill> findAll() {
        final String sql = "SELECT nome, descricao FROM skills";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public void update(Skill skill, String nomeAntigo) {
        final String sql = "UPDATE skills SET nome = ?, descricao = ? WHERE nome = ?";
        jdbcTemplate.update(sql, skill.getNome(), skill.getDescricao(), nomeAntigo);
    }

    public void deleteById(String nome) {
        final String sql = "DELETE FROM skills WHERE nome = ?";
        jdbcTemplate.update(sql, nome);
    }
}
