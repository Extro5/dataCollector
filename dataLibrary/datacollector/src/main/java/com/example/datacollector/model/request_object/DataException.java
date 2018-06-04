package com.example.datacollector.model.request_object;

import com.google.gson.annotations.SerializedName;

public class DataException {

    @SerializedName("exceptionName")
    private String exceptionName;

    @SerializedName("exceptionMessage")
    private String exceptionMessage;

    @SerializedName("idUser")
    private String idUser;

    public DataException(String exceptionName, String exceptionMessage, String idUser) {
        this.exceptionName = exceptionName;
        this.exceptionMessage = exceptionMessage;
        this.idUser = idUser;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}