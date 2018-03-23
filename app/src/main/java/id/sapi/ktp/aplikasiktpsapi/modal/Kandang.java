package id.sapi.ktp.aplikasiktpsapi.modal;

/**
 * Created by ASUS on 3/8/2018.
 */

public class Kandang {
    private String id_kandang;
    private String kandang;
    private String suhu;
    private String kelembapan;
    private String gas_amonia;
    private String id_user;

    public String getId_kandang() {
        return id_kandang;
    }

    public void setId_kandang(String id_kandang) {
        this.id_kandang = id_kandang;
    }

    public String getKandang() {
        return kandang;
    }

    public void setKandang(String kandang) {
        this.kandang = kandang;
    }

    public String getSuhu() {
        return suhu;
    }

    public void setSuhu(String suhu) {
        this.suhu = suhu;
    }

    public String getKelembapan() {
        return kelembapan;
    }

    public void setKelembapan(String kelembapan) {
        this.kelembapan = kelembapan;
    }

    public String getGas_amonia() {
        return gas_amonia;
    }

    public void setGas_amonia(String gas_amonia) {
        this.gas_amonia = gas_amonia;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
