package com.desafio.ubots.msdistribuicao.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestTest {

    @Test
    void testRequestConstructorAndGetters() {
        String type = "Problemas com cartão";
        Request request = new Request(type);
        assertEquals(type, request.getType());
    }
}
