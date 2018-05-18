package id.sapi.ktp.aplikasiktpsapi.modal;

public class KondisiKandang {
    private String id_kandang;
    private String kandang;
    private String foto;
    private String jenis_sensor;
    private String input_date;
    private String suhu;
    private String kelembapan;
    private String gas;
    private String id_user;

    public String getKandang() {
        return kandang;
    }

    public void setKandang(String kandang) {
        this.kandang = kandang;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getId_kandang() {
        return id_kandang;
    }

    public void setId_kandang(String id_kandang) {
        this.id_kandang = id_kandang;
    }

    public String getJenis_sensor() {
        return jenis_sensor;
    }

    public void setJenis_sensor(String jenis_sensor) {
        this.jenis_sensor = jenis_sensor;
    }

    public String getInput_date() {
        return input_date;
    }

    public void setInput_date(String input_date) {
        this.input_date = input_date;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
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

    public String getGas() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }
}
