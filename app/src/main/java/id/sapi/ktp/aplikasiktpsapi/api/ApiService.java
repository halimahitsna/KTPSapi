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
    Call<Result> createUser(
            @Field("name") String name,
            @Field("user") String user,
            @Field("password") String password);


    @FormUrlEncoded
    @POST("login.php")
    Call<UserData> loginRequest(@Field("name") String name,
                                @Field("password") String password);

    //User
    @GET
    Call<ProfilList> getJSONProfil(@Url String url);

    /*@Multipart
    @POST("retrofit_example/upload_image.php")
    Call<UpdateResponse> uploadFile(@Part MultipartBody.Part file,
                                    @Part("file") RequestBody name);

    @FormUrlEncoded
    @POST("update")
    Call<UpdateResponse> updateRequest(@Field("id_user") String id_user,
                                       @Field("fullname") String fullname,
                                       @Field("nama") String nama,
                                       @Field("email") String email,
                                       @Field("password") String password,
                                       @Field("foto") String foto);*/
    //Halaman Utama
    @GET("get_data.php")
    Call<JSONResponse> getJSONSapi();

    @GET("getkategori")
    Call<JSONResponse> getJSONJenis();




}
