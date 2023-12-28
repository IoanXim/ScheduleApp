package gr.aueb.cf.scheduleapp.repository;

import gr.aueb.cf.scheduleapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    List<User> findByUsernameStartingWith(String username);
    User findUserById(Long id);
    Boolean existsByUsername(String username);
}
