package org.extro5.server.dao;

import org.extro5.server.entities.ActivityNameDiagram;
import org.extro5.server.entities.DeviceInfo;
import org.extro5.server.entities.User;

import java.util.List;

public interface DiagramRepository {

    List<User> getAllUsersConsideringProfessions();

    List<User> getAllUsersConsideringGender();

    List<User> getAllUsersConsideringAge();

    List<User> getAllUsersConsideringHobby();

    List<DeviceInfo> getAllDeviceConsideringManufacturer();

    List<DeviceInfo> getAllDeviceConsideringModel();

    List<DeviceInfo> getAllDeviceConsideringMarketName();

    List<ActivityNameDiagram> getAllUsersActivitySession();
}
