package id.sapi.ktp.aplikasiktpsapi.api;

/**
 * Created by hestimr on 29/08/2017.
 */

public class BaseResponse {
    private boolean error;
    private String message;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
