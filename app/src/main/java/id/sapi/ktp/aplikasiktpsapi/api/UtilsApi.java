package id.sapi.ktp.aplikasiktpsapi.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UtilsApi {


    public static final String BASE_URL = "http://192.168.1.10:8080/ktpsapi/";
    private static Retrofit retrofit = null;
    // variable to hold context
    private static Context context;


    public static Retrofit getClient() {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new Connectivity(context))
//                .build();
        if (retrofit==null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;

    }

//    // 10.0.2.2 ini adalah localhost.
//    public static final String BASE_URL_API = "http://www.ktpsapi.id/UjianOnline/api/";
//
//    // Mendeklarasikan Interface BaseApiService
//    public static BaseApiService getAPIService(){
//        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
//    }
}
