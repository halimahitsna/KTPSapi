package id.sapi.ktp.aplikasiktpsapi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.JSONResponse;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.modal.Kategori;
import id.sapi.ktp.aplikasiktpsapi.modal.KategoriAdapter;
import id.sapi.ktp.aplikasiktpsapi.modal.Profil;
import id.sapi.ktp.aplikasiktpsapi.modal.ProfilList;
import id.sapi.ktp.aplikasiktpsapi.modal.Sapi;
import id.sapi.ktp.aplikasiktpsapi.modal.SapiAdapter;
import id.sapi.ktp.aplikasiktpsapi.util.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuManajemen extends Fragment {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    private TextView textView;
    Button datasapi, jenis, indukan, kandang, pakan, penyakit;
    SharedPrefManager sharedPrefManager;
    public TextView nama;
    public ImageView image;
    ArrayList<Profil> profilList;
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
        getActivity().setTitle("Manajemen Data");
        //Bundle bundle=getArguments();
        //datasapi.setText(String.valueOf(bundle.getString("id_user")));
        datasapi = (Button)view.findViewById(R.id.sapi);
        jenis = (Button)view.findViewById(R.id.jenis);
        kandang = (Button)view.findViewById(R.id.kandang);
        indukan = (Button)view.findViewById(R.id.indukan);
        pakan = (Button)view.findViewById(R.id.pakan);
        penyakit = (Button)view.findViewById(R.id.penyakit);

        jenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(getActivity(), DataJenis.class);
                //  sapi.putExtra("id_user",context.getIntent().getStringExtra("id_user"));
                startActivity(j);
            }
        });
        kandang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(getActivity(), DataKandang.class);
                //    sapi.putExtra("id_user", context.getIntent().getStringExtra("id_user"));
                startActivity(k);
            }
        });
        indukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DataIndukan.class);
                //    sapi.putExtra("id_user", context.getIntent().getStringExtra("id_user"));
                startActivity(i);
            }
        });
        penyakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(getActivity(), DataPenyakit.class);
                //   sapi.putExtra("id_user", context.getIntent().getStringExtra("id_user"));
                startActivity(p);
            }
        });
        pakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(getActivity(), DataPakan.class);
                //     sapi.putExtra("id_user", context.getIntent().getStringExtra("id_user"));
                startActivity(p);
            }
        });

        //  datasapi.setText(id);
        datasapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s = new Intent(getActivity(), Manajemen.class);
                //   sapi.putExtra("id_user", context.getIntent().getStringExtra("id_user"));
                startActivity(s);
            }
        });
    }

}
