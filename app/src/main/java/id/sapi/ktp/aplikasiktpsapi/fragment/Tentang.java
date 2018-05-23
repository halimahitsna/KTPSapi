package id.sapi.ktp.aplikasiktpsapi.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.google.android.gms.common.api.Api;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import az.plainpie.PieView;
import az.plainpie.animation.PieAngleAnimation;
import de.hdodenhof.circleimageview.CircleImageView;
import id.sapi.ktp.aplikasiktpsapi.R;
import id.sapi.ktp.aplikasiktpsapi.activities.MainActivity;
import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.BaseResponse;
import id.sapi.ktp.aplikasiktpsapi.api.JSONResponse;
import id.sapi.ktp.aplikasiktpsapi.api.RetrofitClient;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.database.UserDB;
import id.sapi.ktp.aplikasiktpsapi.edit.EditData;
import id.sapi.ktp.aplikasiktpsapi.modal.Indukan;
import id.sapi.ktp.aplikasiktpsapi.modal.IndukanSpinner;
import id.sapi.ktp.aplikasiktpsapi.modal.Jenis;
import id.sapi.ktp.aplikasiktpsapi.modal.JenisSpinner;
import id.sapi.ktp.aplikasiktpsapi.modal.Kandang;
import id.sapi.ktp.aplikasiktpsapi.modal.KandangSlide;
import id.sapi.ktp.aplikasiktpsapi.modal.KandangSpinner;
import id.sapi.ktp.aplikasiktpsapi.modal.Pakan;
import id.sapi.ktp.aplikasiktpsapi.modal.PakanSpinner;
import id.sapi.ktp.aplikasiktpsapi.modal.Penyakit;
import id.sapi.ktp.aplikasiktpsapi.modal.PenyakitSpinner;
import id.sapi.ktp.aplikasiktpsapi.modal.Result;
import id.sapi.ktp.aplikasiktpsapi.modal.User;
import id.sapi.ktp.aplikasiktpsapi.util.SharedPrefManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

public class Tentang extends Fragment {

    SharedPrefManager sharedPrefManager;
    ViewPager viewPager;
    private ArrayList<Kandang> kandangs;
    private ViewPagerAdapter adapter;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    String iduser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_tentang, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Tentang");
        sharedPrefManager = new SharedPrefManager(getActivity());
        Intent a = getActivity().getIntent();
        sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
loadJSON();
       // ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity());



    }
    private void loadJSON() {
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
                viewPager.setAdapter(adapter);
                dotscount = adapter.getCount();
                dots = new ImageView[dotscount];

                for(int i = 0; i < dotscount; i++){

                    dots[i] = new ImageView(getActivity());
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.inactive_dot));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(8, 0, 8, 0);

                    sliderDotspanel.addView(dots[i], params);

                }

                dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.active_dot));

                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {

                        for(int i = 0; i< dotscount; i++){
                            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.inactive_dot));
                        }

                        dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.active_dot));

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

    }


}
