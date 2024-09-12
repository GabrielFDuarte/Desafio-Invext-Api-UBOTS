package com.desafio.ubots.msdistribuicao.model;

import com.desafio.ubots.msdistribuicao.service.AttendantService;

import java.util.Queue;

public class Attendant {
    private String name;
    private AttendantService attendantService;

    public Attendant() {
        this.attendantService = new AttendantService();
    }

    public Attendant(String name) {
        this.name = name;
        this.attendantService = new AttendantService();
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return attendantService.isAvailable();
    }

    public void addRequest(Request request) {
        attendantService.addRequest(request);
    }

    public void removeRequest() {
        attendantService.removeRequest();
    }

    public Queue<Request> getRequestsList() {
        return attendantService.getActiveRequests();
    }
}
