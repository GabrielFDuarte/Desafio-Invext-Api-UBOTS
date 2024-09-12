package com.desafio.ubots.msdistribuicao.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    private Team team;
    private Attendant attendant1;
    private Request request;

    @BeforeEach
    void setUp() {
        List<Attendant> attendants = new LinkedList<>();
        attendant1 = new Attendant("André");
        Attendant attendant2 = new Attendant("Bianca");
        attendants.add(attendant1);
        attendants.add(attendant2);
        team = new Team("Cartões", attendants);
    }

    @Test
    void testAssignRequestToAvailableAttendant() {
        request = new Request("Problemas com cartão");
        ResponseDTO responseDTO = new ResponseDTO();

        responseDTO = team.assignRequest(request, responseDTO);

        assertEquals(attendant1.getName(), responseDTO.getAttendantName());
        assertTrue(attendant1.getRequestsList().contains(request));
    }

    @Test
    void testGetRequestSummary() {
        request = new Request("Problemas com cartão");
        team.assignRequest(request, new ResponseDTO());

        String summary = team.getRequestSummary();
        assertTrue(summary.contains("André"));
        assertTrue(summary.contains("1 solicitação"));
    }

    @Test
    void testRemoveRequestFromAttendant() {
        request = new Request("Problemas com cartão");
        attendant1.addRequest(request);

        boolean result = team.removeRequestFromAttendant(attendant1.getName());

        assertTrue(result);
        assertFalse(attendant1.getRequestsList().contains(request));
    }

    @Test
    void testGetName() {
        assertEquals("Cartões", team.getName());
    }
}
