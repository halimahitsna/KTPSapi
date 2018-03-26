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
                            @Field("harga") String harga);

    //getdata
    @GET("get_data.php")
    Call<JSONResponse> getJSONSapi(@Query("id_user") Integer id_user);

    @GET("get_jenis.php")
    Call<JSONResponse> getJSONJenis(@Query("id_user") Integer id_user);

    @GET("get_kandang.php")
    Call<JSONResponse> getJSONKandang(@Query("id_user") Integer id_user);

    @GET("get_user.php")
    Call<JSONResponse>getUser(@Query("id_user") Integer id_user);

    @GET("get_peternakan.php")
    Call<JSONResponse>getPeternakan(@Query("id_user") Integer id_user);

    @GET("get_pakan.php")
    Call<JSONResponse>getPakan(@Query("id_user") Integer id_user);

    @GET("get_penyakit.php")
    Call<JSONResponse>getPenyakit(@Query("id_user") Integer id_user);

    @GET("get_indukan.php")
    Call<JSONResponse>getIndukan(@Query("id_user") Integer id_user);

    @GET("get_jadwal.php")
    Call<JSONResponse>getJadwal(@Query("id_user") Integer id_user);

    @GET("get_laporan.php")
    Call<JSONResponse>getLaporan(@Query("id_user") Integer id_user);



    //spinner
    @GET("get_jenis.php")
    Call<ResponseData> getJen(@Query("id_user") Integer id_user);

    @GET("get_kandang.php")
    Call<ResponseData> getKan(@Query("id_user") Integer id_user);

    @GET("get_indukan.php")
    Call<ResponseData> getInduk(@Query("id_user") Integer id_user);

    @GET("get_pakan.php")
    Call<ResponseData> getPa(@Query("id_user") Integer id_user);

    @GET("get_penyakit.php")
    Call<ResponseData> getPen(@Query("id_user") Integer id_user);

    //hapus
    @GET("hapus_data.php")
    Call<Result> hapusData(@Query("id_sapi") String id_sapi);

    @GET("hapus_jenis.php")
    Call<Result> hapusJenis(@Query("id_sapi") String id_sapi);




}
