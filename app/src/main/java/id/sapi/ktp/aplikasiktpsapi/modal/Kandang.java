package id.sapi.ktp.aplikasiktpsapi.modal;

/**
 * Created by ASUS on 3/8/2018.
 */

public class Kandang {
    private String id_kandang;
    private String kandang;
    private String batas_suhu;
    private String batas_kelembapan;
    private String batas_gas;
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

    public String getBatas_suhu() {
        return batas_suhu;
    }

    public void setBatas_suhu(String batas_suhu) {
        this.batas_suhu = batas_suhu;
    }

    public String getBatas_kelembapan() {
        return batas_kelembapan;
    }

    public void setBatas_kelembapan(String batas_kelembapan) {
        this.batas_kelembapan = batas_kelembapan;
    }

    public String getBatas_gas() {
        return batas_gas;
    }

    public void setBatas_gas(String batas_gas) {
        this.batas_gas = batas_gas;
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
