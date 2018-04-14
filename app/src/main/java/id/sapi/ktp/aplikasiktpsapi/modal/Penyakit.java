package id.sapi.ktp.aplikasiktpsapi.modal;

/**
 * Created by ASUS on 3/23/2018.
 */

public class Penyakit {
    private String id_penyakit;
    private String penyakit;
    private String input_date;
    private String update_date;
    private String id_user;

    public String getId_penyakit() {
        return id_penyakit;
    }

    public void setId_penyakit(String id_penyakit) {
        this.id_penyakit = id_penyakit;
    }

    public String getPenyakit() {
        return penyakit;
    }

    public void setPenyakit(String penyakit) {
        this.penyakit = penyakit;
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

    public void setAll(String id_penyakit, String penyakit) {
        setId_penyakit(id_penyakit);
        setPenyakit(penyakit);
    }
}
