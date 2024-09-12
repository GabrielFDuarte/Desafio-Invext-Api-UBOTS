package com.desafio.ubots.msdistribuicao.controller;

import com.desafio.ubots.msdistribuicao.model.Attendant;
import com.desafio.ubots.msdistribuicao.model.Request;
import com.desafio.ubots.msdistribuicao.model.ResponseDTO;
import com.desafio.ubots.msdistribuicao.model.Team;
import com.desafio.ubots.msdistribuicao.service.CentralService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class RequestControllerTest {

    @InjectMocks
    private RequestController requestController;

    @Mock
    private CentralService centralService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleRequestWhenEmptyTypeReturnsBadRequest() {
        Request request = new Request("");
        ResponseDTO expectedResponse = new ResponseDTO();
        expectedResponse.setMessage("O tipo da solicitação não pode ser vazio.");

        ResponseEntity<ResponseDTO> response = requestController.handleRequest(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(expectedResponse.getMessage(), response.getBody().getMessage());
    }

    @Test
    void testHandleRequestWhenValidTypeReturnsOk() {
        Request request = new Request("Problemas com cartão");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setTeamName("Cartões");
        responseDTO.setRequestPosition(1);
        responseDTO.setMessage("Solicitação recebida pelo time de Cartões. Solicitação em fila de espera na posição 1.");

        when(centralService.handleRequest(any(Request.class))).thenReturn(responseDTO);

        ResponseEntity<ResponseDTO> response = requestController.handleRequest(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO.getTeamName(), response.getBody().getTeamName());
        assertEquals(responseDTO.getMessage(), response.getBody().getMessage());
    }

    @Test
    void testGetRequestsSummaryReturnsSummary() {
        List<Attendant> attendants = new ArrayList<>();
        attendants.add(new Attendant("André"));
        attendants.add(new Attendant("Bianca"));

        Team team = new Team("Cartões", attendants);
        when(centralService.getAllTeams()).thenReturn(List.of(team));

        List<String> summaries = requestController.getRequestsSummary();

        assertNotNull(summaries);
        assertFalse(summaries.isEmpty());
    }

    @Test
    void testRemoveRequestFromAttendantSuccessReturnsOk() {
        String teamName = "Cartões";
        String attendantName = "André";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setTeamName(teamName);
        responseDTO.setAttendantName(attendantName);
        responseDTO.setMessage("Solicitação removida e fila atualizada para o atendente André.");

        when(centralService.removeRequestFromAttendant(anyString(), anyString())).thenReturn(true);

        ResponseEntity<ResponseDTO> response = requestController.removeRequestFromAttendant(teamName, attendantName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO.getMessage(), response.getBody().getMessage());
    }

    @Test
    void removeRequestFromAttendant_Failure_ReturnsNotFound() {
        String teamName = "Cartões";
        String attendantName = "André";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setTeamName(teamName);
        responseDTO.setAttendantName(attendantName);
        responseDTO.setMessage("Atendente ou time não encontrado.");

        when(centralService.removeRequestFromAttendant(anyString(), anyString())).thenReturn(false);

        ResponseEntity<ResponseDTO> response = requestController.removeRequestFromAttendant(teamName, attendantName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(responseDTO.getMessage(), response.getBody().getMessage());
    }
}
