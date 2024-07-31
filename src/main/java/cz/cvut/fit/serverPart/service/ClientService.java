package cz.cvut.fit.serverPart.service;

import cz.cvut.fit.serverPart.dto.ClientCreateDTO;
import cz.cvut.fit.serverPart.dto.ClientDTO;
import cz.cvut.fit.serverPart.entity.Client;
import cz.cvut.fit.serverPart.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional(readOnly = true)
    public List<ClientDTO> findAll() {
        return clientRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Client> findByIds(List<Integer> ids) {
        return clientRepository.findAllById(ids);
    }

    @Transactional(readOnly = true)
    public Optional<Client> findById(int id) {
        return clientRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<ClientDTO> findByIdAsDTO(int id) {
        return toDTO(findById(id));
    }

    @Transactional
    public ClientDTO create(ClientCreateDTO clientDTO) {
        return toDTO(clientRepository.save(
                new Client(clientDTO.getFirstName(), clientDTO.getLastName() )
            )
        );
    }

    @Transactional
    public ClientDTO update(int id, ClientCreateDTO clientDTO)throws EntityNotFoundException {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isEmpty())
            throw new EntityNotFoundException("no such client");
        Client client = optionalClient.get();
        client.setFirstName(clientDTO.getFirstName());
        client.setLastName(clientDTO.getLastName());
        return toDTO(client);
    }

    @Transactional
    public void deleteById( int id) throws EntityNotFoundException{
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isEmpty())
            throw new EntityNotFoundException("no such client");
        Client client = optionalClient.get();
        clientRepository.delete(client);
    }

    private ClientDTO toDTO(Client client) {
        return new ClientDTO(client.getId(), client.getFirstName(), client.getLastName());
    }

    private Optional<ClientDTO> toDTO(Optional<Client> client) {
        if (client.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(client.get()));
    }
}
