package id.sapi.ktp.aplikasiktpsapi.database;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

public class LaporanDB extends SugarRecord {
    @Unique
    String id_laporan;
    public String judul_laporan;
    public String isi_laporan;
    String tanggal;
    public String input_date;
    String update_date;
    String waktu;

    public LaporanDB(){}

    public LaporanDB( String judul_laporan, String isi_laporan, String input_date,String update_date ,String tanggal){
        this.judul_laporan = judul_laporan;
        this.isi_laporan = isi_laporan;
        this.tanggal = tanggal;
        this.input_date = input_date;
        this.input_date = update_date;

    }
    public LaporanDB(String id_laporan, String judul_laporan, String isi_laporan, String update_date){
        this.id_laporan = id_laporan;
        this.judul_laporan = judul_laporan;
        this.isi_laporan = isi_laporan;
        this.input_date = update_date;

    }

    public String getId_laporan() {
        return id_laporan;
    }

    public void setId_laporan(String id_laporan) {
        this.id_laporan = id_laporan;
    }

    public String getJudul_laporan() {
        return judul_laporan;
    }

    public void setJudul_laporan(String judul_laporan) {
        this.judul_laporan = judul_laporan;
    }

    public String getIsi_laporan() {
        return isi_laporan;
    }

    public void setIsi_laporan(String isi_laporan) {
        this.isi_laporan = isi_laporan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
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

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
