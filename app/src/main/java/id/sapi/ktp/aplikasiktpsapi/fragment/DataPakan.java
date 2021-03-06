package id.sapi.ktp.aplikasiktpsapi.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.getbase.floatingactionbutton.AddFloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

import id.sapi.ktp.aplikasiktpsapi.R;
import id.sapi.ktp.aplikasiktpsapi.tambah.TambahPakan;
import id.sapi.ktp.aplikasiktpsapi.tambah.TambahPenyakit;
import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.JSONResponse;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.modal.Pakan;
import id.sapi.ktp.aplikasiktpsapi.modal.PakanAdapter;
import id.sapi.ktp.aplikasiktpsapi.util.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataPakan extends Fragment {

    Toolbar toolbar;
    AddFloatingActionButton btnadd;
    private TextView tkoneksi;
    private RecyclerView recyclerView;
    private ArrayList<Pakan> data;
    private PakanAdapter adapter;
    SharedPrefManager sharedPrefManager;
    String iduser;
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_data_pakan, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Data Pakan");

        sharedPrefManager = new SharedPrefManager(getActivity());
        iduser = sharedPrefManager.getSPId();

        tkoneksi = (TextView)view.findViewById(R.id.txtkoneksi);
        tkoneksi.setVisibility(View.INVISIBLE);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swiperefresh);
        initViews();

        btnadd = (AddFloatingActionButton) view.findViewById(R.id.add);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getActivity(), TambahPakan.class);
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService request = retrofit.create(ApiService.class);
        Call<JSONResponse> call = request.getPakan(iduser);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                swipeRefreshLayout.setRefreshing(false);
                JSONResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getPakan()));
                adapter = new PakanAdapter(getActivity(), data);
                if(adapter.getItemCount() !=0) {
                    tkoneksi.setVisibility(View.INVISIBLE);
                    recyclerView.setAdapter(adapter);
                }else {
                    tkoneksi.setVisibility(View.VISIBLE);
                    tkoneksi.setText("Belum Ada Data");
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Log.d("Error", t.getMessage());
            }
        });
    }
    private boolean adaInternet(){
        ConnectivityManager koneksi = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        loadJSON();
    }

    @Override
    public void onResume(){
        super.onResume();
        tkoneksi.setVisibility(View.INVISIBLE);
        loadJSON();
    }

    private void koneksi(){
        if(adaInternet()){
//            Toast.makeText(HalamanUtama.this, "Terhubung ke internet", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(), "Tidak ada koneksi internet", Toast.LENGTH_LONG).show();
            tkoneksi.setVisibility(View.VISIBLE);
            tkoneksi.setText("Tidak ada koneksi internet!");
        }
    }
}

