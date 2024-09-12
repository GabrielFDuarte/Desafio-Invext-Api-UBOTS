package com.desafio.ubots.msdistribuicao.model;

import com.desafio.ubots.msdistribuicao.service.TeamService;

import java.util.List;

public class Team {
    private String name;
    private TeamService teamService;

    public Team(List<Attendant> attendants) {
        this.teamService = new TeamService(attendants);
    }

    public Team(String name, List<Attendant> attendants) {
        this.name = name;
        this.teamService = new TeamService(attendants);
    }

    public ResponseDTO assignRequest(Request request, ResponseDTO responseDTO) {
        responseDTO.setTeamName(name);
        return teamService.assignRequest(request, responseDTO);
    }

    public String getRequestSummary() {
        return "Time " + name + ": " + teamService.getRequestSummary();
    }

    public boolean removeRequestFromAttendant(String attendantName) {
        return teamService.removeRequestFromAttendant(attendantName);
    }

    public String getName() {
        return name;
    }
}
