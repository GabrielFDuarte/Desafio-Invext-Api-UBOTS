package com.desafio.ubots.msdistribuicao.service;

import com.desafio.ubots.msdistribuicao.model.Request;

import java.util.LinkedList;
import java.util.Queue;

public class AttendantService {
    private Queue<Request> activeRequests;

    public AttendantService() {
        this.activeRequests = new LinkedList<>();
    }

    public boolean addRequest(Request request) {
        activeRequests.add(request);
        return true;
    }

    public boolean removeRequest() {
        if (!activeRequests.isEmpty()) {
            activeRequests.poll();
            return true;
        }
        return false;
    }

    public boolean isAvailable() {
        return activeRequests.size() < 3;
    }

    public Queue<Request> getActiveRequests() {
        return new LinkedList<>(activeRequests);
    }
}
