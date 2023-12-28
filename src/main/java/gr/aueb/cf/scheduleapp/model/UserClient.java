package gr.aueb.cf.scheduleapp.model;

import gr.aueb.cf.scheduleapp.model.ids.UserClientId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserClientId.class)
@Table(name = "USERS_CLIENTS")
public class UserClient {

    @Id
    @Column(name = "USER_ID")
    private Long userId;

    @Id
    @Column(name = "CLIENT_ID")
    private Long clientId;
}
