package com.pulisaglio.progressoRH.repository;

import com.pulisaglio.progressoRH.model.Beneficio;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class BeneficioRepository {

    private final JdbcTemplate jdbcTemplate;

    public BeneficioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Beneficio> ROW_MAPPER = new RowMapper<>() {
        @Override
        public Beneficio mapRow(ResultSet rs, int rowNum) throws SQLException {
            Beneficio b = new Beneficio();
            b.setIdBeneficio(rs.getInt("id_beneficio"));
            b.setNome(rs.getString("nome"));
            b.setTipo(rs.getString("tipo"));
            BigDecimal valor = rs.getBigDecimal("valor");
            b.setValor(valor);
            b.setDescricao(rs.getString("descricao"));
            return b;
        }
    };

    public void save(Beneficio beneficio) {
        final String sql = "INSERT INTO beneficios (nome, tipo, valor, descricao) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                beneficio.getNome(),
                beneficio.getTipo(),
                beneficio.getValor(),
                beneficio.getDescricao());
    }

    public Optional<Beneficio> findById(Integer id) {
        final String sql = "SELECT id_beneficio, nome, tipo, valor, descricao FROM beneficios WHERE id_beneficio = ?";
        List<Beneficio> results = jdbcTemplate.query(sql, ROW_MAPPER, id);
        if (results.isEmpty()) return Optional.empty();
        return Optional.of(results.get(0));
    }

    public List<Beneficio> findAll() {
        final String sql = "SELECT id_beneficio, nome, tipo, valor, descricao FROM beneficios";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public void update(Beneficio beneficio) {
        final String sql = "UPDATE beneficios SET nome = ?, tipo = ?, valor = ?, descricao = ? WHERE id_beneficio = ?";
        jdbcTemplate.update(sql,
                beneficio.getNome(),
                beneficio.getTipo(),
                beneficio.getValor(),
                beneficio.getDescricao(),
                beneficio.getIdBeneficio());
    }

    public void deleteById(Integer id) {
        final String sql = "DELETE FROM beneficios WHERE id_beneficio = ?";
        jdbcTemplate.update(sql, id);
    }
}
