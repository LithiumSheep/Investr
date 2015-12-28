package group15.oose.investmenttinder.api.models.error_handler;

public class ErrorResponseModel {

    private String reason;

    public ErrorResponseModel(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
