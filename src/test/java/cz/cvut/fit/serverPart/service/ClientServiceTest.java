package cz.cvut.fit.serverPart.service;

import cz.cvut.fit.serverPart.dto.ClientCreateDTO;
import cz.cvut.fit.serverPart.dto.ClientDTO;
import cz.cvut.fit.serverPart.entity.Client;
import cz.cvut.fit.serverPart.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.notNull;

@SpringBootTest
class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepository;

    @Test
    void findAll() {
        List<Client> clients = new ArrayList<>();
        Client client0 = new Client("Mr1", "Nobody1");
        ReflectionTestUtils.setField(client0, "id", 0);
        Client client1 = new Client("Mr2", "Nobody2");
        ReflectionTestUtils.setField(client1, "id", 1);
        Client client2 = new Client("Mr3", "Nobody3");
        ReflectionTestUtils.setField(client2, "id", 2);
        clients.add(client0);
        clients.add(client1);
        clients.add(client2);

        BDDMockito.given(clientRepository.findAll()).willReturn(clients);

        List<ClientDTO> clientsDTOList = clientService.findAll();

        Assertions.assertEquals(clientsDTOList.size(), clients.size());
        Assertions.assertEquals(clientsDTOList.get(0).getId(), client0.getId());
        Assertions.assertEquals(clientsDTOList.get(1).getId(), client1.getId());
        Assertions.assertEquals(clientsDTOList.get(2).getId(), client2.getId());
        Assertions.assertEquals(clientsDTOList.get(0).getFirstName(), client0.getFirstName());
        Assertions.assertEquals(clientsDTOList.get(1).getFirstName(), client1.getFirstName());
        Assertions.assertEquals(clientsDTOList.get(2).getFirstName(), client2.getFirstName());
        Assertions.assertEquals(clientsDTOList.get(0).getLastName(), client0.getLastName());
        Assertions.assertEquals(clientsDTOList.get(1).getLastName(), client1.getLastName());
        Assertions.assertEquals(clientsDTOList.get(2).getLastName(), client2.getLastName());

        Mockito.verify(clientRepository, Mockito.atLeastOnce()).findAll();

    }

    @Test
    void findByIds() {
        List<Client> clients = new ArrayList<>();
        List<Integer> clientsIds = new ArrayList<>();
        Client client0 = new Client("Mr1", "Nobody1");
        ReflectionTestUtils.setField(client0, "id", 0);
        Client client1 = new Client("Mr2", "Nobody2");
        ReflectionTestUtils.setField(client1, "id", 1);
        Client client2 = new Client("Mr3", "Nobody3");
        ReflectionTestUtils.setField(client2, "id", 2);
        clientsIds.add(client0.getId());
        clientsIds.add(client1.getId());
        clientsIds.add(client2.getId());
        clients.add(client0);
        clients.add(client1);
        clients.add(client2);

        BDDMockito.given(clientRepository.findAllById(clientsIds)).willReturn(clients);

        List<Client> clientsList = clientService.findByIds(clientsIds);

        Assertions.assertEquals(clientsList.size(), clients.size());
        Assertions.assertEquals(clientsList.get(0), client0);
        Assertions.assertEquals(clientsList.get(1), client1);
        Assertions.assertEquals(clientsList.get(2), client2);

        Mockito.verify(clientRepository, Mockito.atLeastOnce()).findAllById(clientsIds);
    }

    @Test
    void findById() {
        Client client = new Client("Mr", "Nobody");

        BDDMockito.given(clientRepository.findById(client.getId())).willReturn(Optional.of(client));
        Optional<Client> clientOptional = clientService.findById(client.getId());

        if(clientOptional.isPresent()) {
            Client client1 = clientOptional.get();
            Assertions.assertEquals(client, client1);
        }
        else
           throw new EntityNotFoundException("Client service returned null");

        Mockito.verify(clientRepository,Mockito.atLeastOnce()).findById(client.getId());
    }

    @Test
    void findByIdAsDTO() {
        Client client = new Client("Mr", "Nobody");
        ReflectionTestUtils.setField(client, "id", 0);
        ClientDTO clientDTO = new ClientDTO(client.getId(),"Mr", "Nobody");

        BDDMockito.given(clientRepository.findById(client.getId())).willReturn(Optional.of(client));

        if(clientService.findByIdAsDTO(client.getId()).isPresent()) {
            ClientDTO clientDTO1 = clientService.findByIdAsDTO(client.getId()).get();
            Assertions.assertEquals(clientDTO, clientDTO1);
        }
        else
            throw new EntityNotFoundException("Client service returned null");

        Mockito.verify(clientRepository,Mockito.atLeastOnce()).findById(client.getId());
    }

    @Test
    void create() {
        Client client = new Client("Mr", "Nobody");
        ReflectionTestUtils.setField(client, "id", 0);
        ClientDTO clientDTO = new ClientDTO(client.getId(),client.getFirstName(),client.getLastName());
        ClientCreateDTO clientCreateDTO = new ClientCreateDTO("Mr", "Nobody");
        BDDMockito.given(clientRepository.save((Client)notNull())).willReturn(client);
        Assertions.assertEquals(clientDTO, clientService.create(clientCreateDTO));
        Mockito.verify(clientRepository, Mockito.atLeastOnce()).save((Client)notNull());
    }

    @Test
    void update(){
        Client client = new Client("Mr", "Nobody");
        ReflectionTestUtils.setField(client, "id", 0);
        ClientCreateDTO clientCreateDTOUpdate = new ClientCreateDTO("Mr", "NobodyUpdate");

        BDDMockito.given(clientRepository.findById(client.getId())).willReturn(Optional.of(client));

        ClientDTO clientDTO;
        try {
            clientDTO = clientService.update(client.getId(), clientCreateDTOUpdate);
        }catch(EntityNotFoundException e) {
            throw new EntityNotFoundException("Client service returned Exception");
        }

        Assertions.assertEquals(clientDTO.getId(), client.getId());
        Assertions.assertEquals(clientDTO.getFirstName(), client.getFirstName());
        Assertions.assertEquals(clientDTO.getLastName(), client.getLastName());

        Mockito.verify(clientRepository,Mockito.atLeastOnce()).findById(client.getId());
    }

    @Test
    void deleteById(){
        Client client = new Client("Mr", "Nobody");
        ReflectionTestUtils.setField(client, "id", 0);

        BDDMockito.given(clientRepository.findById(client.getId())).willReturn(Optional.of(client));

        try {
            clientService.deleteById(0);
        }
        catch(EntityNotFoundException e){
            throw new EntityNotFoundException("Client service returned Exception");
        }
        Mockito.verify(clientRepository,Mockito.atLeastOnce()).findById(client.getId());
        Mockito.verify(clientRepository, Mockito.atLeastOnce()).delete((Client)notNull());
    }

}