package com.pulisaglio.progressoRH.repository;

import com.pulisaglio.progressoRH.model.Funcionario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class FuncionarioRepository {

    private final JdbcTemplate jdbcTemplate;

    public FuncionarioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Funcionario> FUNCIONARIO_ROW_MAPPER = new RowMapper<Funcionario>() {
        @Override
        public Funcionario mapRow(ResultSet rs, int rowNum) throws SQLException {
            Funcionario funcionario = new Funcionario();
            funcionario.setCpf(rs.getString("cpf"));
            funcionario.setNome(rs.getString("nome"));
            funcionario.setSobrenome(rs.getString("sobrenome"));
            funcionario.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
            funcionario.setEmail(rs.getString("email"));
            funcionario.setLogradouro(rs.getString("logradouro"));
            funcionario.setNumero(rs.getString("numero"));
            funcionario.setCep(rs.getString("cep"));
            funcionario.setBairro(rs.getString("bairro"));
            return funcionario;
        }
    };

    public void save(Funcionario funcionario) {
        String sql = "INSERT INTO funcionarios (cpf, nome, sobrenome, data_nascimento, email, logradouro, numero, cep, bairro) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(sql,
            funcionario.getCpf(),
            funcionario.getNome(),
            funcionario.getSobrenome(),
            Date.valueOf(funcionario.getDataNascimento()),
            funcionario.getEmail(),
            funcionario.getLogradouro(),
            funcionario.getNumero(),
            funcionario.getCep(),
            funcionario.getBairro()
        );
    }

    public Optional<Funcionario> findById(String cpf) {
        String sql = "SELECT * FROM funcionarios WHERE cpf = ?";
        try {
            Funcionario funcionario = jdbcTemplate.queryForObject(sql, FUNCIONARIO_ROW_MAPPER, cpf);
            return Optional.of(funcionario);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Funcionario> findAll() {
        String sql = "SELECT * FROM funcionarios";
        return jdbcTemplate.query(sql, FUNCIONARIO_ROW_MAPPER);
    }

    public void update(Funcionario funcionario) {
        String sql = "UPDATE funcionarios SET nome = ?, sobrenome = ?, data_nascimento = ?, email = ?, " +
                     "logradouro = ?, numero = ?, cep = ?, bairro = ? WHERE cpf = ?";
        
        jdbcTemplate.update(sql,
            funcionario.getNome(),
            funcionario.getSobrenome(),
            Date.valueOf(funcionario.getDataNascimento()),
            funcionario.getEmail(),
            funcionario.getLogradouro(),
            funcionario.getNumero(),
            funcionario.getCep(),
            funcionario.getBairro(),
            funcionario.getCpf()
        );
    }

    public void deleteById(String cpf) {
        String sql = "DELETE FROM funcionarios WHERE cpf = ?";
        jdbcTemplate.update(sql, cpf);
    }
}
