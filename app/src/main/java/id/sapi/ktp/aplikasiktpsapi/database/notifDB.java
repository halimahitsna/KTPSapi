package id.sapi.ktp.aplikasiktpsapi.database;

import com.orm.SugarRecord;

public class notifDB extends SugarRecord {
    public String id_user, firebase_id;

    public notifDB(){}

    public notifDB( String id_user,  String firebase_id){
        this.id_user = id_user;
        this.firebase_id = firebase_id;
    }
}
