package cz.cvut.fit.serverPart.controller;

import cz.cvut.fit.serverPart.dto.ClientCreateDTO;
import cz.cvut.fit.serverPart.dto.ClientDTO;
import cz.cvut.fit.serverPart.entity.Client;
import cz.cvut.fit.serverPart.service.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class ClientControllerTest {

    @Autowired
    private ClientController clientController;

    @MockBean
    private ClientService clientService;

    @Test
    void readAll() {
        List<ClientDTO> clientsDTO = new ArrayList<>();
        ClientDTO clientDTO0 = new ClientDTO(0,"Mr", "Nobody");
        ClientDTO clientDTO1 = new ClientDTO(1,"Mr", "Nobody1");
        ClientDTO clientDTO2 = new ClientDTO(2,"Mr", "Nobody2");
        clientsDTO.add(clientDTO0);
        clientsDTO.add(clientDTO1);
        clientsDTO.add(clientDTO2);

        BDDMockito.given(clientService.findAll()).willReturn(clientsDTO);

        List<ClientDTO> clientsDTOList = clientController.readAll();
        Assertions.assertEquals(clientsDTOList.size(), clientsDTO.size());
        Assertions.assertEquals(clientsDTOList.get(0).getId(), clientsDTO.get(0).getId());
        Assertions.assertEquals(clientsDTOList.get(1).getId(), clientsDTO.get(1).getId());
        Assertions.assertEquals(clientsDTOList.get(2).getId(), clientsDTO.get(2).getId());
        Assertions.assertEquals(clientsDTOList.get(0).getFirstName(), clientsDTO.get(0).getFirstName());
        Assertions.assertEquals(clientsDTOList.get(1).getFirstName(), clientsDTO.get(1).getFirstName());
        Assertions.assertEquals(clientsDTOList.get(2).getFirstName(), clientsDTO.get(2).getFirstName());
        Assertions.assertEquals(clientsDTOList.get(0).getLastName(), clientsDTO.get(0).getLastName());
        Assertions.assertEquals(clientsDTOList.get(1).getLastName(), clientsDTO.get(1).getLastName());
        Assertions.assertEquals(clientsDTOList.get(2).getLastName(), clientsDTO.get(2).getLastName());

        Mockito.verify(clientService, Mockito.atLeastOnce()).findAll();

    }

    @Test
    void readOneById() {
        Client client = new Client("Mr", "Nobody");
        ReflectionTestUtils.setField(client, "id", 0);
        ClientDTO clientDTO = new ClientDTO(client.getId(),client.getFirstName(),client.getLastName());

        BDDMockito.given(clientService.findByIdAsDTO(clientDTO.getId())).willReturn(Optional.of(clientDTO));

        Assertions.assertEquals(clientDTO, clientController.readOneById(client.getId()));

        Mockito.verify(clientService, Mockito.atLeastOnce()).findByIdAsDTO(client.getId());
    }

    @Test
    void create() {
        Client client = new Client("Mr", "Nobody");
        ReflectionTestUtils.setField(client, "id", 0);
        ClientDTO clientDTO = new ClientDTO(client.getId(),client.getFirstName(),client.getLastName());
        ClientCreateDTO clientCreateDTO = new ClientCreateDTO("Mr", "Nobody");

        BDDMockito.given(clientService.create(clientCreateDTO)).willReturn(clientDTO);

        Assertions.assertEquals(clientDTO, clientController.create(clientCreateDTO));

        Mockito.verify(clientService, Mockito.atLeastOnce()).create(clientCreateDTO);
    }

    @Test
    void update(){
        Client client = new Client("Mr", "Nobody");
        ReflectionTestUtils.setField(client, "id", 0);
        ClientCreateDTO clientCreateDTO = new ClientCreateDTO("Mr", "NobodyUpdate");
        ClientDTO clientDTO = new ClientDTO(client.getId(),"Mr", "NobodyUpdate");

        BDDMockito.given(clientService.update(client.getId(), clientCreateDTO)).willReturn(clientDTO);

        Assertions.assertEquals(clientDTO, clientController.update(client.getId(), clientCreateDTO));

        Mockito.verify(clientService, Mockito.atLeastOnce()).update(client.getId(), clientCreateDTO);
    }

    @Test
    void delete(){
        Client client = new Client("Mr", "Nobody");
        ReflectionTestUtils.setField(client, "id", 0);

        BDDMockito.given(clientService.findById(client.getId())).willReturn(Optional.of(client));

        clientController.deleteById(client.getId());

        Mockito.verify(clientService, Mockito.atLeastOnce()).deleteById(client.getId());
    }
}