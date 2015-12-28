package group15.oose.investmenttinder.api.models.error_handler;

import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.annotation.Annotation;

import retrofit.Converter;
import retrofit.Retrofit;

public class ErrorHelper {

    public static ErrorResponseModel parseError(ResponseBody responseBody, Retrofit retrofit) {
        Converter<ResponseBody, ErrorResponseModel> converter =
                retrofit.responseConverter(ErrorResponseModel.class, new Annotation[0]);

        ErrorResponseModel error = new ErrorResponseModel("Some server error sorry");
        try {
            error = converter.convert(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return error;
    }
}
