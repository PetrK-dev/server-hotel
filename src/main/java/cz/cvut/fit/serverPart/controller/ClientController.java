package cz.cvut.fit.serverPart.controller;

import cz.cvut.fit.serverPart.dto.ClientCreateDTO;
import cz.cvut.fit.serverPart.dto.ClientDTO;
import cz.cvut.fit.serverPart.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/all")
    public List<ClientDTO> readAll() {
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    public ClientDTO readOneById(@PathVariable int id) {
        Optional<ClientDTO> clientDTOOptional = clientService.findByIdAsDTO(id);
        if( clientDTOOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        ClientDTO clientDTO = clientDTOOptional.get();
        clientDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).readOneById(id)).withRel("Client"));
        return clientDTO;
    }

    @PostMapping
    public ClientDTO create(@RequestBody ClientCreateDTO client) {
        ClientDTO clientDTO = clientService.create(client);
        clientDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).create(client)).withRel("Client"));
        return clientDTO;
    }

    @PutMapping("/{id}")
    public ClientDTO update(@PathVariable int id, @RequestBody ClientCreateDTO client){
        try {
            ClientDTO clientDTO = clientService.update(id, client);
            clientDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).update(id,client)).withRel("Client"));
            return clientDTO;
        } catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id){
        try {
            clientService.deleteById(id);
        }catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}