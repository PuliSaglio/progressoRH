package com.pulisaglio.progressoRH.repository;

import com.pulisaglio.progressoRH.model.Cargo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class CargoRepository {

    private final JdbcTemplate jdbcTemplate;

    public CargoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Cargo> ROW_MAPPER = new RowMapper<>() {
        @Override
        public Cargo mapRow(ResultSet rs, int rowNum) throws SQLException {
            Cargo c = new Cargo();
            c.setIdCargo(rs.getInt("id_cargo"));
            c.setNome(rs.getString("nome"));
            c.setDescricao(rs.getString("descricao"));
            c.setLimiteHorasExtras(rs.getInt("limite_horas_extras"));
            c.setSetorId(rs.getInt("setor_id"));
            return c;
        }
    };

    public void save(Cargo cargo) {
        final String sql = "INSERT INTO cargos (nome, descricao, limite_horas_extras, setor_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                cargo.getNome(),
                cargo.getDescricao(),
                cargo.getLimiteHorasExtras(),
                cargo.getSetorId());
    }

    public Optional<Cargo> findById(Integer id) {
        final String sql = "SELECT id_cargo, nome, descricao, limite_horas_extras, setor_id FROM cargos WHERE id_cargo = ?";
        List<Cargo> results = jdbcTemplate.query(sql, ROW_MAPPER, id);
        if (results.isEmpty()) return Optional.empty();
        return Optional.of(results.get(0));
    }

    public List<Cargo> findAll() {
        final String sql = "SELECT id_cargo, nome, descricao, limite_horas_extras, setor_id FROM cargos";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public void update(Cargo cargo) {
        final String sql = "UPDATE cargos SET nome = ?, descricao = ?, limite_horas_extras = ?, setor_id = ? WHERE id_cargo = ?";
        jdbcTemplate.update(sql,
                cargo.getNome(),
                cargo.getDescricao(),
                cargo.getLimiteHorasExtras(),
                cargo.getSetorId(),
                cargo.getIdCargo());
    }

    public void deleteById(Integer id) {
        final String sql = "DELETE FROM cargos WHERE id_cargo = ?";
        jdbcTemplate.update(sql, id);
    }
}
