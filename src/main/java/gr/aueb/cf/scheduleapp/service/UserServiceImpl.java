package gr.aueb.cf.scheduleapp.service;

import gr.aueb.cf.scheduleapp.dto.UserInsertDTO;
import gr.aueb.cf.scheduleapp.dto.UserUpdateDTO;
import gr.aueb.cf.scheduleapp.model.Client;
import gr.aueb.cf.scheduleapp.model.User;
import gr.aueb.cf.scheduleapp.repository.UserRepository;
import gr.aueb.cf.scheduleapp.service.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public User insertUser(UserInsertDTO dto) throws Exception {
        User user = null;
        try {
            user = userRepository.save(convertUserInsertDtoToUser(dto));
            if (user.getId() == null) throw new Exception("Insert user error");
        } catch (Exception e) {
            log.info("Insert user error");
            throw e;
        }
        return user;
    }

    @Override
    public User updateUser(UserUpdateDTO dto) throws EntityNotFoundException {
        User user = null;
        User updatedUser = null;
        try {
            user = userRepository.findUserById(dto.getId());
            if (user == null) throw new EntityNotFoundException(User.class, dto.getId());
            updatedUser = userRepository.save(convertUserUpdateDtoToUser(dto));
        } catch (EntityNotFoundException e) {
            log.info("Error in user update");
        }
        return updatedUser;
    }

    @Override
    public User deleteUser(Long id) throws EntityNotFoundException {
        User user = null;
        try {
            user = userRepository.findUserById(id);
            if (user == null) throw new EntityNotFoundException(User.class, id);
            userRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            log.info("Error in user delete");
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        try {
            users = userRepository.findAll();
            if (users.size() == 0) throw new EntityNotFoundException(Client.class, 0L);
        } catch (EntityNotFoundException e) {
            log.info("Error in Get users by username");
            throw e;
        }
        return users;
    }
    @Override
    public List<User> getUsersByUsername(String username) throws EntityNotFoundException {
        List<User> users = new ArrayList<>();
        try {
            users = userRepository.findByUsernameStartingWith(username);
            if (users.size() == 0) throw new EntityNotFoundException(Client.class, 0L);
        } catch (EntityNotFoundException e) {
            log.info("Error in Get users by username");
            throw e;
        }
        return users;
    }

    @Override
    public User getUserById(Long id) throws EntityNotFoundException {
        User user = null;
        try {
            user = userRepository.findUserById(id);
            if (user == null) throw new EntityNotFoundException(Client.class, id);
        } catch (EntityNotFoundException e) {
            log.info("Error in Get user by id");
            throw e;
        }
        return user;
    }

    @Override
    public User getUserByUsername(String username) throws EntityNotFoundException {
        User user = null;
        try {
            user = userRepository.findUserByUsername(username);
            if (user == null) throw new EntityNotFoundException(Client.class, 0L);
        } catch (EntityNotFoundException e) {
            log.info("Error in Get user by username");
            throw e;
        }
        return user;
    }

    @Override
    public boolean isUsernameTaken(String username) {
        return  userRepository.existsByUsername(username);
    }

    private User convertUserInsertDtoToUser(UserInsertDTO userInsertDTO) {
        return new User(null, userInsertDTO.getUsername(), userInsertDTO.getPassword());
    }

    private User convertUserUpdateDtoToUser(UserUpdateDTO userUpdateDTO) {
        return new User(userUpdateDTO.getId(), userUpdateDTO.getUsername(), userUpdateDTO.getPassword());
    }
}
