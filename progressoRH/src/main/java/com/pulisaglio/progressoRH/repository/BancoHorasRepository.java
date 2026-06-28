package com.pulisaglio.progressoRH.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BancoHorasRepository {

    private final JdbcTemplate jdbcTemplate;

    public BancoHorasRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Verifica se já existe um registro consolidado de banco de horas
     * para o contrato informado no mês/ano da data de referência.
     *
     * @param contratoId id do contrato
     * @param year       ano da data de referência
     * @param month      mês da data de referência
     * @return true se o período já está fechado
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
