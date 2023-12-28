package gr.aueb.cf.scheduleapp.model.details;

import gr.aueb.cf.scheduleapp.model.Client;
import gr.aueb.cf.scheduleapp.model.User;

public class UserClientDetails {

    private User user;
    private Client client;

    public UserClientDetails(User user, Client client) {
        this.user = user;
        this.client = client;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "UserClientDetails{" +
                "user=" + user +
                ", client=" + client +
                '}';
    }
}
