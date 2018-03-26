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

    @SerializedName("kandang")
    private List<Kandang> kandang;

    public void setKandang(List<Kandang> kandang){
        this.kandang = kandang;
    }

    public List<Kandang> getKandang(){
        return kandang;
    }

    @SerializedName("indukan")
    private List<Indukan> indukan;

    public void setIndukan(List<Indukan> indukan){
        this.indukan = indukan;
    }

    public List<Indukan> getIndukan(){
        return indukan;
    }

    @SerializedName("pakan")
    private List<Pakan> pakan;

    public void setPakan(List<Pakan> pakan){
        this.pakan = pakan;
    }

    public List<Pakan> getPakan(){
        return pakan;
    }

    @SerializedName("penyakit")
    private List<Penyakit> penyakit;

    public void setPenyakit(List<Penyakit> penyakit){
        this.penyakit = penyakit;
    }

    public List<Penyakit> getPenyakit(){
        return penyakit;
    }
}
