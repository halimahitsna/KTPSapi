package id.sapi.ktp.aplikasiktpsapi.database;

import com.orm.SugarRecord;

public class JadwalDB extends SugarRecord {
    String id_jadwal;
    String judul;
    String id_kandang;
    String id_sapi;
    String id_pakan;
    String waktu;
    String tanggal;
    String mRepeat;
    String mRepeatNo;
    String mRepeatType;
    String mActive;

    public JadwalDB(){}

    public JadwalDB(String id_jadwal, String judul, String id_kandang, String id_sapi, String id_pakan, String waktu, String tanggal, String Repeat, String RepeatNo, String RepeatType, String Active ){
        this.id_jadwal = id_jadwal;
        this.id_kandang = id_kandang;
        this.id_sapi = id_sapi;
        this.id_pakan = id_pakan;
        this.waktu = waktu;
        this.tanggal = tanggal;
        this.mRepeat = Repeat;
        this.mRepeatNo = RepeatNo;
        this.mRepeatType = RepeatType;
        this.mActive = Active;
    }

    public String getId_jadwal() {
        return id_jadwal;
    }

    public void setId_jadwal(String id_jadwal) {
        this.id_jadwal = id_jadwal;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getId_kandang() {
        return id_kandang;
    }

    public void setId_kandang(String id_kandang) {
        this.id_kandang = id_kandang;
    }

    public String getId_sapi() {
        return id_sapi;
    }

    public void setId_sapi(String id_sapi) {
        this.id_sapi = id_sapi;
    }

    public String getId_pakan() {
        return id_pakan;
    }

    public void setId_pakan(String id_pakan) {
        this.id_pakan = id_pakan;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getmRepeat() {
        return mRepeat;
    }

    public void setmRepeat(String mRepeat) {
        this.mRepeat = mRepeat;
    }

    public String getmRepeatNo() {
        return mRepeatNo;
    }

    public void setmRepeatNo(String mRepeatNo) {
        this.mRepeatNo = mRepeatNo;
    }

    public String getmRepeatType() {
        return mRepeatType;
    }

    public void setmRepeatType(String mRepeatType) {
        this.mRepeatType = mRepeatType;
    }

    public String getmActive() {
        return mActive;
    }

    public void setmActive(String mActive) {
        this.mActive = mActive;
    }
}
