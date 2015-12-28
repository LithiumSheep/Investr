package group15.oose.investmenttinder.api.models.response_models;

public class SearchResponse {

    private String authorizationToken;
    private String search;

    public SearchResponse(String authorizationToken, String search) {
        this.authorizationToken = authorizationToken;
        this.search = search;
    }

    public String getAuthorizationToken() {
        return authorizationToken;
    }

    public String getSearch() {
        return search;
    }
}
