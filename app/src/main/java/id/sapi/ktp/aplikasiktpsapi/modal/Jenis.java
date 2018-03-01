package id.sapi.ktp.aplikasiktpsapi.modal;

/**
 * Created by ASUS on 3/1/2018.
 */

public class Jenis {
    private int id_kategori;
    private String nama_kategori;
    private String gambar1;

    public int getId_kategori() {
        return id_kategori;
    }
    public void setId_paket(int id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }
    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    public String getGambar1() {
        return gambar1;
    }
    public void setGambar1(String gambar1) {
        this.gambar1 = gambar1;
    }
}
