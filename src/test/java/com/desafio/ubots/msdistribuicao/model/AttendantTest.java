package com.desafio.ubots.msdistribuicao.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttendantTest {

    private Attendant attendant;

    @BeforeEach
    void setUp() {
        attendant = new Attendant("André");
    }

    @Test
    void testAddRequest() {
        Request request1 = new Request("Solicitação 1");
        assertTrue(attendant.isAvailable());

        attendant.addRequest(request1);
        assertTrue(attendant.isAvailable());
        assertEquals(1, attendant.getRequestsList().size());

        Request request2 = new Request("Solicitação 2");
        Request request3 = new Request("Solicitação 3");
        attendant.addRequest(request2);
        attendant.addRequest(request3);

        assertFalse(attendant.isAvailable());
        assertEquals(3, attendant.getRequestsList().size());
    }

    @Test
    void testRemoveRequest() {
        Request request = new Request("Problemas com cartão");
        attendant.addRequest(request);
        attendant.removeRequest();

        assertTrue(attendant.isAvailable());
        assertTrue(attendant.getRequestsList().isEmpty());
    }

    @Test
    void testGetName() {
        assertEquals("André", attendant.getName());
    }
}
