package id.sapi.ktp.aplikasiktpsapi.modal;

/**
 * Created by ASUS on 3/26/2018.
 */

public class Jadwal {
    private String id_jadwal;
    private String id_kandang;
    private String id_sapi;
    private String waktu;
    private String id_pakan;
    private String status;
    private String input_date;
    private String update_date;
    private String id_user;
    private String hari;

    public String getId_jadwal() {
        return id_jadwal;
    }

    public void setId_jadwal(String id_jadwal) {
        this.id_jadwal = id_jadwal;
    }

    public String getId_kandang() {
        return id_kandang;
    }

    public void setId_kandang(String id_kandang) {
        this.id_kandang = id_kandang;
    }

    public String getId_sapi() {
        return id_sapi;
    }

    public void setId_sapi(String id_sapi) {
        this.id_sapi = id_sapi;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getId_pakan() {
        return id_pakan;
    }

    public void setId_pakan(String id_pakan) {
        this.id_pakan = id_pakan;
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

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }
}
