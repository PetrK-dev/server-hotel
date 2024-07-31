package cz.cvut.fit.serverPart.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientDTOTest {

    @Test
    void getId() {
        ClientDTO clientDTO = new ClientDTO(0,"Mr", "Nobody");
        assertEquals(0, clientDTO.getId());
    }

    @Test
    void getFirstName() {
        ClientDTO clientDTO = new ClientDTO(0,"Mr", "Nobody");
        assertEquals("Mr", clientDTO.getFirstName());
    }

    @Test
    void getLastName() {
        ClientDTO clientDTO = new ClientDTO(0,"Mr", "Nobody");
        assertEquals("Nobody", clientDTO.getLastName());
    }

    @Test
    void testEquals() {
        ClientDTO clientDTO1 = new ClientDTO(0,"Mr", "Nobody");
        ClientDTO clientDTO2 = new ClientDTO(0,"Mr", "Nobody");
        assertEquals( clientDTO1, clientDTO2);
    }
}