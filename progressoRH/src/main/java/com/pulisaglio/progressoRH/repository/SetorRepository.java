package com.pulisaglio.progressoRH.repository;

import com.pulisaglio.progressoRH.model.Setor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class SetorRepository {

    private final JdbcTemplate jdbcTemplate;

    public SetorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Setor> ROW_MAPPER = new RowMapper<>() {
        @Override
        public Setor mapRow(ResultSet rs, int rowNum) throws SQLException {
            Setor s = new Setor();
            s.setIdSetor(rs.getInt("id_setor"));
            s.setNome(rs.getString("nome"));
            return s;
        }
    };

    public void save(Setor setor) {
        final String sql = "INSERT INTO setores (nome) VALUES (?)";
        jdbcTemplate.update(sql, setor.getNome());
    }

    public Optional<Setor> findById(Integer id) {
        final String sql = "SELECT id_setor, nome FROM setores WHERE id_setor = ?";
        List<Setor> results = jdbcTemplate.query(sql, ROW_MAPPER, id);
        if (results.isEmpty()) return Optional.empty();
        return Optional.of(results.get(0));
    }

    public List<Setor> findAll() {
        final String sql = "SELECT id_setor, nome FROM setores";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public void update(Setor setor) {
        final String sql = "UPDATE setores SET nome = ? WHERE id_setor = ?";
        jdbcTemplate.update(sql, setor.getNome(), setor.getIdSetor());
    }

    public void deleteById(Integer id) {
        final String sql = "DELETE FROM setores WHERE id_setor = ?";
        jdbcTemplate.update(sql, id);
    }
}
