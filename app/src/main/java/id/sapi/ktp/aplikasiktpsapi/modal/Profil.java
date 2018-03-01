package id.sapi.ktp.aplikasiktpsapi.modal;

/**
 * Created by ASUS on 04/10/2017.
 */

public class Profil {
    String id_user, name, user, password, foto, jk;

    public Profil(String id_user, String name, String user, String jk, String password, String foto) {
        this.id_user = id_user;
        this.name = name;
        this.user = user;
        this.jk = jk;
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

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }
}
