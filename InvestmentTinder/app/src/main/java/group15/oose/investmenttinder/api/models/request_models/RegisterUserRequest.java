package group15.oose.investmenttinder.api.models.request_models;

public class RegisterUserRequest {

    private String username;
    private String password;
    private ClientProfile profile;

    public RegisterUserRequest(String username, String password, ClientProfile profile) {
        this.username = username;
        this.password = password;
        this.profile = profile;
    }
}
