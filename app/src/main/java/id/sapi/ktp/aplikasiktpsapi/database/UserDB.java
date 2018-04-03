package id.sapi.ktp.aplikasiktpsapi.database;

import com.orm.SugarRecord;

public class UserDB extends SugarRecord {
    public String id_user, username, name, password, foto;

    public UserDB(){}

    public UserDB( String id_user, String username, String name, String password, String foto){
        this.id_user = id_user;
        this.username = username;
        this.name = name;
        this.password = password;
        this.foto = foto;
    }

}
