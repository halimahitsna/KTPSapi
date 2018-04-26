package id.sapi.ktp.aplikasiktpsapi.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
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
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.common.api.Api;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
import id.sapi.ktp.aplikasiktpsapi.edit.EditProfil;
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

public class Profile extends Fragment {
    TextView tid, tuser, tname, tpass;
    EditText eid;
    CircleImageView ifoto;
    FloatingActionButton btnedit;
    private ArrayList<User> data;
    SharedPrefManager sharedPrefManager;
    String iduser, imagePath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_profile, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Profile");
        sharedPrefManager = new SharedPrefManager(getActivity());
        iduser = sharedPrefManager.getSPId();

        eid = (EditText) view.findViewById(R.id.id);
        eid.setText(iduser);
        tuser = (TextView)view.findViewById(R.id.username);
        tname = (TextView)view.findViewById(R.id.nama);
        tpass = (TextView)view.findViewById(R.id.password);
        ifoto= (CircleImageView) view.findViewById(R.id.foto);
        btnedit = (FloatingActionButton)view.findViewById(R.id.edit);
        loadHeader();

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getActivity(), EditProfil.class);
                a.putExtra("id_user", eid.getText().toString().trim());
                a.putExtra("foto", data.get(0).getFoto());
                a.putExtra("username", data.get(0).getUser());
                a.putExtra("nama", data.get(0).getName());
                a.putExtra("password", data.get(0).getPassword());
                a.putExtra("jeniskelamin", data.get(0).getJenis_kelamin());
                startActivity(a);
            }
        });
    }

    private void loadHeader() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // get user data from session
        HashMap<String, String> user = sharedPrefManager.getUserDetails();
        ApiService request = retrofit.create(ApiService.class);

        Call<JSONResponse> call = request.getUser(iduser);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getUsers()));
               // tid.setText(data.get(0).getId_user());
                Picasso.with(getActivity()).load(data.get(0).getFoto()).placeholder(R.drawable.load).into(ifoto);
                tname.setText(data.get(0).getName());
                tuser.setText(data.get(0).getUser());
                tpass.setText(data.get(0).getPassword());
                //Picasso.with(MainActivity.class).load(data.get(0).getFoto()).placeholder(R.drawable.load).into(MainActivity.id.byteValue())
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    private void uploadImage() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ktpsapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        File file = new File(imagePath);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        MultipartBody.Part text = MultipartBody.Part.createFormData("id_user", eid.getText().toString().trim());

        ApiService request = retrofit.create(ApiService.class);
        Call<BaseResponse> call = request.uploadFotoUser(body,text);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()==true) {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }else
                        Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                }

                //ifoto.setImageDrawable(null);
                imagePath = null;
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        loadHeader();
    }

    @Override
    public void onResume(){
        super.onResume();
        loadHeader();
    }

}
