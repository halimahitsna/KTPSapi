package id.sapi.ktp.aplikasiktpsapi.modal;

/**
 * Created by ASUS on 3/9/2018.
 */

public class Jenis {
    private String id_jenis;
    private String jenis;
    private String id_user;

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

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public void setAll(String id_jenis, String jenis) {
        setId_jenis(id_jenis);
        setJenis(jenis);
    }
}
