package id.sapi.ktp.aplikasiktpsapi.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListKondisiKandang {
    @SerializedName("suhu")
    @Expose
    private ArrayList<KondisiKandang> suhus = new ArrayList<>();

    public ArrayList<KondisiKandang> getSuhus() {
        return suhus;
    }
}
