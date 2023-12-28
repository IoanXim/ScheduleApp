package gr.aueb.cf.scheduleapp.dto;

public class UserClientDTO {

    private Long userId;
    private Long clientId;
    public UserClientDTO() {
    }
    public UserClientDTO(Long userId, Long clientId) {
        this.userId = userId;
        this.clientId = clientId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
