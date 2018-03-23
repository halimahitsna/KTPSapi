package id.sapi.ktp.aplikasiktpsapi.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import id.sapi.ktp.aplikasiktpsapi.modal.Jenis;
import id.sapi.ktp.aplikasiktpsapi.modal.Kandang;
import id.sapi.ktp.aplikasiktpsapi.modal.Sapi;
import id.sapi.ktp.aplikasiktpsapi.modal.User;

/**
 * Created by hali on 25/08/2017.
 */

public class JSONResponse {
    private Sapi[] sapi;
    private Kandang[]kandang;
    private Jenis[]jenis;
    private User[]users;

    public Sapi[] getSapi(){
        return sapi;
    }
    public Kandang[]getKandang(){return kandang;}
    public Jenis[]getJenis(){return jenis;}
    public User[]getUsers(){return users;}

}
