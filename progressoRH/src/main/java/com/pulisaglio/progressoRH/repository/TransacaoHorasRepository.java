package com.pulisaglio.progressoRH.repository;

import com.pulisaglio.progressoRH.model.TransacaoHoras;
import com.pulisaglio.progressoRH.model.enums.TransacaoStatus;
import com.pulisaglio.progressoRH.model.enums.TransacaoTipo;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class TransacaoHorasRepository {

    private final JdbcTemplate jdbcTemplate;

    public TransacaoHorasRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<TransacaoHoras> ROW_MAPPER =
        new RowMapper<>() {
            @Override
            public TransacaoHoras mapRow(ResultSet rs, int rowNum)
                throws SQLException {
                TransacaoHoras t = new TransacaoHoras();
                t.setIdTransacao(rs.getLong("id_transacao"));
                t.setDataReferencia(
                    rs.getDate("data_referencia").toLocalDate()
                );
                t.setQuantidadeHoras(rs.getBigDecimal("quantidade_horas"));
                t.setJustificativa(rs.getString("justificativa"));
                t.setStatus(TransacaoStatus.valueOf(rs.getString("status")));
                t.setComprovante(rs.getString("comprovante"));
                t.setTipo(TransacaoTipo.valueOf(rs.getString("tipo")));
                t.setContratoId(rs.getInt("contrato_id"));
                return t;
            }
        };

    public void save(TransacaoHoras transacao) {
        final String sql =
            "INSERT INTO transacoes_horas " +
            "(data_referencia, quantidade_horas, justificativa, status, comprovante, tipo, contrato_id) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
            sql,
            Date.valueOf(transacao.getDataReferencia()),
            transacao.getQuantidadeHoras(),
            transacao.getJustificativa(),
            transacao.getStatus().name(),
            transacao.getComprovante(),
            transacao.getTipo().name(),
            transacao.getContratoId()
        );
    }

    public Optional<TransacaoHoras> findById(Long id) {
        final String sql =
            "SELECT id_transacao, data_referencia, quantidade_horas, justificativa, " +
            "status, comprovante, tipo, contrato_id " +
            "FROM transacoes_horas WHERE id_transacao = ?";
        List<TransacaoHoras> results = jdbcTemplate.query(sql, ROW_MAPPER, id);
        if (results.isEmpty()) return Optional.empty();
        return Optional.of(results.get(0));
    }

    public List<TransacaoHoras> findAll() {
        final String sql =
            "SELECT id_transacao, data_referencia, quantidade_horas, justificativa, " +
            "status, comprovante, tipo, contrato_id " +
            "FROM transacoes_horas";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public List<TransacaoHoras> findByContratoId(Integer contratoId) {
        final String sql =
            "SELECT id_transacao, data_referencia, quantidade_horas, justificativa, " +
            "status, comprovante, tipo, contrato_id " +
            "FROM transacoes_horas WHERE contrato_id = ?";
        return jdbcTemplate.query(sql, ROW_MAPPER, contratoId);
    }

    public void update(TransacaoHoras transacao) {
        final String sql =
            "UPDATE transacoes_horas " +
            "SET data_referencia = ?, quantidade_horas = ?, justificativa = ?, " +
            "status = ?, comprovante = ?, tipo = ?, contrato_id = ? " +
            "WHERE id_transacao = ?";
        jdbcTemplate.update(
            sql,
            Date.valueOf(transacao.getDataReferencia()),
            transacao.getQuantidadeHoras(),
            transacao.getJustificativa(),
            transacao.getStatus().name(),
            transacao.getComprovante(),
            transacao.getTipo().name(),
            transacao.getContratoId(),
            transacao.getIdTransacao()
        );
    }

    public void deleteById(Long id) {
        final String sql =
            "DELETE FROM transacoes_horas WHERE id_transacao = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<TransacaoHoras> findByPeriodo(
        java.time.LocalDate dataInicial,
        java.time.LocalDate dataFinal
    ) {
        final String sql =
            "SELECT id_transacao, data_referencia, quantidade_horas, justificativa, " +
            "status, comprovante, tipo, contrato_id " +
            "FROM transacoes_horas " +
            "WHERE data_referencia >= ? AND data_referencia <= ? " +
            "ORDER BY data_referencia ASC";
        return jdbcTemplate.query(
            sql,
            ROW_MAPPER,
            Date.valueOf(dataInicial),
            Date.valueOf(dataFinal)
        );
    }
}
