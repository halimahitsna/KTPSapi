package id.sapi.ktp.aplikasiktpsapi.modal;

/**
 * Created by ASUS on 3/1/2018.
 */

public class Jenis {
    private String id_jenis;
    private String jenis;
    public Jenis() {
    }


    public Jenis(String id_jenis, String jenis) {
        this.id_jenis = id_jenis;
        this.jenis = jenis;
    }
    public String getId_jenis() {
        return id_jenis;
    }

    public void setId_jenis(String id_jenis) {
        this.id_jenis = id_jenis;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }
}
