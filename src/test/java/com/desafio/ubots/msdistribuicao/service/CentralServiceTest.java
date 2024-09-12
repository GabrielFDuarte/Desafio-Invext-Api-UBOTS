package com.desafio.ubots.msdistribuicao.service;

import com.desafio.ubots.msdistribuicao.model.Request;
import com.desafio.ubots.msdistribuicao.model.ResponseDTO;
import com.desafio.ubots.msdistribuicao.model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CentralServiceTest {

    private CentralService centralService;

    @BeforeEach
    void setUp() {
        centralService = new CentralService();
    }

    @Test
    void testHandleRequestToCartoesTeam() {
        Request request = new Request("Problemas com cartão");
        ResponseDTO responseDTO = centralService.handleRequest(request);

        assertEquals("Cartões", responseDTO.getTeamName());
        assertNotNull(responseDTO.getAttendantName());
    }

    @Test
    void testHandleRequestToEmprestimosTeam() {
        Request request = new Request("Contratação de empréstimo");
        ResponseDTO responseDTO = centralService.handleRequest(request);

        assertEquals("Empréstimos", responseDTO.getTeamName());
        assertNotNull(responseDTO.getAttendantName());
    }

    @Test
    void testHandleRequestToOutrosAssuntosTeam() {
        Request request = new Request("Demais assuntos");
        ResponseDTO responseDTO = centralService.handleRequest(request);

        assertEquals("Outros Assuntos", responseDTO.getTeamName());
        assertNotNull(responseDTO.getAttendantName());
    }

    @Test
    void testRemoveRequestFromAttendant() {
        boolean result = centralService.removeRequestFromAttendant("Cartões", "André");
        assertTrue(result);
    }

    @Test
    void testRemoveRequestFromAttendantWhenTeamNotFound() {
        boolean result = centralService.removeRequestFromAttendant("RH", "André");
        assertFalse(result);
    }

    @Test
    void testRemoveRequestFromAttendantWhenAttendantNotFound() {
        boolean result = centralService.removeRequestFromAttendant("Cartões", "Gabriel");
        assertFalse(result);
    }

    @Test
    void testGetAllTeams() {
        List<Team> teams = centralService.getAllTeams();

        assertEquals(3, teams.size());

        assertTrue(teams.stream().anyMatch(team -> team.getName().equals("Cartões")));
        assertTrue(teams.stream().anyMatch(team -> team.getName().equals("Empréstimos")));
        assertTrue(teams.stream().anyMatch(team -> team.getName().equals("Outros Assuntos")));
    }
}
