package gr.aueb.cf.scheduleapp.service;

import gr.aueb.cf.scheduleapp.dto.ClientInsertDTO;
import gr.aueb.cf.scheduleapp.dto.ClientUpdateDTO;
import gr.aueb.cf.scheduleapp.model.Client;
import gr.aueb.cf.scheduleapp.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface IClientService {

    Client insertClient(ClientInsertDTO dto) throws Exception;
    Client updateClient(ClientUpdateDTO dto) throws EntityNotFoundException;
    Client deleteClient(Long id) throws EntityNotFoundException;
    List<Client> getClientsByLastname(String lastname) throws EntityNotFoundException;
    List<Client> getAllClients() throws Exception;
    Client getClientById(Long id) throws EntityNotFoundException;
}
