package com.desafio.ubots.msdistribuicao.service;

import com.desafio.ubots.msdistribuicao.model.Attendant;
import com.desafio.ubots.msdistribuicao.model.Request;
import com.desafio.ubots.msdistribuicao.model.ResponseDTO;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TeamService {
    private List<Attendant> attendants;
    private Queue<Request> pendingRequests;
    private int currentAttendantIndex = 0;

    public TeamService(List<Attendant> attendants) {
        this.attendants = attendants;
        this.pendingRequests = new LinkedList<>();
    }

    public ResponseDTO assignRequest(Request request, ResponseDTO responseDTO) {
        boolean assigned = false;
        int attemptedAssignments = 0;

        while (attemptedAssignments < attendants.size()) {
            Attendant currentAttendant = attendants.get(currentAttendantIndex);
            currentAttendantIndex = (currentAttendantIndex + 1) % attendants.size();
            attemptedAssignments++;

            if (currentAttendant.isAvailable()) {
                currentAttendant.addRequest(request);
                responseDTO.setAttendantName(currentAttendant.getName());
                assigned = true;
                break;
            }
        }

        if (!assigned) {
            pendingRequests.add(request);
            responseDTO.setRequestPosition(pendingRequests.size());
        }

        return responseDTO;
    }

    public Queue<Request> getPendingRequests() {
        return pendingRequests;
    }

    public String getRequestSummary() {
        StringBuilder summary = new StringBuilder();

        for (Attendant attendant : attendants) {
            summary.append(attendant.getName())
                    .append(" - ")
                    .append(attendant.getRequestsList().size())
                    .append(attendant.getRequestsList().size() == 1 ? " solicitação / " : " solicitações / ");
        }

        summary.append("Solicitações pendentes - ")
                .append(pendingRequests.size());

        return summary.toString();
    }

    public boolean removeRequestFromAttendant(String attendantName) {
        for (Attendant attendant : attendants) {
            if (attendant.getName().equals(attendantName)) {
                attendant.removeRequest();

                if (!pendingRequests.isEmpty()) {
                    attendant.addRequest(pendingRequests.poll());
                }

                return true;
            }
        }
        return false;
    }
}
