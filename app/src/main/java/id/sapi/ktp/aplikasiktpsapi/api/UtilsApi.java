package id.sapi.ktp.aplikasiktpsapi.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UtilsApi {


    public static final String BASE_URL = "http://192.168.1.15:8080/ktpsapi/";

        public static ApiService getAPIService(){
            return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
        }

    }

