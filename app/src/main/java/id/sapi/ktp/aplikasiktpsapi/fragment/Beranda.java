package id.sapi.ktp.aplikasiktpsapi.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Arrays;

import id.sapi.ktp.aplikasiktpsapi.R;
import id.sapi.ktp.aplikasiktpsapi.activities.Config;
import id.sapi.ktp.aplikasiktpsapi.activities.MainActivity;
import id.sapi.ktp.aplikasiktpsapi.activities.NotificationIntentService;
import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.JSONResponse;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.modal.InfoAdapter;
import id.sapi.ktp.aplikasiktpsapi.modal.Kandang;
import id.sapi.ktp.aplikasiktpsapi.modal.KandangSlide;
import id.sapi.ktp.aplikasiktpsapi.modal.Peternakan;
import id.sapi.ktp.aplikasiktpsapi.util.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Beranda extends Fragment {
    String iduser;
    TextView  tkoneksi;
    SharedPrefManager sharedPrefManager;
    private ArrayList<Kandang> kandangs;
    ViewPager viewPagerKandang, viewPagerInfo;
    private ViewPagerAdapter adapter;
    LinearLayout sliderDotspanel, sliderDotspanelinfo;
    private int dotscount;
    private int dotscount2;
    private ImageView[] dots;
    private ImageView[] dots2;
    AVLoadingIndicatorView circleload;
    ImageView navleft, navright;
    RelativeLayout Rsapi, Rkandang, Rjadwal, Rlap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_beranda, container, false);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("KTP Sapi");
        tkoneksi = (TextView)view.findViewById(R.id.txtkoneksi);
        circleload =(AVLoadingIndicatorView)view.findViewById(R.id.loading);
        circleload.setVisibility(View.INVISIBLE);

        tkoneksi.setVisibility(View.INVISIBLE);
        //swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swiperefresh);
        //mRecyclerView = (RecyclerView) view.findViewById(R.id.card_recycle_view);
        sharedPrefManager = new SharedPrefManager(getActivity());
        SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);
        Rsapi = (RelativeLayout)view.findViewById(R.id.sapi);
        Rkandang = (RelativeLayout)view.findViewById(R.id.kandang);
        Rjadwal = (RelativeLayout)view.findViewById(R.id.jdwl);
        Rlap = (RelativeLayout)view.findViewById(R.id.lap);
        sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);
        sliderDotspanelinfo =(LinearLayout)view.findViewById(R.id.SliderDotsinfo);
        viewPagerKandang = (ViewPager) view.findViewById(R.id.viewPagerkandang);
        viewPagerInfo = (ViewPager) view.findViewById(R.id.viewPagerinfo);
        navleft = (ImageView)view.findViewById(R.id.left_nav);
        navright = (ImageView)view.findViewById(R.id.right_nav);

        Rsapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuManajemen fragment2 = new MenuManajemen();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Rkandang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataKandang fragment3 = new DataKandang();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment3);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Rjadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JadwalMakan fragment3 = new JadwalMakan();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment3);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Rlap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Laporan fragment3 = new Laporan();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment3);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        navleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPagerInfo.arrowScroll(View.FOCUS_LEFT);
            }
        });
        navright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPagerInfo.arrowScroll(View.FOCUS_RIGHT);
            }
        });

        //infoSlide
        InfoAdapter adapter2 = new InfoAdapter(getActivity());
        viewPagerInfo.setAdapter(adapter2);

        //DotsInfo
        dotscount2 = adapter2.getCount();
        dots2 = new ImageView[dotscount2];

        for(int i = 0; i < dotscount2; i++){

            dots2[i] = new ImageView(getActivity());
            dots2[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.inactive_dot));

            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params2.setMargins(8, 0, 8, 0);

            sliderDotspanelinfo.addView(dots2[i], params2);

        }

        dots2[0].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.active_dot));

        viewPagerInfo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount2; i++){
                    dots2[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.inactive_dot));
                }

                dots2[position].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //Waktu dan tgl
        Time wk = new Time();
        wk.setToNow();
//        ttgl.setText(wk.monthDay +"-"+ wk.month +"-"+wk.year +"");
//        ttime.setText(wk.format("%k:%M:%S"));

        loadJSON();
        autoScroll();
    }

    public void autoScroll(){
        final int speedScroll = 4000;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                iduser = sharedPrefManager.getSPId();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(UtilsApi.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiService request = retrofit.create(ApiService.class);
                Call<JSONResponse> call = request.getJSONKandang(iduser);
                call.enqueue(new Callback<JSONResponse>() {
                    @Override
                    public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                        JSONResponse jsonResponse = response.body();
                        kandangs = new ArrayList<>(Arrays.asList(jsonResponse.getKandang()));
                        adapter = new ViewPagerAdapter(getActivity(), kandangs);
                        viewPagerKandang.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<JSONResponse> call, Throwable t) {
                        Log.d("Error", t.getMessage());
                    }
                });
                handler.postDelayed(this, speedScroll);
            }
        };
        handler.postDelayed(runnable,speedScroll);
    }

    private void loadJSON() {
        iduser = sharedPrefManager.getSPId();
        circleload.setVisibility(View.VISIBLE);
        circleload.smoothToShow();
        koneksi();
        iduser = sharedPrefManager.getSPId();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService request = retrofit.create(ApiService.class);
        Call<JSONResponse> call = request.getJSONKandang(iduser);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                circleload.smoothToHide();
                JSONResponse jsonResponse = response.body();
                kandangs = new ArrayList<>(Arrays.asList(jsonResponse.getKandang()));
                adapter = new ViewPagerAdapter(getActivity(), kandangs);
                if (adapter.getCount() != 0) {
                    viewPagerKandang.setAdapter(adapter);
                    dotscount = adapter.getCount();
                    dots = new ImageView[dotscount];

                    for (int i = 0; i < dotscount; i++) {

                        dots[i] = new ImageView(getActivity());
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.inactive_dot));

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                        params.setMargins(8, 0, 8, 0);

                        sliderDotspanel.addView(dots[i], params);

                    }

                    dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.active_dot));

                    viewPagerKandang.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {

                            for (int i = 0; i < dotscount; i++) {
                                dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.inactive_dot));
                            }

                            dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.active_dot));

                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                }else{
                    viewPagerKandang.setVisibility(View.GONE);
                    sliderDotspanel.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
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
            tkoneksi.setVisibility(View.VISIBLE);
            tkoneksi.setText("Tidak ada koneksi internet!");
            circleload.smoothToHide();
        }
    }
}
