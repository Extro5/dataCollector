package org.extro5.server.dao;

import org.extro5.server.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;


@Repository
public class JDBCUserRepository implements UserRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public String adduser(User user) {

        String sql = "SELECT id_user FROM user WHERE first_name = ? AND last_name =? AND father_name = ?";
        List<String> listUser = jdbcTemplate.queryForList(sql, new Object[]{user.getFirstName(), user.getLastName(), user.getFatherName()}, String.class);

        if (listUser.size() == 0) {
            System.out.println("==null?");
            this.jdbcTemplate.update("INSERT INTO user(first_name, last_name, father_name, email, gender, phone, age, hobby, profession) VALUE (?,?,?,?,?,?,?,?,?)", user.getFirstName(), user.getLastName(), user.getFatherName(), user.getEmail(), user.getGender(), user.getPhone(), user.getAge(), user.getHobby(), user.getProfession());
            return jdbcTemplate.queryForList(sql, new Object[]{user.getFirstName(), user.getLastName(), user.getFatherName()}, String.class).get(0);
        }
        return "";
    }

    @Override
    public void addProgramUser(String idProgram, String idUser) {
        this.jdbcTemplate.update("INSERT INTO program_users(id_program, id_user) VALUE (?,?)", idProgram, idUser);
    }
}