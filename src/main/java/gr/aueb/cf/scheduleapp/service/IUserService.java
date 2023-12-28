package gr.aueb.cf.scheduleapp.service;

import gr.aueb.cf.scheduleapp.dto.UserInsertDTO;
import gr.aueb.cf.scheduleapp.dto.UserUpdateDTO;
import gr.aueb.cf.scheduleapp.model.User;
import gr.aueb.cf.scheduleapp.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface IUserService {

    User insertUser(UserInsertDTO dto) throws Exception;
    User updateUser(UserUpdateDTO dto) throws EntityNotFoundException;
    User deleteUser(Long id) throws EntityNotFoundException;
    List<User> getAllUsers() throws Exception;
    List<User> getUsersByUsername(String username) throws EntityNotFoundException;
    User getUserById(Long id) throws EntityNotFoundException;
    User getUserByUsername(String username) throws EntityNotFoundException;
    boolean isUsernameTaken(String username) throws Exception;
}
