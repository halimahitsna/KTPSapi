package id.sapi.ktp.aplikasiktpsapi.api;


import id.sapi.ktp.aplikasiktpsapi.modal.ProfilList;
import id.sapi.ktp.aplikasiktpsapi.modal.Result;
import id.sapi.ktp.aplikasiktpsapi.modal.UserData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ApiService {

    @FormUrlEncoded
    @POST("daftar.php")
    Call<ResponseBody> createUser(
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
                              @Field("umur") String umur);


    @GET("get_data.php")
    Call<JSONResponse> getJSONSapi();

    @GET("getkategori")
    Call<JSONResponse> getJSONKategori();

    @GET("get_jenis.php")
    Call<JSONResponse> getJSONJenis();

    @GET("get_kandang.php")
    Call<JSONResponse> getJSONKandang();

    @GET("get_user.php?id={id_user}")
    Call<JSONResponse>getUser(@Path("id_user") String id_user);




}
