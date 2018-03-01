package id.sapi.ktp.aplikasiktpsapi.modal;

/**
 * Created by ASUS on 2/28/2018.
 */

public class Sapi {
    //SELECT `id_sapi`, `id_jenis`, `id_indukan`, `id_pakan`, `id_kandang`, `tgl_lahir`, `bobot_lahir`, `bobot_hidup`, `warna`, `umur`,
    // `foto`, `input_date`, `update_date` FROM `data_sapi` WHERE 1
    private int id_sapi;
    private int id_jenis;
    private int id_indukan;
    private int id_pakan;
    private int id_kandang;
    private String tgl_lahir;
    private String bobot_lahir;
    private String bobot_hidup;
    private String warna;
    private int umur;
    private String foto;
    private String input_date;
    private String update_date;

    public int getId_sapi() {
        return id_sapi;
    }

    public void setId_sapi(int id_sapi) {
        this.id_sapi = id_sapi;
    }

    public int getId_jenis() {
        return id_jenis;
    }

    public void setId_jenis(int id_jenis) {
        this.id_jenis = id_jenis;
    }

    public int getId_indukan() {
        return id_indukan;
    }

    public void setId_indukan(int id_indukan) {
        this.id_indukan = id_indukan;
    }

    public int getId_pakan() {
        return id_pakan;
    }

    public void setId_pakan(int id_pakan) {
        this.id_pakan = id_pakan;
    }

    public int getId_kandang() {
        return id_kandang;
    }

    public void setId_kandang(int id_kandang) {
        this.id_kandang = id_kandang;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getBobot_lahir() {
        return bobot_lahir;
    }

    public void setBobot_lahir(String bobot_lahir) {
        this.bobot_lahir = bobot_lahir;
    }

    public String getBobot_hidup() {
        return bobot_hidup;
    }

    public void setBobot_hidup(String bobot_hidup) {
        this.bobot_hidup = bobot_hidup;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public int getUmur() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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
}
