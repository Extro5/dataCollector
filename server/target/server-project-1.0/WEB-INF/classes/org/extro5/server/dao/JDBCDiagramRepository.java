package org.extro5.server.dao;

import org.extro5.server.entities.ActivityNameDiagram;
import org.extro5.server.entities.DeviceInfo;
import org.extro5.server.entities.SystemInformation;
import org.extro5.server.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Repository
public class JDBCDiagramRepository implements DiagramRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public List<User> getAllUsersConsideringProfessions() {
        String sql = "SELECT * FROM user";


        List<User> userList = jdbcTemplate.query(sql, (rs, rowNum) -> {

            User user = new User();
            user.setProfession(rs.getString("profession"));
            return user;
        });
        System.out.println(userList.size());
        return userList;
    }

    @Override
    public List<User> getAllUsersConsideringGender() {
        String sql = "SELECT * FROM user";


        List<User> userList = jdbcTemplate.query(sql, (rs, rowNum) -> {

            User user = new User();
            user.setGender(rs.getString("gender"));
            user.setUserId(String.valueOf(rs.getInt("id_user")));
            return user;
        });
        System.out.println(userList.size());
        return userList;
    }

    @Override
    public List<User> getAllUsersConsideringAge() {
        String sql = "SELECT * FROM user";


        List<User> userList = jdbcTemplate.query(sql, (rs, rowNum) -> {

            User user = new User();
            user.setAge(rs.getString("age"));
            return user;
        });
        System.out.println(userList.size());
        return userList;
    }

    @Override
    public List<User> getAllUsersConsideringHobby() {
        String sql = "SELECT * FROM user";


        List<User> userList = jdbcTemplate.query(sql, (rs, rowNum) -> {

            User user = new User();
            user.setHobby(rs.getString("hobby"));
            return user;
        });
        System.out.println(userList.size());
        return userList;
    }

    @Override
    public List<DeviceInfo> getAllDeviceConsideringManufacturer() {
        String sql = "SELECT * FROM device_info";


        List<DeviceInfo> deviceInfosList = jdbcTemplate.query(sql, (rs, rowNum) -> {

            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setManufacturer(rs.getString("manufacturer"));
            return deviceInfo;
        });
        System.out.println(deviceInfosList.size());
        return deviceInfosList;
    }

    @Override
    public List<DeviceInfo> getAllDeviceConsideringModel() {
        String sql = "SELECT * FROM device_info";


        List<DeviceInfo> deviceInfosList = jdbcTemplate.query(sql, (rs, rowNum) -> {

            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setModel(rs.getString("model"));
            return deviceInfo;
        });
        System.out.println(deviceInfosList.size());
        return deviceInfosList;
    }

    @Override
    public List<DeviceInfo> getAllDeviceConsideringMarketName() {
        String sql = "SELECT * FROM device_info";


        List<DeviceInfo> deviceInfosList = jdbcTemplate.query(sql, (rs, rowNum) -> {

            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setMarketName(rs.getString("marketName"));
            return deviceInfo;
        });
        System.out.println(deviceInfosList.size());
        return deviceInfosList;
    }

    //сколько пользователей на каких экранах сидит - общие данные
    // сколько мужчин на экране сколько женщин
    @Override
    public List<ActivityNameDiagram> getAllUsersActivitySession() {
        String sql = "SELECT * FROM activity";

        //id_user + activity_name
        List<SystemInformation> systemInformations = jdbcTemplate.query(sql, (rs, rowNum) -> {
            SystemInformation systemInformation = new SystemInformation();
            systemInformation.setUserId(String.valueOf(rs.getInt("id_user")));
            systemInformation.setActivityName(rs.getString("activity_name"));
            return systemInformation;
        });

        List<User> allSystemUsers = getAllUsersConsideringGender();
        List<User> newUserList = new ArrayList<>();


        List<ActivityNameDiagram> activityNameDiagrams = new ArrayList<>();

        for (SystemInformation systemInformation : systemInformations) {
            newUserList.add(new User(systemInformation.getUserId(), systemInformation.getActivityName()));
        }


        for (User user : newUserList) {
            if (allSystemUsers.contains(user)) {
                activityNameDiagrams.add(new ActivityNameDiagram(allSystemUsers.get(allSystemUsers.indexOf(user)).getGender(), user.getActivityName()));
            }
        }
        return activityNameDiagrams;
    }
}