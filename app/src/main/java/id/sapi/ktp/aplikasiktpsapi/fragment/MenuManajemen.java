package id.sapi.ktp.aplikasiktpsapi.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Line;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import id.sapi.ktp.aplikasiktpsapi.activities.DataIndukan;
import id.sapi.ktp.aplikasiktpsapi.activities.DataJenis;
import id.sapi.ktp.aplikasiktpsapi.activities.Manajemen;
import id.sapi.ktp.aplikasiktpsapi.R;
import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.JSONResponse;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.database.UserDB;
import id.sapi.ktp.aplikasiktpsapi.modal.Data;
import id.sapi.ktp.aplikasiktpsapi.modal.DataAdapter;
import id.sapi.ktp.aplikasiktpsapi.modal.Indukan;
import id.sapi.ktp.aplikasiktpsapi.modal.Jenis;
import id.sapi.ktp.aplikasiktpsapi.modal.Sapi;
import id.sapi.ktp.aplikasiktpsapi.modal.SapiAdapter;
import id.sapi.ktp.aplikasiktpsapi.util.SharedPrefManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuManajemen extends Fragment {

    Toolbar toolbar;
    LinearLayout datasapi, jenis, indukan;
    SharedPrefManager sharedPrefManager;
    public TextView nama, idusers, jmlsapi, jmljenis, jmlindukan;
    public ImageView image;
    String iduser, idfirebase;
    Context mContext;
    ApiService mApiService;
    private ArrayList<Data> data1;
    private ArrayList<Jenis> data2;
    private ArrayList<Indukan> data3;
    Activity context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments

        return inflater.inflate(R.layout.activity_menu_manajemen, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Data Sapi");

        mContext = getContext();
        mApiService = UtilsApi.getAPIService();
        idusers = (TextView)view.findViewById(R.id.idu);
        datasapi = (LinearLayout) view.findViewById(R.id.sapi);
        jenis = (LinearLayout) view.findViewById(R.id.jenis);
        indukan = (LinearLayout) view.findViewById(R.id.indukan);
        jmlsapi = (TextView)view.findViewById(R.id.jumlah);
        jmljenis = (TextView)view.findViewById(R.id.jumlahjenis);
        jmlindukan = (TextView)view.findViewById(R.id.jumlahinduk);
        Toast.makeText(getActivity(),"" +getArguments().getString("firebase"), Toast.LENGTH_SHORT ).show();

        sharedPrefManager = new SharedPrefManager(getActivity());
        iduser = sharedPrefManager.getSPId();
        JumlahSapi();
        JumlahJenis();
        JumlahInduk();
        jenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(getActivity(), DataJenis.class);
                j.putExtra("id_user",iduser);
                startActivity(j);
            }
        });
        indukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DataIndukan.class);
                i.putExtra("id_user",iduser);
                startActivity(i);
            }
        });
        //  datasapi.setText(id);
        datasapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
                Intent s = new Intent(getActivity(), Manajemen.class);
                s.putExtra("id_user",iduser);
                startActivity(s);
            }
        });
        sendNotification();
    }

    private void sendNotification(){
        idfirebase = getArguments().getString("firebase");
        mApiService.sendNotif(idfirebase.trim(), "judul", "pesan")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            try{
                                JSONObject jsonRESULT = new JSONObject(response.body().string());
                                if(jsonRESULT.getString("success").equals(1)){
                                    Toast.makeText(mContext, "Berhasil notif", Toast.LENGTH_SHORT).show();
                                }else {
                                    String error_message= jsonRESULT.getString("error_msg");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            }catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else {
                            Toast.makeText(mContext, "Error notif", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "OnFailure: ERROR > " +t.toString());
                    }
                });
    }

    private void JumlahSapi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService request = retrofit.create(ApiService.class);
        Call<JSONResponse> call = request.getData(iduser);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                data1 = new ArrayList<>(Arrays.asList(jsonResponse.getData()));
                jmlsapi.setText("Jumlah Sapi : " +data1.size());
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    private void JumlahJenis() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService request = retrofit.create(ApiService.class);
        Call<JSONResponse> call = request.getJSONJenis(iduser);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                data2 = new ArrayList<>(Arrays.asList(jsonResponse.getJenis()));
                jmljenis.setText("Jumlah Jenis : " +data2.size());
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
    private void JumlahInduk() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService request = retrofit.create(ApiService.class);
        Call<JSONResponse> call = request.getIndukan(iduser);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                data3 = new ArrayList<>(Arrays.asList(jsonResponse.getIndukan()));
                jmlindukan.setText("Jumlah Indukan : " +data3.size());
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

}
