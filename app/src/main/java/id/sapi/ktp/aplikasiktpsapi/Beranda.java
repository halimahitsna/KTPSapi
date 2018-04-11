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
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Arrays;

import az.plainpie.PieView;
import az.plainpie.animation.PieAngleAnimation;
import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.JSONResponse;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.modal.Jenis;
import id.sapi.ktp.aplikasiktpsapi.modal.JenisAdapter;
import id.sapi.ktp.aplikasiktpsapi.modal.Kandang;
import id.sapi.ktp.aplikasiktpsapi.modal.KandangAdapter;
import id.sapi.ktp.aplikasiktpsapi.modal.KandangSlide;
import id.sapi.ktp.aplikasiktpsapi.modal.MonitoringAdapter;
import id.sapi.ktp.aplikasiktpsapi.modal.Peternakan;
import id.sapi.ktp.aplikasiktpsapi.modal.SapiAdapter;
import id.sapi.ktp.aplikasiktpsapi.util.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Beranda extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;
    String regId;
    TextView nmpeternakan, tkandang, tsuhu, tkelembapan, tgas, tid;
    private ArrayList<Peternakan> data;
    SharedPrefManager sharedPrefManager;
    String iduser;
    private ArrayList<Kandang> kandangs;
    private KandangAdapter adapter;
    LinearLayout monitoring;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_beranda, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("KTP Sapi");
        txtRegId = (TextView) view.findViewById(R.id.txt_reg_id);
        txtMessage = (TextView) view.findViewById(R.id.txt_push_message);

        monitoring = (LinearLayout)view.findViewById(R.id.monitor);
        tid = (TextView)view.findViewById(R.id.idkandang);
        nmpeternakan = (TextView) view.findViewById(R.id.namaPeternakan);
        tkandang = (TextView)view.findViewById(R.id.kandang);
        tsuhu  =(TextView)view.findViewById(R.id.suhu);
        tkelembapan = (TextView)view.findViewById(R.id.kelembapan);
        tgas = (TextView)view.findViewById(R.id.gas);

        sharedPrefManager = new SharedPrefManager(getActivity());
        SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);
        Log.d("regid", regId);

        monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DetailMonitoringKandang.class);
                i.putExtra("id_kandang", tid.getText().toString());
                i.putExtra("kandang", nmpeternakan.getText().toString());
                i.putExtra("suhu", tsuhu.getText().toString());
                i.putExtra("gas", tgas.getText().toString());
                i.putExtra("kelembapan", tkelembapan.getText().toString());
                startActivity(i);
            }
        });

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getKandang();
                handler.postDelayed(this,2000);//60 second delay
            }
        };handler.postDelayed(runnable, 2000);

        //Notification
       /* mRegistrationBroadcastReceiver = new BroadcastReceiver() {
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

                    Toast.makeText(getActivity(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    txtMessage.setText(message);
                    Notif(message);

                }
            }
        };*/

        //displayFirebaseRegId();
    }
    public void getKandang(){
     //   koneksi();
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
                tid.setText(kandangs.get(0).getId_kandang());
                tkandang.setText(kandangs.get(0).getKandang());
                tsuhu.setText(kandangs.get(0).getSuhu());
                tkelembapan.setText(kandangs.get(0).getKelembapan());
                tgas.setText(kandangs.get(0).getGas_amonia());
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

   /* void Notif(String msg){
        Notification notification = new NotificationCompat.Builder(getActivity())
                .setTicker("")
                .setSmallIcon(R.drawable.ic_folder_black_24dp)
                .setContentTitle("")
                .setContentText(msg)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            txtRegId.setText("Firebase Reg Id: " + regId);
        else
            txtRegId.setText("Firebase Reg Id is not received yet!");
    }

    @Override
    public void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getActivity());
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }*/
    //end notif

//    private boolean adaInternet(){
//        ConnectivityManager koneks = (ConnectivityManager) getActivity().getSystemService(getContext().CONNECTIVITY_SERVICE);
//        return koneks.getActiveNetworkInfo() != null;
//    }
//    private void koneksi(){
//        if(adaInternet()){
////            Toast.makeText(HalamanUtama.this, "Terhubung ke internet", Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(getActivity(), "Tidak ada koneksi internet", Toast.LENGTH_LONG).show();
//        }
//    }

}
