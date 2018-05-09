package id.sapi.ktp.aplikasiktpsapi.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;

import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import az.plainpie.PieView;
import az.plainpie.animation.PieAngleAnimation;
import id.sapi.ktp.aplikasiktpsapi.R;
import id.sapi.ktp.aplikasiktpsapi.database.LaporanDB;
import id.sapi.ktp.aplikasiktpsapi.edit.EditLaporan;
import id.sapi.ktp.aplikasiktpsapi.modal.LaporanAdapter;
import id.sapi.ktp.aplikasiktpsapi.tambah.TambahKandang;
import id.sapi.ktp.aplikasiktpsapi.tambah.TambahLaporan;
import id.sapi.ktp.aplikasiktpsapi.util.SharedPrefManager;
import im.dacer.androidcharts.LineView;
import me.ithebk.barchart.BarChart;
import me.ithebk.barchart.BarChartModel;

public class Laporan extends Fragment {
    LaporanAdapter adapter;
    RecyclerView recyclerView;
    AddFloatingActionButton add;
    SharedPrefManager sharedPrefManager;
    String iduser;
    SwipeRefreshLayout swipeRefreshLayout;
    long initialCount;
    List<LaporanDB> laporans = new ArrayList<>();

    int modifyPos = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View rootView = inflater.inflate(R.layout.activity_laporan, container, false);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Laporan");
        sharedPrefManager = new SharedPrefManager(getActivity());
        iduser = sharedPrefManager.getSPId().toString();

        recyclerView = (RecyclerView)view.findViewById(R.id.card_recycle_view);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        recyclerView.setLayoutManager(gridLayoutManager);

        initialCount = LaporanDB.count(LaporanDB.class);

        if (savedInstanceState != null)
            modifyPos = savedInstanceState.getInt("modify");


        if (initialCount >= 0) {

            laporans = LaporanDB.listAll(LaporanDB.class);

            adapter = new LaporanAdapter(getActivity(), laporans);
            recyclerView.setAdapter(adapter);

            if (laporans.isEmpty())
                Snackbar.make(recyclerView, "No laporan added.", Snackbar.LENGTH_LONG).show();

        }

        recyclerView.setLayoutManager(gridLayoutManager);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swiperefresh);
        add = (AddFloatingActionButton) view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getActivity(), TambahLaporan.class);
                //a.putExtra("id_user", iduser);
                startActivity(a);
            }
        });

    }

    public void loadJSON(){
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setRefreshing(false);

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

}
