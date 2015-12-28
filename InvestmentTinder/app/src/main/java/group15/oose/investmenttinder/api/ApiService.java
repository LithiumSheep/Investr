package group15.oose.investmenttinder.api;

import group15.oose.investmenttinder.api.models.request_models.ClientProfile;
import group15.oose.investmenttinder.api.models.request_models.LoginUserRequest;
import group15.oose.investmenttinder.api.models.request_models.RegisterUserRequest;
import group15.oose.investmenttinder.api.models.request_models.TickerSwipeRequest;
import group15.oose.investmenttinder.api.models.response_models.LoginResponse;
import group15.oose.investmenttinder.api.models.response_models.RecommendationResponse;
import group15.oose.investmenttinder.api.models.response_models.TickerDetailsResponse;
import com.squareup.okhttp.ResponseBody;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface ApiService {

    @POST("new")
    Call<LoginResponse> createUser(@Body RegisterUserRequest registerUserRequest);

    @POST("login")
    Call<LoginResponse> loginUser(@Body LoginUserRequest loginUserRequest);

    @GET("{ticker}")
    Call<TickerDetailsResponse> getTicker(@Path("ticker") String ticker);

    @GET("{userId}/rec")
    Call<List<RecommendationResponse>> getRecommendation(@Path("userId") String userId);

    @POST("{userId}/swipeleft")
    Call<ResponseBody> swipeLeft(@Path("userId") String userId, @Body TickerSwipeRequest tickerSwipeRequest);

    @POST("{userId}/swiperight")
    Call<ResponseBody> swipeRight(@Path("userId") String userId, @Body TickerSwipeRequest tickerSwipeRequest);

    @GET("{userId}/liked")
    Call<List<String>> getLikedList(@Path("userId") String userId);

    @PUT("{userId}")
    Call<ClientProfile> updateProfile(@Path("userId") String userId);
}
