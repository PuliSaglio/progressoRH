package com.pulisaglio.progressoRH.repository;

import com.pulisaglio.progressoRH.model.Telefone;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class TelefoneRepository {

    private final JdbcTemplate jdbcTemplate;

    public TelefoneRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Telefone> TELEFONE_ROW_MAPPER = new RowMapper<Telefone>() {
        @Override
        public Telefone mapRow(ResultSet rs, int rowNum) throws SQLException {
            Telefone telefone = new Telefone();
            telefone.setNumero(rs.getString("numero"));
            telefone.setFuncionarioCpf(rs.getString("funcionario_cpf"));
            return telefone;
        }
    };

    public void save(Telefone telefone) {
        String sql = "INSERT INTO telefones (numero, funcionario_cpf) VALUES (?, ?)";
        jdbcTemplate.update(sql,
            telefone.getNumero(),
            telefone.getFuncionarioCpf()
        );
    }

    public Optional<Telefone> findById(String numero, String funcionarioCpf) {
        String sql = "SELECT * FROM telefones WHERE numero = ? AND funcionario_cpf = ?";
        try {
            Telefone telefone = jdbcTemplate.queryForObject(sql, TELEFONE_ROW_MAPPER, numero, funcionarioCpf);
            return Optional.of(telefone);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Telefone> findByFuncionarioCpf(String funcionarioCpf) {
        String sql = "SELECT * FROM telefones WHERE funcionario_cpf = ?";
        return jdbcTemplate.query(sql, TELEFONE_ROW_MAPPER, funcionarioCpf);
    }

    public List<Telefone> findAll() {
        String sql = "SELECT * FROM telefones";
        return jdbcTemplate.query(sql, TELEFONE_ROW_MAPPER);
    }

    public void update(Telefone telefone, String numeroAntigo) {
        String sql = "UPDATE telefones SET numero = ? WHERE numero = ? AND funcionario_cpf = ?";
        jdbcTemplate.update(sql,
            telefone.getNumero(),
            numeroAntigo,
            telefone.getFuncionarioCpf()
        );
    }

    public void deleteById(String numero, String funcionarioCpf) {
        String sql = "DELETE FROM telefones WHERE numero = ? AND funcionario_cpf = ?";
        jdbcTemplate.update(sql, numero, funcionarioCpf);
    }

    public void deleteByFuncionarioCpf(String funcionarioCpf) {
        String sql = "DELETE FROM telefones WHERE funcionario_cpf = ?";
        jdbcTemplate.update(sql, funcionarioCpf);
    }
}
