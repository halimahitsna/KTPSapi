package id.sapi.ktp.aplikasiktpsapi.activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Circle;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import id.sapi.ktp.aplikasiktpsapi.R;
import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.JSONResponse;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.database.UserDB;
import id.sapi.ktp.aplikasiktpsapi.edit.EditData;
import id.sapi.ktp.aplikasiktpsapi.fragment.Beranda;
import id.sapi.ktp.aplikasiktpsapi.fragment.DataKandang;
import id.sapi.ktp.aplikasiktpsapi.fragment.DataPakan;
import id.sapi.ktp.aplikasiktpsapi.fragment.DataPenyakit;
import id.sapi.ktp.aplikasiktpsapi.fragment.JadwalMakan;
import id.sapi.ktp.aplikasiktpsapi.fragment.Laporan;
import id.sapi.ktp.aplikasiktpsapi.fragment.MenuManajemen;
import id.sapi.ktp.aplikasiktpsapi.fragment.MonitoringKandang;
import id.sapi.ktp.aplikasiktpsapi.fragment.Pengaturan;
import id.sapi.ktp.aplikasiktpsapi.fragment.Profile;
import id.sapi.ktp.aplikasiktpsapi.fragment.Tentang;
import id.sapi.ktp.aplikasiktpsapi.modal.Peternakan;
import id.sapi.ktp.aplikasiktpsapi.modal.Result;
import id.sapi.ktp.aplikasiktpsapi.modal.User;
import id.sapi.ktp.aplikasiktpsapi.util.SharedPrefManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    EditText txtRegId, txtMessage;
    Toolbar toolbar;
    private ArrayList<User> data;
    public ArrayList<Peternakan> data1;
    SharedPrefManager sharedPrefManager;
    TextView nama, id_user, nmpeternakan;
    CircleImageView image;
    public static Integer id;
    String regId;
    Context mContext;
    ApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mApiService = UtilsApi.getAPIService();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //add this line to display menu1 when the activity is loaded
        displaySelectedScreen(R.id.menu_utama);
        sharedPrefManager = new SharedPrefManager(this);
        txtRegId = (EditText) findViewById(R.id.txt_reg_id);
        txtMessage = (EditText) findViewById(R.id.txt_push_message);
        txtMessage.setText(sharedPrefManager.getSPId());


        if (!sharedPrefManager.getSPSudahLogin()) {
            startActivity(new Intent(MainActivity.this, HalamanLogin.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
        loadHeader();

        //Notification
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                   Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
//                   Log.d("Push Notif", message);

                   txtMessage.setText(message);
                    Notif(message);

                }
            }
        };

        displayFirebaseRegId();


    }
    void Notif(String msg){
        RemoteViews expandedView = new RemoteViews(getApplicationContext().getPackageName(), R.layout.custom_notifications);
        expandedView.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
        expandedView.setTextViewText(R.id.notification_message,msg);
        // adding action to left button
        Intent leftIntent = new Intent(getApplicationContext(), NotificationIntentService.class);
        leftIntent.setAction("left");
        expandedView.setOnClickPendingIntent(R.id.left_button, PendingIntent.getService(getApplicationContext(), 0, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        // adding action to right button
        Intent rightIntent = new Intent(getApplicationContext(), NotificationIntentService.class);
        rightIntent.setAction("right");
        expandedView.setOnClickPendingIntent(R.id.right_button, PendingIntent.getService(getApplicationContext(), 1, rightIntent, PendingIntent.FLAG_UPDATE_CURRENT));

        RemoteViews collapsedView = new RemoteViews(getApplicationContext().getPackageName(), R.layout.view_collapsed_notification);
        collapsedView.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                // these are the three things a NotificationCompat.Builder object requires at a minimum
                .setSmallIcon(R.drawable.sapi2)
                .setContentTitle("Judul")
                .setContentText(msg)
                // notification will be dismissed when tapped
                .setAutoCancel(true)
                // tapping notification will open MainActivity
                .setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), MainActivity.class), 0))
                // setting the custom collapsed and expanded views
                .setCustomContentView(collapsedView)
                .setCustomBigContentView(expandedView)
                // setting style to DecoratedCustomViewStyle() is necessary for custom views to display
                .setStyle(new android.support.v7.app.NotificationCompat.DecoratedCustomViewStyle());

        // retrieves android.app.NotificationManager
        NotificationManager notificationManager = (android.app.NotificationManager)getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(90, builder.build());
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);
        updateRegid();
        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            txtRegId.setText(regId);
        else {
            txtRegId.setText("Firebase Reg Id is not received yet!");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
        loadHeader();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
    //end notif

    private void loadHeader() {
        koneksi();
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View header=navigationView.getHeaderView(0);
        id_user = (TextView)header.findViewById(R.id.tvid);
        nama = (TextView) header.findViewById(R.id.tvnama);
        image = (CircleImageView) header.findViewById(R.id.imageView);
        nmpeternakan = (TextView)header.findViewById(R.id.txpeternakan);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image.setImageResource(R.drawable.ic_camera_alt_black_24dp);

            }
        });

        id_user.setText(sharedPrefManager.getSPId());
        UserDB userDB = new UserDB(id_user.getText().toString(), nama.getText().toString());
        userDB.save();

        sharedPrefManager = new SharedPrefManager(getApplicationContext());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // get user data from session
        HashMap<String, String> user = sharedPrefManager.getUserDetails();
        ApiService request = retrofit.create(ApiService.class);

        id = Integer.valueOf(sharedPrefManager.getSPId());
        Call<JSONResponse> call = request.getUser(sharedPrefManager.getSPId());
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getUsers()));
                nama.setText(data.get(0).getUser());
                Picasso.with(MainActivity.this).load( data.get(0).getFoto()).placeholder(R.drawable.load).into(image);
                loadPeternakan();
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

    }

    private void loadPeternakan() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService request1 = retrofit.create(ApiService.class);
        //String id = sharedPrefManager.getSPId());
        Call<JSONResponse> call1 = request1.getPeternakan(sharedPrefManager.getSPId());
        call1.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call1, Response<JSONResponse> response) {
                JSONResponse jsonResponse1 = response.body();
                data1 = new ArrayList<>(Arrays.asList(jsonResponse1.getPeternakan()));
                nmpeternakan.setText(data1.get(0).getPeternakan());
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int itemId) {
        //creating fragment object
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        switch (itemId) {
            case R.id.menu_utama:
                fragment = new Beranda();
                break;
            case R.id.menu_manajemen:
                bundle.putString("firebase", regId);
                fragment = new MenuManajemen();
                fragment.setArguments(bundle);
                break;
            case R.id.riwayat_penyakit:
                fragment = new DataPenyakit();
                break;
            case R.id.kandang:
                fragment = new DataKandang();
                break;
            case R.id.pakan:
                fragment = new DataPakan();
                break;
            case R.id.menu_monitoring:
                fragment = new MonitoringKandang();
                break;
            case R.id.menu_jadwal:
                fragment = new JadwalMakan();
                break;
            case R.id.menu_laporan:
                fragment = new Laporan();
                break;
            case R.id.menu_pengaturan:
                fragment = new Pengaturan();
                break;
            case R.id.menu_profil:
                fragment = new Profile();
                break;
            case R.id.menu_tentang:
                fragment = new Tentang();
                break;
            case R.id.menu_keluar:
                sharedPrefManager.saveSPBoolean(SharedPrefManager.KEY_LOGIN, false);
                startActivity(new Intent(MainActivity.this, HalamanLogin.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void updateRegid() {
        String id = txtMessage.getText().toString().trim();
        String token = txtRegId.getText().toString().trim();

        koneksi();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService api = retrofit.create(ApiService.class);
        Call<Result> call = api.updateToken(regId.trim(), txtMessage.getText().toString().trim());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                if (value.equals("1")) {
                    Log.d("Pesan", message);
                } else {
                    Log.d("Pesan", message);
                }
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
       // onBackPressed();
    }

    private boolean adaInternet(){
        ConnectivityManager koneksi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }
    private void koneksi(){
        if(adaInternet()){
//            Toast.makeText(HalamanUtama.this, "Terhubung ke internet", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(MainActivity.this, "Tidak ada koneksi internet", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        loadHeader();
    }

}
