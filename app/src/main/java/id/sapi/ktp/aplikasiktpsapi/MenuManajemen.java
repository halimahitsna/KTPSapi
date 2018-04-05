package id.sapi.ktp.aplikasiktpsapi;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import id.sapi.ktp.aplikasiktpsapi.database.UserDB;
import id.sapi.ktp.aplikasiktpsapi.util.SharedPrefManager;

public class MenuManajemen extends Fragment {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    private TextView textView;
    Button datasapi, jenis, indukan, kandang, pakan, penyakit;
    SharedPrefManager sharedPrefManager;
    public TextView nama, iduser;
    public ImageView image;
    List<UserDB> userDBList;

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
        iduser = (TextView)view.findViewById(R.id.idu);
        datasapi = (Button)view.findViewById(R.id.sapi);
        jenis = (Button)view.findViewById(R.id.jenis);
        kandang = (Button)view.findViewById(R.id.kandang);
        indukan = (Button)view.findViewById(R.id.indukan);
        pakan = (Button)view.findViewById(R.id.pakan);
        penyakit = (Button)view.findViewById(R.id.penyakit);
        sharedPrefManager = new SharedPrefManager(getActivity());
        long count = UserDB.count(UserDB.class);

        if(count>0) {
            userDBList = UserDB.listAll(UserDB.class);
            if (userDBList != null) {
                iduser.setText(userDBList.get(0).id_user);
            } else {
                Toast.makeText(getActivity(), "Data User Kosong", Toast.LENGTH_SHORT).show();
            }
        }
        //iduser.setText(sharedPrefManager.getSPId());

        jenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(getActivity(), DataJenis.class);
                j.putExtra("id_user",iduser.getText().toString());
                startActivity(j);
            }
        });
        kandang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(getActivity(), DataKandang.class);
                k.putExtra("id_user",iduser.getText().toString());
                startActivity(k);
            }
        });
        indukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DataIndukan.class);
                i.putExtra("id_user",iduser.getText().toString());
                startActivity(i);
            }
        });
        penyakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pe = new Intent(getActivity(), DataPenyakit.class);
                pe.putExtra("id_user",iduser.getText().toString());
                startActivity(pe);
            }
        });
        pakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pa = new Intent(getActivity(), DataPakan.class);
                pa.putExtra("id_user",iduser.getText().toString());
                startActivity(pa);
            }
        });

        //  datasapi.setText(id);
        datasapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s = new Intent(getActivity(), Manajemen.class);
                s.putExtra("id_user",iduser.getText().toString());
                startActivity(s);
            }
        });
    }

}
