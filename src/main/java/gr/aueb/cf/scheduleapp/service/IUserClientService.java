package gr.aueb.cf.scheduleapp.service;

import gr.aueb.cf.scheduleapp.dto.UserClientDTO;
import gr.aueb.cf.scheduleapp.model.UserClient;
import gr.aueb.cf.scheduleapp.service.exceptions.EntityNotFoundException;

public interface IUserClientService {

    UserClient insertUserClient(UserClientDTO dto) throws Exception;
    UserClient deleteUserClient(UserClientDTO dto) throws EntityNotFoundException;
    UserClient getUserClientByUserId(Long id) throws EntityNotFoundException;
    UserClient getUserClientByClientId(Long id) throws EntityNotFoundException;
}
