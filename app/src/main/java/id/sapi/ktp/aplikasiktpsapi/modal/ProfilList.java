package id.sapi.ktp.aplikasiktpsapi.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ASUS on 04/10/2017.
 */

public class ProfilList {
    @SerializedName("user") //nama JSON
    @Expose
    private ArrayList<Profil> profList = new ArrayList<>();

    public ArrayList<Profil> getProfList() {
        return profList;
    }

}
