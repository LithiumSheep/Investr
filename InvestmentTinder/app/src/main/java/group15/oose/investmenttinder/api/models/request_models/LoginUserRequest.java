package group15.oose.investmenttinder.api.models.request_models;

public class LoginUserRequest {

    private String username;
    private String password;

    public LoginUserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
