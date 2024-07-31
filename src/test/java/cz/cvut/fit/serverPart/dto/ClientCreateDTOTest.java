package cz.cvut.fit.serverPart.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientCreateDTOTest {

    @Test
    void getFirstName() {
        ClientCreateDTO clientCreateDTO = new ClientCreateDTO("Mr", "Nobody");
        assertEquals("Mr", clientCreateDTO.getFirstName());
    }

    @Test
    void getLastName() {
        ClientCreateDTO clientCreateDTO = new ClientCreateDTO("Mr", "Nobody");
        assertEquals("Nobody", clientCreateDTO.getLastName());
    }

    @Test
    void testEquals() {
        ClientCreateDTO clientCreateDTO1 = new ClientCreateDTO("Mr", "Nobody");
        ClientCreateDTO clientCreateDTO2 = new ClientCreateDTO("Mr", "Nobody");
        assertEquals( clientCreateDTO1, clientCreateDTO2);
    }
}