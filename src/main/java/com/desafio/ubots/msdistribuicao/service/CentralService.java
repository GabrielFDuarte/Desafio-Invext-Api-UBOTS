package com.desafio.ubots.msdistribuicao.service;

import com.desafio.ubots.msdistribuicao.model.Attendant;
import com.desafio.ubots.msdistribuicao.model.Request;
import com.desafio.ubots.msdistribuicao.model.ResponseDTO;
import com.desafio.ubots.msdistribuicao.model.Team;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.*;

@Service
public class CentralService {
    private Map<String, Team> teams = new HashMap<>();
    private static final String CARTOES = "Cartões";
    private static final String EMPRESTIMOS = "Empréstimos";
    private static final String OUTROS_ASSUNTOS = "Outros Assuntos";
    private static final String PROBLEMAS_COM_CARTAO = "Problemas com cartão";
    private static final String CONTRATACAO_DE_EMPRESTIMO = "Contratação de empréstimo";

    public CentralService() {
        List<Attendant> cartoesTeamAttendants = new LinkedList<>();
        cartoesTeamAttendants.add(new Attendant("André"));
        cartoesTeamAttendants.add(new Attendant("Bianca"));

        List<Attendant> emprestimosTeamAttendants = new LinkedList<>();
        emprestimosTeamAttendants.add(new Attendant("Carla"));
        emprestimosTeamAttendants.add(new Attendant("Diego"));

        List<Attendant> outrosTeamAttendants = new LinkedList<>();
        outrosTeamAttendants.add(new Attendant("Eduardo"));
        outrosTeamAttendants.add(new Attendant("Fabiana"));

        teams.put(CARTOES, new Team(CARTOES, cartoesTeamAttendants));
        teams.put(EMPRESTIMOS, new Team(EMPRESTIMOS, emprestimosTeamAttendants));
        teams.put(OUTROS_ASSUNTOS, new Team(OUTROS_ASSUNTOS, outrosTeamAttendants));
    }

    public ResponseDTO handleRequest(Request request) {
        String type = normalizeString(request.getType());
        ResponseDTO responseDTO = new ResponseDTO();

        if (type.equals(normalizeString(PROBLEMAS_COM_CARTAO))) {
            return teams.get(CARTOES).assignRequest(request, responseDTO);
        } else if (type.equals(normalizeString(CONTRATACAO_DE_EMPRESTIMO))) {
            return teams.get(EMPRESTIMOS).assignRequest(request, responseDTO);
        } else {
            return teams.get(OUTROS_ASSUNTOS).assignRequest(request, responseDTO);
        }
    }

    private static String normalizeString(String input) {
        String normalized = input.toUpperCase();

        normalized = Normalizer.normalize(normalized, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        normalized = normalized.replace("Ç", "C");

        return normalized;
    }

    public List<Team> getAllTeams() {
        return new ArrayList<>(teams.values());
    }

    public boolean removeRequestFromAttendant(String teamName, String attendantName) {
        List<Team> teamsArrayList = new ArrayList<>(teams.values());
        for (Team team : teamsArrayList) {
            if (team.getName().equals(teamName)) {
                return team.removeRequestFromAttendant(attendantName);
            }
        }
        return false;
    }
}
