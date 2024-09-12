package com.desafio.ubots.msdistribuicao.controller;

import com.desafio.ubots.msdistribuicao.model.Request;
import com.desafio.ubots.msdistribuicao.model.ResponseDTO;
import com.desafio.ubots.msdistribuicao.model.Team;
import com.desafio.ubots.msdistribuicao.service.CentralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/request")
public class RequestController {

    private final CentralService centralService;

    @Autowired
    public RequestController(CentralService centralService) {
        this.centralService = centralService;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> handleRequest(@RequestBody Request request) {
        if (request.getType().isEmpty()) {
            ResponseDTO responseDTOEmpty = new ResponseDTO();
            responseDTOEmpty.setMessage("O tipo da solicitação não pode ser vazio.");
            return ResponseEntity.badRequest().body(responseDTOEmpty);
        }

        ResponseDTO responseDTO = centralService.handleRequest(request);
        String message = "Solicitação recebida pelo time de " + responseDTO.getTeamName() + ".";

        if (responseDTO.getRequestPosition() > 0) {
            message += " Solicitação em fila de espera na posição " + responseDTO.getRequestPosition() + ".";
        }

        responseDTO.setMessage(message);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public List<String> getRequestsSummary() {
        List<Team> teams = centralService.getAllTeams();

        return teams.stream().map(Team::getRequestSummary).toList();
    }

    @DeleteMapping("/{teamName}/{attendantName}")
    public ResponseEntity<ResponseDTO> removeRequestFromAttendant(@PathVariable String teamName, @PathVariable String attendantName) {
        boolean removed = centralService.removeRequestFromAttendant(teamName, attendantName);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setTeamName(teamName);
        responseDTO.setAttendantName(attendantName);

        if (removed) {
            responseDTO.setMessage("Solicitação removida e fila atualizada para o atendente " + attendantName + ".");
            return ResponseEntity.ok(responseDTO);
        } else {
            responseDTO.setMessage("Atendente ou time não encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }
}
