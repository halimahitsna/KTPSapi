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
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
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
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;

import java.util.ArrayList;
import java.util.Arrays;

import az.plainpie.PieView;
import az.plainpie.animation.PieAngleAnimation;
import id.sapi.ktp.aplikasiktpsapi.R;
import id.sapi.ktp.aplikasiktpsapi.activities.Config;
import id.sapi.ktp.aplikasiktpsapi.activities.MainActivity;
import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.JSONResponse;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.modal.Data;
import id.sapi.ktp.aplikasiktpsapi.modal.DataAdapter;
import id.sapi.ktp.aplikasiktpsapi.modal.Kandang;
import id.sapi.ktp.aplikasiktpsapi.modal.KandangAdapter;
import id.sapi.ktp.aplikasiktpsapi.modal.KandangSlide;
import id.sapi.ktp.aplikasiktpsapi.modal.LinePagerIndicator;
import id.sapi.ktp.aplikasiktpsapi.modal.Peternakan;
import id.sapi.ktp.aplikasiktpsapi.util.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Beranda extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;
    String regId, iduser;
    TextView nmpeternakan, tkandang,tid;
    private ArrayList<Peternakan> data;
    SharedPrefManager sharedPrefManager;
    private ArrayList<Kandang> kandangs;
    private KandangSlide adapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    Context mContext;
    ApiService mApiService;

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
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swiperefresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.card_recycle_view);
        sharedPrefManager = new SharedPrefManager(getActivity());
        SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);

        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        //mRecyclerView.setItemAnimator(new LinePagerIndicator());

        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mRecyclerView);
       // mRecyclerView.addItemDecoration(new LinePagerIndicator());
        mRecyclerView.setLayoutManager(layoutManager);

    loadJSON();
    }

    public void autoScroll(){
        final int speedScroll = 4000;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                if(count == adapter.getItemCount())
                    count = 0;
                mRecyclerView.smoothScrollToPosition(0);
//                    mRecyclerView.smoothScrollToPosition(count);
//                    handler.postDelayed( this,speedScroll);
                if(count < adapter.getItemCount()){
                    mRecyclerView.smoothScrollToPosition(++count);
                    handler.postDelayed( this,speedScroll);
                    }
                }
            };
        handler.postDelayed(runnable,speedScroll);
    }

    private void loadJSON() {
        iduser = sharedPrefManager.getSPId();
        swipeRefreshLayout.setRefreshing(true);
        //koneksi();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService request = retrofit.create(ApiService.class);
        Call<JSONResponse> call = request.getJSONKandang(iduser);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                swipeRefreshLayout.setRefreshing(false);
                JSONResponse jsonResponse = response.body();
                kandangs = new ArrayList<>(Arrays.asList(jsonResponse.getKandang()));
                adapter = new KandangSlide(getActivity(), kandangs);
                mRecyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
                autoScroll();
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void notif() {
        // Create NotificationManager
        final NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        // NotificationTargetActivity is the activity opened when user click notification.
        Intent intent = new Intent(getActivity(), MenuManajemen.class);
        Intent intentArr[] = {intent};

        PendingIntent pendingIntent = PendingIntent.getActivities(getActivity(), 0, intentArr, 0);

        // Create a new Notification instance.
        Notification notification = new Notification();

        // Set small icon.
        notification.icon = R.drawable.ic_notifications_black_24dp;

        // Set large icon.
        BitmapDrawable bitmapDrawable = (BitmapDrawable)getActivity().getDrawable(R.drawable.sapi2);
        Bitmap largeIconBitmap = bitmapDrawable.getBitmap();
        notification.largeIcon = largeIconBitmap;

        // Set flags.
        notification.flags = Notification.FLAG_ONGOING_EVENT;

        // Set send time.
        notification.when = System.currentTimeMillis();

        // Create and set notification content view.
        RemoteViews customRemoteViews = new RemoteViews(getActivity().getPackageName(), R.layout.custom_notifications);
        notification.contentView = customRemoteViews;

        // Set notification intent.
        notification.contentIntent = pendingIntent;

        notificationManager.notify(5, notification);
    }
}
