package id.sapi.ktp.aplikasiktpsapi.modal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ASUS on 2/23/2018.
 */

public class Result {
    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private String value;

    @SerializedName("user")
    private User user;

    @SerializedName("sapi")
    private User sapi;

    public Result(Boolean error, String value, String message, User user) {
        this.value = value;
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

    public String getValue(){return value;}

    public User getUser() {
        return user;
    }

    public User getSapi(){return sapi;}

    @SerializedName("jenis")
    private List<Jenis> jenis;

    public void setJenis(List<Jenis> jenis){
        this.jenis = jenis;
    }

    public List<Jenis> getJenis(){
        return jenis;
    }
}
