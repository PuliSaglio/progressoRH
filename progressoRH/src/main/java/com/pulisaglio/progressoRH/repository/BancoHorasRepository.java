package com.pulisaglio.progressoRH.repository;

import com.pulisaglio.progressoRH.model.BancoHoras;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class BancoHorasRepository {

    private final JdbcTemplate jdbcTemplate;

    public BancoHorasRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<BancoHoras> ROW_MAPPER = new RowMapper<>() {
        @Override
        public BancoHoras mapRow(ResultSet rs, int rowNum) throws SQLException {
            BancoHoras b = new BancoHoras();
            b.setIdBancoHoras(rs.getInt("id_banco_horas"));
            b.setDataRegistro(rs.getDate("data_registro").toLocalDate());
            b.setSaldoAnterior(rs.getBigDecimal("saldo_anterior"));
            b.setSaldoAtual(rs.getBigDecimal("saldo_atual"));
            b.setHorasExtrasMes(rs.getBigDecimal("horas_extras_mes"));
            b.setContratoId(rs.getInt("contrato_id"));
            return b;
        }
    };

    public void save(BancoHoras bancoHoras) {
        final String sql =
            "INSERT INTO banco_horas " +
            "(data_registro, saldo_anterior, saldo_atual, horas_extras_mes, contrato_id) " +
            "VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
            );
            ps.setDate(1, Date.valueOf(bancoHoras.getDataRegistro()));
            ps.setBigDecimal(2, bancoHoras.getSaldoAnterior());
            ps.setBigDecimal(3, bancoHoras.getSaldoAtual());
            if (bancoHoras.getHorasExtrasMes() != null) {
                ps.setBigDecimal(4, bancoHoras.getHorasExtrasMes());
            } else {
                ps.setNull(4, Types.DECIMAL);
            }
            ps.setInt(5, bancoHoras.getContratoId());
            return ps;
        }, keyHolder);
        bancoHoras.setIdBancoHoras(
            Objects.requireNonNull(keyHolder.getKey()).intValue()
        );
    }

    /**
     * Retorna o registro de banco de horas mais recente do contrato
     * Utilizado para capturar o saldo_atual que se tornará o saldo_anterior do novo mês.
     */
    public Optional<BancoHoras> findLatestByContratoId(Integer contratoId) {
        final String sql =
            "SELECT id_banco_horas, data_registro, saldo_anterior, saldo_atual, " +
            "horas_extras_mes, contrato_id " +
            "FROM banco_horas WHERE contrato_id = ? " +
            "ORDER BY data_registro DESC LIMIT 1";
        List<BancoHoras> results = jdbcTemplate.query(
            sql,
            ROW_MAPPER,
            contratoId
        );
        if (results.isEmpty()) return Optional.empty();
        return Optional.of(results.get(0));
    }

    /**
     * Retorna o histórico completo de consolidações de um contrato,
     * ordenado cronologicamente.
     */
    public List<BancoHoras> findByContratoId(Integer contratoId) {
        final String sql =
            "SELECT id_banco_horas, data_registro, saldo_anterior, saldo_atual, " +
            "horas_extras_mes, contrato_id " +
            "FROM banco_horas WHERE contrato_id = ? " +
            "ORDER BY data_registro ASC";
        return jdbcTemplate.query(sql, ROW_MAPPER, contratoId);
    }

    /**
     * Verifica se já existe um registro consolidado de banco de horas
     * para o contrato informado no mês/ano especificados.
     * Utilizado como barreira de duplicação no motor de consolidação.
     */
    public boolean existsConsolidadoForContratoAndMonth(
        Integer contratoId,
        int year,
        int month
    ) {
        final String sql =
            "SELECT COUNT(*) FROM banco_horas " +
            "WHERE contrato_id = ? AND YEAR(data_registro) = ? AND MONTH(data_registro) = ?";
        Integer count = jdbcTemplate.queryForObject(
            sql,
            Integer.class,
            contratoId,
            year,
            month
        );
        return count != null && count > 0;
    }
}
