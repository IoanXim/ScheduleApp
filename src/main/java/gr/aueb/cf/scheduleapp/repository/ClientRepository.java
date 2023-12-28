package gr.aueb.cf.scheduleapp.repository;

import gr.aueb.cf.scheduleapp.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findClientsByLastnameStartingWith(String lastname);
    Client findClientById(Long id);
}
