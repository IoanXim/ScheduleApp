package gr.aueb.cf.scheduleapp.service;

import gr.aueb.cf.scheduleapp.dto.ClientInsertDTO;
import gr.aueb.cf.scheduleapp.dto.ClientUpdateDTO;
import gr.aueb.cf.scheduleapp.model.Client;
import gr.aueb.cf.scheduleapp.repository.ClientRepository;
import gr.aueb.cf.scheduleapp.service.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ClientServiceImpl implements IClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional
    @Override
    public Client insertClient(ClientInsertDTO dto) throws Exception {
        Client client = null;
        try {
            client = clientRepository.save(convertClientInsertDtoToClient(dto));
            if (client.getId() == null) throw new Exception("Insert Error");
        } catch (Exception e) {
            log.info("Insert Client Error");
            throw e;
        }
        return client;
    }

    @Transactional
    @Override
    public Client updateClient(ClientUpdateDTO dto) throws EntityNotFoundException {
        Client client = null;
        Client updatedClient = null;
        try {
            client = clientRepository.findClientById(dto.getId());
            if (client == null) throw new EntityNotFoundException(Client.class, dto.getId());
            updatedClient = clientRepository.save(convertClientUpdateDtoToClient(dto));
        } catch (EntityNotFoundException e) {
            log.info("Error in client update");
            throw e;
        }
        return updatedClient;
    }

    @Transactional
    @Override
    public Client deleteClient(Long id) throws EntityNotFoundException {
        Client client = null;
        try {
            client = clientRepository.findClientById(id);
            if (client == null) throw new EntityNotFoundException(Client.class, id);
            clientRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            log.info("Error in client delete");
            throw e;
        }
        return client;
    }

    @Override
    public List<Client> getClientsByLastname(String lastname) throws EntityNotFoundException {
        List<Client> clients = new ArrayList<>();
        try {
            clients = clientRepository.findClientsByLastnameStartingWith(lastname);
            if (clients.size() == 0) throw new EntityNotFoundException(Client.class, 0L);
        } catch (EntityNotFoundException e) {
            log.info("Error in Get clients by lastname");
            throw e;
        }
        return clients;
    }

    @Override
    public List<Client> getAllClients() throws Exception {
        List<Client> clients = new ArrayList<>();
        try {
            clients = clientRepository.findAll();
            if (clients.size() == 0) throw new EntityNotFoundException(Client.class, 0L);
        } catch (EntityNotFoundException e) {
            log.info("Error in Get all clients");
            throw e;
        }
        return clients;
    }

    @Override
    public Client getClientById(Long id) throws EntityNotFoundException {
        Client client;
        try {
            client = clientRepository.findClientById(id);
            if (client == null) throw new EntityNotFoundException(Client.class, id);
        } catch (EntityNotFoundException e) {
            log.info("Error in get client by id");
            throw e;
        }
        return client;
    }

    private Client convertClientInsertDtoToClient(ClientInsertDTO clientInsertDTO) {
        return new Client(null, clientInsertDTO.getFirstname(), clientInsertDTO.getLastname(), clientInsertDTO.getGender(), clientInsertDTO.getBirthday());
    }

    private Client convertClientUpdateDtoToClient(ClientUpdateDTO clientUpdateDTO) {
        return new Client(clientUpdateDTO.getId(), clientUpdateDTO.getFirstname(), clientUpdateDTO.getLastname(), clientUpdateDTO.getGender(), clientUpdateDTO.getBirthday());
    }
}
