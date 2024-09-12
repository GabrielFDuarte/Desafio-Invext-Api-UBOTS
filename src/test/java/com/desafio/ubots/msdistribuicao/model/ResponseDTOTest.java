package com.desafio.ubots.msdistribuicao.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseDTOTest {

    private ResponseDTO responseDTO;

    @Test
    void testResponseDTOConstructorAndGetters() {
        String message = "Solicitação recebida.";
        String teamName = "Cartões";
        String attendantName = "André";
        int requestPosition = 1;

        responseDTO = new ResponseDTO(message, teamName, attendantName, requestPosition);

        assertEquals(message, responseDTO.getMessage());
        assertEquals(teamName, responseDTO.getTeamName());
        assertEquals(attendantName, responseDTO.getAttendantName());
        assertEquals(requestPosition, responseDTO.getRequestPosition());
    }

    @Test
    void testResponseDTOSetters() {
        responseDTO = new ResponseDTO();
        responseDTO.setMessage("Solicitação recebida.");
        responseDTO.setTeamName("Empréstimos");
        responseDTO.setAttendantName("Carla");
        responseDTO.setRequestPosition(2);

        assertEquals("Solicitação recebida.", responseDTO.getMessage());
        assertEquals("Empréstimos", responseDTO.getTeamName());
        assertEquals("Carla", responseDTO.getAttendantName());
        assertEquals(2, responseDTO.getRequestPosition());
    }
}
