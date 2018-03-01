package id.sapi.ktp.aplikasiktpsapi.modal;

import id.sapi.ktp.aplikasiktpsapi.api.BaseResponse;

public class User extends BaseResponse {

    private int id;
    private String name;
    private String user;
    private String password;
    private String jk;
    private String peternakan;
    private String alamat;

    public User(String name, String user, String password) {
        this.name = name;
        this.user = user;
        this.password = password;
    }

    public User(int id, String name, String user){
        this.id =id;
        this.name = name;
        this.user = user;
    }

    public User(int id, String name, String user, String password, String jk, String peternakan, String alamat) {
        this.id =id;
        this.name = name;
        this.user = user;
        this.jk = jk;
        this.peternakan = peternakan;
        this.alamat =alamat;
        this.password=password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public String getPeternakan() {
        return peternakan;
    }

    public void setPeternakan(String peternakan) {
        this.peternakan = peternakan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
