package com.desafio.ubots.msdistribuicao.service;

import com.desafio.ubots.msdistribuicao.model.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class AttendantServiceTest {

    private AttendantService attendantService;

    @BeforeEach
    void setUp() {
        attendantService = new AttendantService();
    }

    @Test
    void testAddRequest() {
        Request request = new Request("Problemas com cartão");
        boolean result = attendantService.addRequest(request);

        assertTrue(result);
        assertTrue(attendantService.isAvailable());
        assertEquals(1, attendantService.getActiveRequests().size());
    }

    @Test
    void testRemoveRequest() {
        Request request = new Request("Problemas com cartão");
        attendantService.addRequest(request);
        boolean removed = attendantService.removeRequest();

        assertTrue(removed);
        assertTrue(attendantService.isAvailable());
    }

    @Test
    void testIsAvailableWhenUnderMaxCapacity() {
        Request request1 = new Request("Solicitação 1");
        Request request2 = new Request("Solicitação 2");

        attendantService.addRequest(request1);
        assertTrue(attendantService.isAvailable());

        attendantService.addRequest(request2);
        assertTrue(attendantService.isAvailable());
    }

    @Test
    void testIsNotAvailableWhenMaxCapacityReached() {
        Request request1 = new Request("Solicitação 1");
        Request request2 = new Request("Solicitação 2");
        Request request3 = new Request("Solicitação 3");

        attendantService.addRequest(request1);
        attendantService.addRequest(request2);
        attendantService.addRequest(request3);

        assertFalse(attendantService.isAvailable());
    }

    @Test
    void testGetActiveRequests() {
        Request request1 = new Request("Solicitação 1");
        Request request2 = new Request("Solicitação 2");

        attendantService.addRequest(request1);
        attendantService.addRequest(request2);

        Queue<Request> activeRequests = attendantService.getActiveRequests();
        assertEquals(2, activeRequests.size());
        assertEquals("Solicitação 1", activeRequests.poll().getType());
        assertEquals("Solicitação 2", activeRequests.poll().getType());
    }
}
