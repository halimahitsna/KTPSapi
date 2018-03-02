package id.sapi.ktp.aplikasiktpsapi.modal;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ASUS on 2/23/2018.
 */

public class Result {
    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private User user;

    @SerializedName("sapi")
    private User sapi;

    public Result(Boolean error, String message, User user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public User getSapi(){return sapi;}
}