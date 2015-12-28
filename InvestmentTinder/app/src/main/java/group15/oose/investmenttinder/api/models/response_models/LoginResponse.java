package group15.oose.investmenttinder.api.models.response_models;

import com.google.gson.annotations.SerializedName;

import group15.oose.investmenttinder.api.models.request_models.ClientProfile;

public class LoginResponse {

    @SerializedName("userID")
    private String userID;
    private String username;
    private ClientProfile profile;

    public LoginResponse(String userId) {
        this.userID = userId;
    }

    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public ClientProfile getProfile() {
        return profile;
    }
}
