package gr.aueb.cf.scheduleapp.repository;

import gr.aueb.cf.scheduleapp.model.UserClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserClientRepository extends JpaRepository<UserClient, Long> {

    UserClient findUserClientByUserId(Long userId);
    UserClient findUserClientByClientId(Long clientId);

    UserClient findUserClientByUserIdAndClientId(Long userId, Long clientId);
}
