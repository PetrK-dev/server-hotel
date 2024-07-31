package cz.cvut.fit.serverPart.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void getFirstName() {
        Client client = new Client("Mr", "Nobody");
        assertEquals("Mr", client.getFirstName());
    }

    @Test
    void setFirstName() {
        Client client = new Client("Mr", "Nobody");
        client.setFirstName("XXX");
        assertEquals("XXX", client.getFirstName());
    }

    @Test
    void getLastName() {
        Client client = new Client("Mr", "Nobody");
        assertEquals("Nobody", client.getLastName());
    }

    @Test
    void setLastName() {
        Client client = new Client("Mr", "Nobody");
        client.setLastName("Petr");
        assertEquals("Petr", client.getLastName());
    }

    @Test
    void testEquals() {
        Client client1 = new Client("Mr", "Nobody");
        Client client2 = new Client("Mr", "Nobody");
        assertEquals( client1,  client2 );
    }
}