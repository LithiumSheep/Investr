package group15.oose.investmenttinder.helpers;

import group15.oose.investmenttinder.api.ApiService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

//This library provides methods which can be used to easily perform api calls and eventually handle errors

public class RetrofitCustomBuilder {

    private Retrofit retrofit;
    private ApiService apiService;

    public RetrofitCustomBuilder() {

        OkHttpClient client = new OkHttpClient();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        client.setConnectTimeout(60, TimeUnit.SECONDS);
        client.setReadTimeout(60, TimeUnit.SECONDS);
        client.setWriteTimeout(60, TimeUnit.SECONDS);
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(interceptor);
        client.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("Accept", "application/json").build();
                return chain.proceed(request);
            }
        });

        final Gson gson = new GsonBuilder()
                .setFieldNamingStrategy(new AndroidUnderscoreNamingStrategy())
                .create();

        this.retrofit = new Retrofit.Builder()
                .baseUrl(AppConst.API_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        this.apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }
}
