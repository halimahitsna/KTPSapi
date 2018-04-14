package id.sapi.ktp.aplikasiktpsapi.modal;

/**
 * Created by ASUS on 3/23/2018.
 */

public class Indukan {
    private String id_indukan;
    private String indukan;
    private String input_date;
    private String update_date;
    private String id_user;

    public String getId_indukan() {
        return id_indukan;
    }

    public void setId_indukan(String id_indukan) {
        this.id_indukan = id_indukan;
    }

    public String getIndukan() {
        return indukan;
    }

    public void setIndukan(String indukan) {
        this.indukan = indukan;
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

    public void setAll(String id_indukan, String indukan) {
        setId_indukan(id_indukan);
        setIndukan(indukan);
    }
}
