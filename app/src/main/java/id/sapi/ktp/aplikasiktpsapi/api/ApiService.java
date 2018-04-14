package id.sapi.ktp.aplikasiktpsapi.api;


import id.sapi.ktp.aplikasiktpsapi.modal.ProfilList;
import id.sapi.ktp.aplikasiktpsapi.modal.ResponseData;
import id.sapi.ktp.aplikasiktpsapi.modal.Result;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {

    @FormUrlEncoded
    @POST("daftar.php")
    Call<Result> createUser(
            @Field("name") String name,
            @Field("user") String user,
            @Field("password") String password);


    @FormUrlEncoded
    @POST("masuk.php")
    Call<ResponseBody> loginRequest(@Field("user") String user,
                                    @Field("password") String password);

    //User
    @GET
    Call<ProfilList> getJSONProfil(@Url String url);

    //insertdata
    @FormUrlEncoded
    @POST("insert_data.php")
    Call<Result> insertSapi(@Field("id_sapi") String id_sapi,
                            @Field("id_jenis") String id_jenis,
                            @Field("id_kandang") String id_kandang,
                            @Field("id_indukan") String id_indukan,
                            @Field("id_pakan") String id_pakan,
                            @Field("id_penyakit") String id_penyakit,
                            @Field("tgl_lahir") String tgl_lahir,
                            @Field("bobot_lahir") String bobot_lahir,
                            @Field("bobot_hidup") String bobot_hidup,
                            @Field("umur") String umur,
                            @Field("warna") String warna,
                            @Field("id_user") String id_user,
                            @Field("harga") String harga);
    @FormUrlEncoded
    @POST("insert_jenis.php")
    Call<Result> insertJenis(@Field("id_user") String id_user,
                             @Field("jenis") String jenis);
    @FormUrlEncoded
    @POST("insert_indukan.php")
    Call<Result> insertIndukan(@Field("id_user") String id_user,
                               @Field("indukan") String indukan);
    @FormUrlEncoded
    @POST("insert_kandang.php")
    Call<Result> insertKandang(@Field("id_user") String id_user,
                               @Field("kandang") String kandang,
                               @Field("batas_suhu") String bsuhu,
                               @Field("batas_kelembapan") String bkelembapan,
                               @Field("batas_gas") String bgas);
    @FormUrlEncoded
    @POST("insert_pakan.php")
    Call<Result> insertPakan(@Field("id_user") String id_user,
                             @Field("pakan") String pakan,
                             @Field("jumlah") String jumlah,
                             @Field("status") String status);
    @FormUrlEncoded
    @POST("insert_penyakit.php")
    Call<Result> insertPenyakit(@Field("id_user") String id_user,
                                @Field("penyakit") String penyakit);

    @FormUrlEncoded
    @POST("insert_peternakan.php")
    Call<Result> insertPeternakan(@Field("id_user") String id_user,
                                  @Field("peternakan") String peternakan);


    //getdata
    @GET("spinner.php")
    Call<JSONResponse> getData(@Query("id_user") String id_user);

    @GET("get_data.php")
    Call<JSONResponse> getJSONSapi(@Query("id_user") String id_user);

    @GET("get_jenis.php")
    Call<JSONResponse> getJSONJenis(@Query("id_user") String id_user);

    @GET("get_kandang.php")
    Call<JSONResponse> getJSONKandang(@Query("id_user") String id_user);

    @GET("get_user.php")
    Call<JSONResponse>getUser(@Query("id_user") String id_user);

    @GET("get_peternakan.php")
    Call<JSONResponse>getPeternakan(@Query("id_user") String id_user);

    @GET("get_pakan.php")
    Call<JSONResponse>getPakan(@Query("id_user") String id_user);

    @GET("get_penyakit.php")
    Call<JSONResponse>getPenyakit(@Query("id_user") String id_user);

    @GET("get_indukan.php")
    Call<JSONResponse>getIndukan(@Query("id_user") String id_user);

    @GET("get_jadwal.php")
    Call<JSONResponse>getJadwal(@Query("id_user") String id_user);

    @GET("get_laporan.php")
    Call<JSONResponse>getLaporan(@Query("id_user") String id_user);

    //spinner
    @GET("get_jenis.php")
    Call<ResponseData> getJen(@Query("id_user") String id_user);

    @GET("get_kandang.php")
    Call<ResponseData> getKan(@Query("id_user") String id_user);

    @GET("get_indukan.php")
    Call<ResponseData> getInduk(@Query("id_user") String id_user);

    @GET("get_pakan.php")
    Call<ResponseData> getPa(@Query("id_user") String id_user);

    @GET("get_penyakit.php")
    Call<ResponseData> getPen(@Query("id_user") String id_user);

    //hapus
    @GET("hapus_data.php")
    Call<Result> hapusData(@Query("id_sapi") String id_sapi);

    @GET("hapus_jenis.php")
    Call<Result> hapusJenis(@Query("id_jenis") String id_jenis);

    @GET("hapus_kandang.php")
    Call<Result> hapusKandang(@Query("id_kandang") String id_kandang);

    @GET("hapus_indukan.php")
    Call<Result> hapusIndukan(@Query("id_indukan") String id_indukan);

    @GET("hapus_pakan.php")
    Call<Result> hapusPakan(@Query("id_pakan") String id_pakan);

    @GET("hapus_penyakit.php")
    Call<Result> hapusPenyakit(@Query("id_penyakit") String id_penyakit);

    //UpdateData
    @FormUrlEncoded
    @POST("update_data.php")
    Call<Result> updateSapi(@Field("id_sapi") String id_sapi,
                            @Field("id_jenis") String id_jenis,
                            @Field("id_kandang") String id_kandang,
                            @Field("id_indukan") String id_indukan,
                            @Field("id_pakan") String id_pakan,
                            @Field("id_penyakit") String id_penyakit,
                            @Field("tgl_lahir") String tgl_lahir,
                            @Field("bobot_lahir") String bobot_lahir,
                            @Field("bobot_hidup") String bobot_hidup,
                            @Field("umur") String umur,
                            @Field("warna") String warna,
                            @Field("harga") String harga);
    @FormUrlEncoded
    @POST("update_jenis.php")
    Call<Result> updateJenis(@Field("id_jenis") String id_jenis,
                             @Field("jenis") String jenis);
    @FormUrlEncoded
    @POST("update_indukan.php")
    Call<Result> updateIndukan(@Field("id_indukan") String id_indukan,
                               @Field("indukan") String indukan);
    @FormUrlEncoded
    @POST("update_kandang.php")
    Call<Result> updateKandang(@Field("id_kandang") String id_kandang,
                               @Field("kandang") String kandang,
                               @Field("batas_suhu") String bsuhu,
                               @Field("batas_kelembapan") String bkelembapan,
                               @Field("batas_gas") String bgas);
    @FormUrlEncoded
    @POST("update_pakan.php")
    Call<Result> updatePakan(@Field("id_pakan") String id_pakan,
                             @Field("pakan") String pakan,
                             @Field("jumlah") String jumlah,
                             @Field("status") String status);
    @FormUrlEncoded
    @POST("update_penyakit.php")
    Call<Result> updatePenyakit(@Field("id_penyakit") String id_penyakit,
                                @Field("penyakit") String penyakit);

    @FormUrlEncoded
    @POST("update_peternakan.php")
    Call<Result> updatePeternakan(@Field("id_peternakan") String id_peternakan,
                                  @Field("peternakan") String peternakan);

    @FormUrlEncoded
    @POST("pengendali.php")
    Call<Result> updateStatus(@Field("id_kandang") String id_kandang,
                              @Field("status_lampu") String status_lampu,
                              @Field("status_kipas") String status_kipas);

    @FormUrlEncoded
    @POST("status_lampu.php")
    Call<Result> updateLampu(@Field("id_kandang") String id_kandang);

    //get data by their id
    @GET("jenis.php")
    Call<JSONResponse> getJenis(@Query("id_jenis") String id_jenis);

    //notif
    @GET("notification.php")
    Call<ResponseBody> sendNotif (@Query("firebase_id") String firebase_id,
                                 @Query("title") String title,
                                 @Query("body") String body);
}
