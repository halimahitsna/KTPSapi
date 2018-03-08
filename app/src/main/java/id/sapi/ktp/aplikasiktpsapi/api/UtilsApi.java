package id.sapi.ktp.aplikasiktpsapi.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UtilsApi {


    public static final String BASE_URL = "http://ktpsapi.id/android/ktpsapi/";
    private static Retrofit retrofit = null;
    // variable to hold context
    private static Context context;


    public static Retrofit getClient() {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new Connectivity(context))
//                .build();
        if (retrofit == null) {
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
}

