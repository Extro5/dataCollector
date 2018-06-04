package org.extro5.server.dao;


import org.extro5.server.entities.User;

import java.util.List;

public interface UserRepository {

    String adduser(User user) throws Exception;

    void addProgramUser(String idProgram, String idUser);
}