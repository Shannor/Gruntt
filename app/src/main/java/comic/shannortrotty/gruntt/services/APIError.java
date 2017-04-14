package comic.shannortrotty.gruntt.services;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shannortrotty on 4/14/17.
 */

public class APIError {

    @SerializedName("code")
    private int statusCode;
    @SerializedName("message")
    private String message;

    public APIError() {
    }

    public int status() {
        return statusCode;
    }

    public String message() {
        return message;
    }

    @Override
    public String toString() {
        return "APIError{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }
}
