package comic.shannortrotty.gruntt.services;

/**
 * Created by shannortrotty on 4/14/17.
 */

public class APIError {

    private int statusCode;
    private String message;

    public APIError() {
    }

    public int status() {
        return statusCode;
    }

    public String message() {
        return message;
    }
}
