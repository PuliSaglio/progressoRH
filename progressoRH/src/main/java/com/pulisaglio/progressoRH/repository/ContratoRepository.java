package com.pulisaglio.progressoRH.repository;

import com.pulisaglio.progressoRH.model.Contrato;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ContratoRepository {

    private final JdbcTemplate jdbcTemplate;

    public ContratoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Contrato> ROW_MAPPER = new RowMapper<>() {
        @Override
        public Contrato mapRow(ResultSet rs, int rowNum) throws SQLException {
            Contrato c = new Contrato();
            c.setIdContrato(rs.getInt("id_contrato"));
            c.setDataInicio(rs.getDate("data_inicio").toLocalDate());
            Date dataFim = rs.getDate("data_fim");
            c.setDataFim(dataFim != null ? dataFim.toLocalDate() : null);
            c.setSalarioContratual(rs.getBigDecimal("salario_contratual"));
            c.setCargaHorariaSemanal(rs.getInt("carga_horaria_semanal"));
            c.setFuncionarioCpf(rs.getString("funcionario_cpf"));
            c.setCargoId(rs.getInt("cargo_id"));
            return c;
        }
    };

    public void save(Contrato contrato) {
        final String sql = "INSERT INTO contratos (data_inicio, data_fim, salario_contratual, carga_horaria_semanal, funcionario_cpf, cargo_id) " +
                           "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                Date.valueOf(contrato.getDataInicio()),
                contrato.getDataFim() != null ? Date.valueOf(contrato.getDataFim()) : null,
                contrato.getSalarioContratual(),
                contrato.getCargaHorariaSemanal(),
                contrato.getFuncionarioCpf(),
                contrato.getCargoId());
    }

    public Optional<Contrato> findById(Integer id) {
        final String sql = "SELECT id_contrato, data_inicio, data_fim, salario_contratual, carga_horaria_semanal, funcionario_cpf, cargo_id " +
                           "FROM contratos WHERE id_contrato = ?";
        List<Contrato> results = jdbcTemplate.query(sql, ROW_MAPPER, id);
        if (results.isEmpty()) return Optional.empty();
        return Optional.of(results.get(0));
    }

    public List<Contrato> findAll() {
        final String sql = "SELECT id_contrato, data_inicio, data_fim, salario_contratual, carga_horaria_semanal, funcionario_cpf, cargo_id " +
                           "FROM contratos";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public List<Contrato> findByFuncionarioCpf(String cpf) {
        final String sql = "SELECT id_contrato, data_inicio, data_fim, salario_contratual, carga_horaria_semanal, funcionario_cpf, cargo_id " +
                           "FROM contratos WHERE funcionario_cpf = ?";
        return jdbcTemplate.query(sql, ROW_MAPPER, cpf);
    }

    public void update(Contrato contrato) {
        final String sql = "UPDATE contratos SET data_inicio = ?, data_fim = ?, salario_contratual = ?, " +
                           "carga_horaria_semanal = ?, funcionario_cpf = ?, cargo_id = ? WHERE id_contrato = ?";
        jdbcTemplate.update(sql,
                Date.valueOf(contrato.getDataInicio()),
                contrato.getDataFim() != null ? Date.valueOf(contrato.getDataFim()) : null,
                contrato.getSalarioContratual(),
                contrato.getCargaHorariaSemanal(),
                contrato.getFuncionarioCpf(),
                contrato.getCargoId(),
                contrato.getIdContrato());
    }

    public void deleteById(Integer id) {
        final String sql = "DELETE FROM contratos WHERE id_contrato = ?";
        jdbcTemplate.update(sql, id);
    }
}
