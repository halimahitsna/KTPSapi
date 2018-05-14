package id.sapi.ktp.aplikasiktpsapi.activities;

import android.app.Application;
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
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import id.sapi.ktp.aplikasiktpsapi.R;
import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.JSONResponse;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.database.UserDB;
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
import id.sapi.ktp.aplikasiktpsapi.modal.User;
import id.sapi.ktp.aplikasiktpsapi.util.SharedPrefManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Notif extends Application {
    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    TextView txtRegId, txtMessage;
    String regId;
    Context mContext;
}

