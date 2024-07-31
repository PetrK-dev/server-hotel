package cz.cvut.fit.serverPart.repository;

import cz.cvut.fit.serverPart.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
