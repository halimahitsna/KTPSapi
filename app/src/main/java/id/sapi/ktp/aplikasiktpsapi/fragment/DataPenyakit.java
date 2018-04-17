package id.sapi.ktp.aplikasiktpsapi.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import com.getbase.floatingactionbutton.AddFloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

import id.sapi.ktp.aplikasiktpsapi.R;
import id.sapi.ktp.aplikasiktpsapi.tambah.TambahPenyakit;
import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.JSONResponse;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.modal.Penyakit;
import id.sapi.ktp.aplikasiktpsapi.modal.PenyakitAdapter;
import id.sapi.ktp.aplikasiktpsapi.util.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataPenyakit extends Fragment {

    Toolbar toolbar;
    ActionBar actionBar;
    AddFloatingActionButton btnadd;
    private TextView textView;
    private RecyclerView recyclerView;
    private ArrayList<Penyakit> data;
    private PenyakitAdapter adapter;
    SharedPrefManager sharedPrefManager;
    String iduser;
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_data_penyakit, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Riwayat Penyakit");

        sharedPrefManager = new SharedPrefManager(getActivity());
        iduser = sharedPrefManager.getSPId();

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swiperefresh);
        initViews();

        AddFloatingActionButton add = (AddFloatingActionButton) view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getActivity(), TambahPenyakit.class);
                a.putExtra("id_user", iduser);
                startActivity(a);
            }
        });
    }
    private void initViews() {
        recyclerView = (RecyclerView)getActivity().findViewById (R.id.card_recycle_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);

        loadJSON();
    }

    private void loadJSON() {
        swipeRefreshLayout.setRefreshing(true);
        koneksi();
        iduser = sharedPrefManager.getSPId();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService request = retrofit.create(ApiService.class);
        Call<JSONResponse> call = request.getPenyakit(iduser);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                swipeRefreshLayout.setRefreshing(false);
                JSONResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getPenyakit()));
                adapter = new PenyakitAdapter(getActivity(), data);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Log.d("Error", t.getMessage());
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        loadJSON();
    }

    @Override
    public void onResume(){
        super.onResume();
        loadJSON();
    }

    private boolean adaInternet(){
        ConnectivityManager koneksi = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }
    private void koneksi(){
        if(adaInternet()){
//            Toast.makeText(HalamanUtama.this, "Terhubung ke internet", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(), "Tidak ada koneksi internet", Toast.LENGTH_LONG).show();
        }
    }
}