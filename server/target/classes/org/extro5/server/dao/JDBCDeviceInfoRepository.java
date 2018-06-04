package org.extro5.server.dao;

import org.extro5.server.entities.DataException;
import org.extro5.server.entities.DeviceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JDBCDeviceInfoRepository implements DeviceInfoRepository {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void addDeviceInfo(DeviceInfo deviceInfo) {
        this.jdbcTemplate.update("INSERT INTO device_info(id_user, manufacturer, marketName, model) VALUE (?,?,?,?)", deviceInfo.getUserId(), deviceInfo.getManufacturer(), deviceInfo.getMarketName(), deviceInfo.getModel());
    }
}