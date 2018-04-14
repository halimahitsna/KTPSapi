package id.sapi.ktp.aplikasiktpsapi.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import id.sapi.ktp.aplikasiktpsapi.modal.Data;
import id.sapi.ktp.aplikasiktpsapi.modal.Indukan;
import id.sapi.ktp.aplikasiktpsapi.modal.Jadwal;
import id.sapi.ktp.aplikasiktpsapi.modal.Jenis;
import id.sapi.ktp.aplikasiktpsapi.modal.Kandang;
import id.sapi.ktp.aplikasiktpsapi.modal.Pakan;
import id.sapi.ktp.aplikasiktpsapi.modal.Penyakit;
import id.sapi.ktp.aplikasiktpsapi.modal.Peternakan;
import id.sapi.ktp.aplikasiktpsapi.modal.Sapi;
import id.sapi.ktp.aplikasiktpsapi.modal.User;

/**
 * Created by hali on 25/08/2017.
 */

public class JSONResponse {
    private Sapi[] sapi;
    private Kandang[]kandang;
    private Jenis[]jenis;
    private Peternakan[]peternakan;
    private User[]users;
    private Pakan[]pakan;
    private Penyakit[]penyakit;
    private Indukan[]indukan;
    private Jadwal[]jadwal;
    private Data[]data;

    public Sapi[] getSapi(){
        return sapi;
    }
    public Kandang[]getKandang(){return kandang;}
    public Jenis[]getJenis(){return jenis;}
    public Peternakan[]getPeternakan(){return peternakan;}
    public User[]getUsers(){return users;}
    public Pakan[]getPakan(){return pakan;}
    public Penyakit[]getPenyakit(){return penyakit;}
    public Indukan[]getIndukan(){return indukan;}
    public Jadwal[]getJadwal(){return jadwal;}
    public Data[]getData(){return data;}

}
