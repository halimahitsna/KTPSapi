package id.sapi.ktp.aplikasiktpsapi.modal;

/**
 * Created by ASUS on 3/23/2018.
 */

public class Pakan {
    private String id_pakan;
    private String pakan;
    private String jumlah;
    private String status;
    private String input_date;
    private String update_date;
    private String id_user;

    public String getId_pakan() {
        return id_pakan;
    }

    public void setId_pakan(String id_pakan) {
        this.id_pakan = id_pakan;
    }

    public String getPakan() {
        return pakan;
    }

    public void setPakan(String pakan) {
        this.pakan = pakan;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInput_date() {
        return input_date;
    }

    public void setInput_date(String input_date) {
        this.input_date = input_date;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
