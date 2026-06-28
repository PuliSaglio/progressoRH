package com.pulisaglio.progressoRH.repository;

import com.pulisaglio.progressoRH.model.Ponto;
import com.pulisaglio.progressoRH.model.enums.TipoPonto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PontoRepository {

    private final JdbcTemplate jdbcTemplate;

    public PontoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Ponto> ROW_MAPPER = new RowMapper<>() {
        @Override
        public Ponto mapRow(ResultSet rs, int rowNum) throws SQLException {
            Ponto p = new Ponto();
            p.setIdPonto(rs.getInt("id_ponto"));
            p.setTipo(TipoPonto.valueOf(rs.getString("tipo").toUpperCase()));
            p.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
            p.setContratoId(rs.getInt("contrato_id"));
            return p;
        }
    };

    public void save(Ponto ponto) {
        final String sql =
            "INSERT INTO pontos (tipo, data_hora, contrato_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(
            sql,
            ponto.getTipo().name(),
            Timestamp.valueOf(ponto.getDataHora()),
            ponto.getContratoId()
        );
    }

    public Optional<Ponto> findById(Integer id) {
        final String sql =
            "SELECT id_ponto, tipo, data_hora, contrato_id FROM pontos WHERE id_ponto = ?";
        List<Ponto> results = jdbcTemplate.query(sql, ROW_MAPPER, id);
        if (results.isEmpty()) return Optional.empty();
        return Optional.of(results.get(0));
    }

    public List<Ponto> findAll() {
        final String sql =
            "SELECT id_ponto, tipo, data_hora, contrato_id FROM pontos";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public List<Ponto> findByContratoId(Integer contratoId) {
        final String sql =
            "SELECT id_ponto, tipo, data_hora, contrato_id FROM pontos WHERE contrato_id = ?";
        return jdbcTemplate.query(sql, ROW_MAPPER, contratoId);
    }

    public void update(Ponto ponto) {
        final String sql =
            "UPDATE pontos SET tipo = ?, data_hora = ?, contrato_id = ? WHERE id_ponto = ?";
        jdbcTemplate.update(
            sql,
            ponto.getTipo().name(),
            Timestamp.valueOf(ponto.getDataHora()),
            ponto.getContratoId(),
            ponto.getIdPonto()
        );
    }

    public List<Ponto> findByDataHoraBetween(
        LocalDateTime dataInicio,
        LocalDateTime dataFim
    ) {
        final String sql =
            "SELECT id_ponto, tipo, data_hora, contrato_id FROM pontos " +
            "WHERE data_hora >= ? AND data_hora <= ? ORDER BY data_hora ASC";
        return jdbcTemplate.query(
            sql,
            ROW_MAPPER,
            Timestamp.valueOf(dataInicio),
            Timestamp.valueOf(dataFim)
        );
    }

    public void deleteById(Integer id) {
        final String sql = "DELETE FROM pontos WHERE id_ponto = ?";
        jdbcTemplate.update(sql, id);
    }
}
