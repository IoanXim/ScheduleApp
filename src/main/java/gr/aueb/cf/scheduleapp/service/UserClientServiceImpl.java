package gr.aueb.cf.scheduleapp.service;

import gr.aueb.cf.scheduleapp.dto.UserClientDTO;
import gr.aueb.cf.scheduleapp.model.UserClient;
import gr.aueb.cf.scheduleapp.repository.UserClientRepository;
import gr.aueb.cf.scheduleapp.service.exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserClientServiceImpl implements IUserClientService {

    private final UserClientRepository userClientRepository;

    @Autowired
    public UserClientServiceImpl(UserClientRepository userClientRepository) {
        this.userClientRepository = userClientRepository;
    }

    @Override
    public UserClient insertUserClient(UserClientDTO dto) throws Exception {
        UserClient userClient = null;
        try {
            userClient = userClientRepository.save(convertUserClientDtoToUserClient(dto));
            if (userClient.getClientId() == null || userClient.getUserId() == null) throw new Exception("Insert Error");

        } catch (Exception e) {
            log.info("UserClient Insert Error");
        }
        return userClient;
    }

    @Override
    public UserClient deleteUserClient(UserClientDTO dto) throws EntityNotFoundException {
        UserClient userClient = null;

        try {
            userClient = userClientRepository.findUserClientByUserIdAndClientId(dto.getUserId(), dto.getClientId());
            if (userClient == null) throw new EntityNotFoundException(UserClient.class, 0L);
            userClientRepository.delete(userClient);
        } catch (EntityNotFoundException e) {
            log.info("UserClient delete Error");
        }
        return userClient;
    }

    @Override
    public UserClient getUserClientByUserId(Long id) throws EntityNotFoundException {
        UserClient userClient = null;

        try {
            userClient = userClientRepository.findUserClientByUserId(id);
            if (userClient == null) throw new EntityNotFoundException(UserClient.class, 0L);
        } catch (EntityNotFoundException e) {
            log.info("UserClient get by userId: " + id + " not found");
        }
        return userClient;
    }

    @Override
    public UserClient getUserClientByClientId(Long id) throws EntityNotFoundException {
        UserClient userClient = null;

        try {
            userClient = userClientRepository.findUserClientByClientId(id);
            if (userClient == null) throw new EntityNotFoundException(UserClient.class, 0L);
        } catch (EntityNotFoundException e) {
            log.info("UserClient get by clientId: " + id + " not found");
        }
        return userClient;
    }

    private UserClient convertUserClientDtoToUserClient(UserClientDTO userClientDTO) {
        return new UserClient(userClientDTO.getUserId(), userClientDTO.getClientId());
    }
}
