package com.pulisaglio.progressoRH.repository;

import com.pulisaglio.progressoRH.model.Desconto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class DescontoRepository {

    private final JdbcTemplate jdbcTemplate;

    public DescontoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Desconto> ROW_MAPPER = new RowMapper<>() {
        @Override
        public Desconto mapRow(ResultSet rs, int rowNum) throws SQLException {
            Desconto d = new Desconto();
            d.setIdDesconto(rs.getInt("id_desconto"));
            d.setNome(rs.getString("nome"));
            d.setTipo(rs.getString("tipo"));
            BigDecimal valor = rs.getBigDecimal("valor");
            d.setValor(valor);
            d.setDescricao(rs.getString("descricao"));
            return d;
        }
    };

    public void save(Desconto desconto) {
        final String sql = "INSERT INTO descontos (nome, tipo, valor, descricao) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                desconto.getNome(),
                desconto.getTipo(),
                desconto.getValor(),
                desconto.getDescricao());
    }

    public Optional<Desconto> findById(Integer id) {
        final String sql = "SELECT id_desconto, nome, tipo, valor, descricao FROM descontos WHERE id_desconto = ?";
        List<Desconto> results = jdbcTemplate.query(sql, ROW_MAPPER, id);
        if (results.isEmpty()) return Optional.empty();
        return Optional.of(results.get(0));
    }

    public List<Desconto> findAll() {
        final String sql = "SELECT id_desconto, nome, tipo, valor, descricao FROM descontos";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public void update(Desconto desconto) {
        final String sql = "UPDATE descontos SET nome = ?, tipo = ?, valor = ?, descricao = ? WHERE id_desconto = ?";
        jdbcTemplate.update(sql,
                desconto.getNome(),
                desconto.getTipo(),
                desconto.getValor(),
                desconto.getDescricao(),
                desconto.getIdDesconto());
    }

    public void deleteById(Integer id) {
        final String sql = "DELETE FROM descontos WHERE id_desconto = ?";
        jdbcTemplate.update(sql, id);
    }
}
