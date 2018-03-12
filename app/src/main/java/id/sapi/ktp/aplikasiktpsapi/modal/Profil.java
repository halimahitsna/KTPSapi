package id.sapi.ktp.aplikasiktpsapi.modal;

/**
 * Created by ASUS on 04/10/2017.
 */

public class Profil {

    String id_user, name, user, password, foto, jenis_kelamin, input_date, update_date;

    public Profil(String id_user, String name, String user, String jenis_kelamin, String password, String foto) {
        this.id_user = id_user;
        this.name = name;
        this.user = user;
        this.jenis_kelamin = jenis_kelamin;
        this.password = password;
        this.foto = foto;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
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
