package id.sapi.ktp.aplikasiktpsapi;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

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
import id.sapi.ktp.aplikasiktpsapi.modal.User;
import id.sapi.ktp.aplikasiktpsapi.util.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.support.v7.app.ActionBar;

/**
 * Created by NgocTri on 4/9/2016.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    private TextView textView;
    private RecyclerView recyclerView;
    private ArrayList<User> data;
    private SapiAdapter adapter;
    private ArrayList<Kategori> data1;
    private KategoriAdapter adapter1;
    SharedPrefManager sharedPrefManager;
    public TextView nama, id_user;
    public ImageView image;
    ArrayList<Profil> profilList;
    public static Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtRegId = (TextView) findViewById(R.id.txt_reg_id);
        txtMessage = (TextView) findViewById(R.id.txt_push_message);

        sharedPrefManager = new SharedPrefManager(this);
        if (!sharedPrefManager.getSPSudahLogin()) {
            startActivity(new Intent(MainActivity.this, HalamanLogin.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
loadHeader();
        //Drawerbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);


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

                    txtMessage.setText(message);
                    Notif(message);

                }
            }
        };

        displayFirebaseRegId();
    }
    void Notif(String msg){
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker("")
                .setSmallIcon(R.drawable.ic_folder_black_24dp)
                .setContentTitle("")
                .setContentText(msg)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            txtRegId.setText("Firebase Reg Id: " + regId);
        else
            txtRegId.setText("Firebase Reg Id is not received yet!");
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
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
    //end notif

    private void loadHeader() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View header=navigationView.getHeaderView(0);
        id_user = (TextView)header.findViewById(R.id.tvid);
        nama = (TextView) header.findViewById(R.id.tvnama);
        image = (ImageView) header.findViewById(R.id.imageView);
        id_user.setText(sharedPrefManager.getSPId());
        //nama.setText(sharedPrefManager.getSPId());
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }
        setupNavigationDrawerContent(navigationView);

        sharedPrefManager = new SharedPrefManager(getApplicationContext());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // get user data from session
        HashMap<String, String> user = sharedPrefManager.getUserDetails();
        ApiService request = retrofit.create(ApiService.class);
        String id_user = user.get(sharedPrefManager.getSPId());
        id = Integer.valueOf(sharedPrefManager.getSPId());
        Call<JSONResponse> call = request.getUser(id);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getUsers()));
                nama.setText(data.get(0).getUser());
                //image.setImageResource(0);
                Picasso.with(MainActivity.this).load(data.get(0).getFoto()).resize(100, 100)
                        .into(image);

            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menu_utama:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent a = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(a);
                                return true;
                            case R.id.menu_manajemen:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent b = new Intent(MainActivity.this, Manajemen.class);
                                startActivity(b);
                                return true;
                            case R.id.menu_monitoring:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                /*sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                                startActivity(new Intent(HalamanUtama.this, SignIn.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                finish();*/
                                return true;
                            case R.id.menu_jadwal:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                /*Intent c = new Intent(HalamanUtama.this, Setting.class);
                                startActivity(c);*/
                                return true;
                            case R.id.menu_laporan:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                /*Intent d = new Intent(HalamanUtama.this, Bantuan.class);
                                startActivity(d);*/
                                return true;
                            case R.id.menu_keluar:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                sharedPrefManager.saveSPBoolean(SharedPrefManager.KEY_LOGIN, false);
                                startActivity(new Intent(MainActivity.this, HalamanLogin.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                finish();
                                return true;
                        }
                        return true;
                    }
                });
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
}
