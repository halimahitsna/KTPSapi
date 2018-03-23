package id.sapi.ktp.aplikasiktpsapi.modal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ASUS on 3/23/2018.
 */

public class ResponseData {
    @SerializedName("jenis")
    private List<Jenis> jenis;
    @SerializedName("indukan")
    private List<Indukan> indukan;
    @SerializedName("kandang")
    private List<Kandang> kandang;
    @SerializedName("penyakit")
    private List<Penyakit> penyakit;
    @SerializedName("pakan")
    private List<Pakan> pakan;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public void setJenis(List<Jenis> jenis){
        this.jenis = jenis;
    }

    public List<Jenis> getJenis(){
        return jenis;
    }

    public void setKandang(List<Kandang> kandang){
        this.kandang = kandang;
    }

    public List<Kandang> getKandang(){
        return kandang;
    }

    public void setError(boolean error){
        this.error = error;
    }

    public boolean isError(){
        return error;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    @Override
    public String toString(){
        return
                "jenis{" +
                        "jenis = '" + jenis + '\'' +
                        ",error = '" + error + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}
