package id.sapi.ktp.aplikasiktpsapi;

import android.app.Application;

import com.orm.SugarRecord;

public class SapiData extends SugarRecord {
    String id_user;
    String username;
    String name;
    String password;
    String foto;

    public SapiData(){

    }

    public SapiData(String id_user, String username, String name, String password, String foto){
        this.id_user = id_user;
        this.username = username;
        this.name = name;
        this.password = password;
        this.foto = foto;
    }
    public SapiData(String id_user, String username, String foto){
        this.id_user = id_user;
        this.username = username;
        this.foto = foto;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
