package com.desafio.ubots.msdistribuicao.service;

import com.desafio.ubots.msdistribuicao.model.Attendant;
import com.desafio.ubots.msdistribuicao.model.Request;
import com.desafio.ubots.msdistribuicao.model.ResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeamServiceTest {

    private TeamService teamService;

    @BeforeEach
    void setUp() {
        Attendant attendant1 = new Attendant("André");
        Attendant attendant2 = new Attendant("Bianca");
        teamService = new TeamService(Arrays.asList(attendant1, attendant2));
    }

    @Test
    void testAssignRequestToAvailableAttendant() {
        Request request = new Request("Problemas com cartão");
        ResponseDTO responseDTO = new ResponseDTO();

        ResponseDTO result = teamService.assignRequest(request, responseDTO);

        assertNotNull(result.getAttendantName());
        assertEquals(0, teamService.getPendingRequests().size());
    }

    @Test
    void testAssignRequestToPendingQueueWhenFull() {
        Request request1 = new Request("Solicitação 1");
        Request request2 = new Request("Solicitação 2");
        Request request3 = new Request("Solicitação 3");
        Request request4 = new Request("Solicitação 4");
        Request request5 = new Request("Solicitação 5");
        Request request6 = new Request("Solicitação 6");
        Request request7 = new Request("Solicitação 7");

        teamService.assignRequest(request1, new ResponseDTO());
        teamService.assignRequest(request2, new ResponseDTO());
        teamService.assignRequest(request3, new ResponseDTO());
        teamService.assignRequest(request4, new ResponseDTO());
        teamService.assignRequest(request5, new ResponseDTO());
        teamService.assignRequest(request6, new ResponseDTO());
        ResponseDTO responseDTO = teamService.assignRequest(request7, new ResponseDTO());

        assertEquals(1, teamService.getPendingRequests().size());
        assertNull(responseDTO.getAttendantName());
    }

    @Test
    void testRemoveRequestFromAttendant() {
        Request request = new Request("Problemas com cartão");
        teamService.assignRequest(request, new ResponseDTO());

        boolean removed = teamService.removeRequestFromAttendant("André");
        assertTrue(removed);
    }

    @Test
    void testRemoveRequestFromAttendantWithPendingRequests() {
        Request request1 = new Request("Solicitação 1");
        Request request2 = new Request("Solicitação 2");
        Request request3 = new Request("Solicitação 3");
        Request request4 = new Request("Solicitação 4");
        Request request5 = new Request("Solicitação 5");
        Request request6 = new Request("Solicitação 6");
        Request request7 = new Request("Solicitação 7");

        teamService.assignRequest(request1, new ResponseDTO());
        teamService.assignRequest(request2, new ResponseDTO());
        teamService.assignRequest(request3, new ResponseDTO());
        teamService.assignRequest(request4, new ResponseDTO());
        teamService.assignRequest(request5, new ResponseDTO());
        teamService.assignRequest(request6, new ResponseDTO());
        teamService.assignRequest(request7, new ResponseDTO());

        List<Request> requestsBefore = new LinkedList<>(teamService.getPendingRequests());
        assertFalse(requestsBefore.isEmpty());

        boolean removed = teamService.removeRequestFromAttendant("André");
        assertTrue(removed);

        List<Request> requestsAfter = new LinkedList<>(teamService.getPendingRequests());
        assertTrue(requestsAfter.isEmpty());
    }

    @Test
    void testGetRequestSummaryWhenNoRequests() {
        String summary = teamService.getRequestSummary();

        String expectedSummary = "André - 0 solicitações / Bianca - 0 solicitações / Solicitações pendentes - 0";
        assertEquals(expectedSummary, summary);
    }

    @Test
    void testGetRequestSummaryWithRequestsAndPending() {
        Request request1 = new Request("Solicitação 1");
        Request request2 = new Request("Solicitação 2");
        Request request3 = new Request("Solicitação 3");
        Request request4 = new Request("Solicitação 4");
        Request request5 = new Request("Solicitação 5");
        Request request6 = new Request("Solicitação 6");
        Request request7 = new Request("Solicitação 7");

        teamService.assignRequest(request1, new ResponseDTO());
        teamService.assignRequest(request2, new ResponseDTO());
        teamService.assignRequest(request3, new ResponseDTO());
        teamService.assignRequest(request4, new ResponseDTO());
        teamService.assignRequest(request5, new ResponseDTO());
        teamService.assignRequest(request6, new ResponseDTO());
        teamService.assignRequest(request7, new ResponseDTO());

        String summary = teamService.getRequestSummary();

        String expectedSummary = "André - 3 solicitações / Bianca - 3 solicitações / Solicitações pendentes - 1";
        assertEquals(expectedSummary, summary);
    }
}
