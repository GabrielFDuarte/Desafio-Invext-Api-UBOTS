package com.desafio.ubots.msdistribuicao.model;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ResponseDTO {
    private String message;
    private String teamName;
    private String attendantName;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int requestPosition;

    public ResponseDTO() {}

    public ResponseDTO(String message, String teamName, String attendantName, int requestPosition) {
        this.message = message;
        this.teamName = teamName;
        this.attendantName = attendantName;
        this.requestPosition = requestPosition;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getRequestPosition() {
        return requestPosition;
    }

    public void setRequestPosition(int requestPosition) {
        this.requestPosition = requestPosition;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAttendantName() {
        return attendantName;
    }

    public void setAttendantName(String attendantName) {
        this.attendantName = attendantName;
    }
}
