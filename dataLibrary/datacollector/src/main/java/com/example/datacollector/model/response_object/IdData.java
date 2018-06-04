package com.example.datacollector.model.response_object;

import com.google.gson.annotations.SerializedName;

public class IdData {

    @SerializedName("idProgram")
    private String idProgram;

    @SerializedName("idUser")
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
