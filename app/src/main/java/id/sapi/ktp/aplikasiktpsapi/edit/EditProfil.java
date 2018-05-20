package id.sapi.ktp.aplikasiktpsapi.edit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import id.sapi.ktp.aplikasiktpsapi.R;
import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.BaseResponse;
import id.sapi.ktp.aplikasiktpsapi.api.JSONResponse;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.modal.Indukan;
import id.sapi.ktp.aplikasiktpsapi.modal.IndukanSpinner;
import id.sapi.ktp.aplikasiktpsapi.modal.Jenis;
import id.sapi.ktp.aplikasiktpsapi.modal.JenisSpinner;
import id.sapi.ktp.aplikasiktpsapi.modal.Kandang;
import id.sapi.ktp.aplikasiktpsapi.modal.KandangSpinner;
import id.sapi.ktp.aplikasiktpsapi.modal.Pakan;
import id.sapi.ktp.aplikasiktpsapi.modal.PakanSpinner;
import id.sapi.ktp.aplikasiktpsapi.modal.Penyakit;
import id.sapi.ktp.aplikasiktpsapi.modal.PenyakitSpinner;
import id.sapi.ktp.aplikasiktpsapi.modal.Result;
import id.sapi.ktp.aplikasiktpsapi.tambah.TambahData;
import id.sapi.ktp.aplikasiktpsapi.util.SharedPrefManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditProfil extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    ActionBar actionBar;
    Toolbar toolbar;
    Button btnsimpan;
    EditText eid, euser, enama;
    CircleImageView foto;
    FloatingActionButton add;
    Spinner jenis;
    //DatePicker tgl_lahir;
    Context mcontext;
    Retrofit apiService;
    String iduser, imagePath;
    SharedPrefManager sharedPrefManager;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);
        sharedPrefManager = new SharedPrefManager(this);
        Intent i = getIntent();
        iduser = i.getStringExtra("id_user");
        verifyStoragePermissions(this);

        mcontext = this;
        apiService = UtilsApi.getClient();
        //Drawerbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        // actionBar.setDisplayShowHomeEnabled(true);
        TextView judul = (TextView)findViewById(R.id.toolbar_title);
        judul.setText("Edit Profil");
        eid = (EditText)findViewById(R.id.idu);
        euser = (EditText)findViewById(R.id.Eusername) ;
        enama = (EditText)findViewById(R.id.Enama);
        btnsimpan = (Button) findViewById(R.id.btnSimpan);
        foto = (CircleImageView) findViewById(R.id.foto);
        eid.setText(getIntent().getStringExtra("id_user"));
        add = (FloatingActionButton)findViewById(R.id.btnfoto);
        Picasso.with(mcontext).load(getIntent().getStringExtra("foto")).resize(150, 150).centerCrop()                .into(foto);
        enama.setText(getIntent().getStringExtra("nama"));
        euser.setText(getIntent().getStringExtra("username"));

        jenis = (Spinner) findViewById(R.id.jk);
        jenis.setOnItemSelectedListener(this);
        List<String> status = new ArrayList<String>();
        status.add("Pria");
        status.add("Wanita");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, status);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        jenis.setAdapter(dataAdapter);
        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imagePath != null) {
                    //
                    // update();
                    uploadImage();
                } else if (euser.getText().toString().isEmpty() || eid.getText().toString().isEmpty()){
                    euser.setError("Username masih kosong!");
                    enama.setError("Nama masih kosong!");
                }else {
                    //update();
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_PICK);

                final Intent chooserIntent = Intent.createChooser(galleryIntent, "Choose image");
                startActivityForResult(chooserIntent, 100);
            }
        });
    }

    private void update() {
        String id = eid.getText().toString().trim();
        String user = euser.getText().toString().trim();
        String nm = enama.getText().toString().trim();

        //validate();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService api = retrofit.create(ApiService.class);
        Call<Result> call = api.updateUser(id, user, nm, jenis.getSelectedItem().toString().trim() );
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
//                loading.dismiss();
                if (value.equals("1")) {
                    Toast.makeText(EditProfil.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditProfil.this, message, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                // progress.dismiss();
                Toast.makeText(EditProfil.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });
        onBackPressed();
    }


    private void uploadImage(){
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
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                    }else
                        Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
                onBackPressed();

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
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            if (data == null) {
                Toast.makeText(getApplicationContext(),"Unable to pick image",Toast.LENGTH_LONG).show();
                return;
            }

            Uri imageUri = data.getData();
            foto.setImageURI(imageUri);
            imagePath =getRealPathFromURI(imageUri);

        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = jenis.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
