package id.sapi.ktp.aplikasiktpsapi.modal;

public class Data {
    private String id_sapi;
    private String id_jenis;
    private String id_indukan;
    private String id_pakan;
    private String id_kandang;
    private String jenis_kelamin;
    private String tgl_lahir;
    private String bobot_lahir;
    private String bobot_hidup;
    private String warna;
    private String umur;
    private String foto;
    private String input_date;
    private String update_date;
    private String jenis;
    private String harga_sapi;
    private String indukan;
    private String kandang;
    private String pakan;
    private String penyakit;

    public Data(String id_sapi, String id_jenis, String umur){
        this.id_sapi =id_sapi;
        this.id_jenis = id_jenis;
        this.umur = umur;
    }

    public String getId_sapi() {
        return id_sapi;
    }

    public void setId_sapi(String id_sapi) {
        this.id_sapi = id_sapi;
    }

    public String getId_jenis() {
        return id_jenis;
    }

    public void setId_jenis(String id_jenis) {
        this.id_jenis = id_jenis;
    }

    public String getId_indukan() {
        return id_indukan;
    }

    public void setId_indukan(String id_indukan) {
        this.id_indukan = id_indukan;
    }

    public String getId_pakan() {
        return id_pakan;
    }

    public void setId_pakan(String id_pakan) {
        this.id_pakan = id_pakan;
    }

    public String getId_kandang() {
        return id_kandang;
    }

    public void setId_kandang(String id_kandang) {
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

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
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

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getIndukan() {
        return indukan;
    }

    public void setIndukan(String indukan) {
        this.indukan = indukan;
    }

    public String getKandang() {
        return kandang;
    }

    public void setKandang(String kandang) {
        this.kandang = kandang;
    }

    public String getPakan() {
        return pakan;
    }

    public void setPakan(String pakan) {
        this.pakan = pakan;
    }

    public String getPenyakit() {
        return penyakit;
    }

    public void setPenyakit(String penyakit) {
        this.penyakit = penyakit;
    }

    public String getHarga_sapi() {
        return harga_sapi;
    }

    public void setHarga_sapi(String harga_sapi) {
        this.harga_sapi = harga_sapi;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }
}
