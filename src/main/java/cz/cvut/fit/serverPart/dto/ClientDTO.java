package cz.cvut.fit.serverPart.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class ClientDTO extends RepresentationModel<ClientDTO>{

    private final int id;
    private final String firstName;
    private final String lastName;

    public ClientDTO(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDTO clientDTO = (ClientDTO) o;
        return id == clientDTO.id &&
                firstName.equals(clientDTO.firstName) &&
                lastName.equals(clientDTO.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }
}
