package org.extro5.server.dao;

import org.extro5.server.entities.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JDBCExceptionRepository implements ExceptionRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void addException(DataException dataException) {
        this.jdbcTemplate.update("INSERT INTO exception(id_user, exception_message, exception_name) VALUE (?,?,?)", dataException.getIdUser(), dataException.getExceptionMessage(), dataException.getExceptionName());
    }
}
