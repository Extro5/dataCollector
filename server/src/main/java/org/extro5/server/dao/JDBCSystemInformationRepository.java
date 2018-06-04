package org.extro5.server.dao;


import org.extro5.server.entities.SystemInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JDBCSystemInformationRepository implements SystemInformationRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public void addSystemInfo(SystemInformation systemInformation) {
        this.jdbcTemplate.update("INSERT INTO sessions (id_user, session_count, session_time) VALUES (?, ? , ?)", systemInformation.getUserId(), 1, systemInformation.getElapsedTime());
        this.jdbcTemplate.update("INSERT INTO activity (id_user, command_line, activity_name) VALUES (?, ?, ?)", systemInformation.getUserId(), systemInformation.getCommandLine(), systemInformation.getActivityName());
    }
}