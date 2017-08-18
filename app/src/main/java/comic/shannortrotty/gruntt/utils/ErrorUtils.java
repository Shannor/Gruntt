package comic.shannortrotty.gruntt.utils;

import java.io.IOException;
import java.lang.annotation.Annotation;

import comic.shannortrotty.gruntt.model.RetrofitGrunttBackendService;
import comic.shannortrotty.gruntt.services.APIError;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by shannortrotty on 4/14/17.
 */

public class ErrorUtils {
    public static APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter =
                RetrofitGrunttBackendService.retrofit
                        .responseBodyConverter(APIError.class, new Annotation[0]);

        APIError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIError();
        }

        return error;
    }
}
