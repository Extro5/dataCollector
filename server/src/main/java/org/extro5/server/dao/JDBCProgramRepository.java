package org.extro5.server.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JDBCProgramRepository implements ProgramRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public String getProgramKey(String idProgram) {
        String sql = "SELECT id_program FROM program_users WHERE id_program = ?";
        List<String> programIds = jdbcTemplate.queryForList(sql, new Object[]{idProgram}, String.class);
        return String.valueOf(programIds.size());
    }
}