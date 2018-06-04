package org.extro5.server.entities;

public class IdData {

    private String idProgram;

    private String idUser;

    public IdData(String idProgram, String idUser) {
        this.idProgram = idProgram;
        this.idUser = idUser;
    }

    public String getIdProgram() {
        return idProgram;
    }

    public void setIdProgram(String idProgram) {
        this.idProgram = idProgram;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
