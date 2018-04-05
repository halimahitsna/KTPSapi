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

    public void setIndukan(List<Indukan> indukan){
        this.indukan = indukan;
    }

    public List<Indukan> getIndukan(){
        return indukan;
    }

    public void setPakan(List<Pakan> pakan){
        this.pakan = pakan;
    }

    public List<Pakan> getPakan(){
        return pakan;
    }

    public void setPenyakit(List<Penyakit> penyakit){
        this.penyakit = penyakit;
    }

    public List<Penyakit> getPenyakit(){
        return penyakit;
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

}
