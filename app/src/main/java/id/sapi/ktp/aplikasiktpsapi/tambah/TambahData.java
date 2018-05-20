package id.sapi.ktp.aplikasiktpsapi.tambah;

import android.Manifest;
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
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import id.sapi.ktp.aplikasiktpsapi.R;
import id.sapi.ktp.aplikasiktpsapi.activities.MainActivity;
import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.BaseResponse;
import id.sapi.ktp.aplikasiktpsapi.api.JSONResponse;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.modal.Indukan;
import id.sapi.ktp.aplikasiktpsapi.modal.IndukanSpinner;
import id.sapi.ktp.aplikasiktpsapi.modal.Jenis;
import id.sapi.ktp.aplikasiktpsapi.modal.JenisAdapter;
import id.sapi.ktp.aplikasiktpsapi.modal.JenisSpinner;
import id.sapi.ktp.aplikasiktpsapi.modal.Kandang;
import id.sapi.ktp.aplikasiktpsapi.modal.KandangSpinner;
import id.sapi.ktp.aplikasiktpsapi.modal.Pakan;
import id.sapi.ktp.aplikasiktpsapi.modal.PakanSpinner;
import id.sapi.ktp.aplikasiktpsapi.modal.Penyakit;
import id.sapi.ktp.aplikasiktpsapi.modal.PenyakitSpinner;
import id.sapi.ktp.aplikasiktpsapi.modal.ResponseData;
import id.sapi.ktp.aplikasiktpsapi.modal.Result;
import id.sapi.ktp.aplikasiktpsapi.util.SharedPrefManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TambahData extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    ActionBar actionBar;
    Toolbar toolbar;
    ProgressDialog loading;
    Button btnsimpan;
    EditText txtidSapi, txtbobotlahir, txtbobothidup, txtumur, txtharga, txtwarna, tgllahir, txdate, txiduser;
    ImageView imdate;
    CircleImageView foto;
    FloatingActionButton add;
    Spinner jenis, kandang, indukan, pakan, penyakit, jeniskel;
    //DatePicker tgl_lahir;
    Context mcontext;
    Retrofit apiService;
    String iduser, sjenis, skandang, sindukan, spakan, spenyakit,sjk, imagePath;
    private Calendar mCalendar;
    private int mYear, mMonth, mHour, mMinute, mDay;
    private String mDate;
    SharedPrefManager sharedPrefManager;
    private ArrayList<Jenis> data1;
    private ArrayList<Indukan> data2;
    private ArrayList<Kandang> data3;
    private ArrayList<Pakan> data4;
    private ArrayList<Penyakit> data5;

    // Constant values in milliseconds
    private static final long milMinute = 60000L;
    private static final long milHour = 3600000L;
    private static final long milDay = 86400000L;
    private static final long milWeek = 604800000L;
    private static final long milMonth = 2592000000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
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
        judul.setText(R.string.tambah_data);
        btnsimpan = (Button) findViewById(R.id.btnSimpan);
        foto = (CircleImageView) findViewById(R.id.foto);
        txtidSapi = (EditText) findViewById(R.id.idSapi);
        txtharga = (EditText) findViewById(R.id.harga);
        txtbobothidup = (EditText) findViewById(R.id.bobot_hidup);
        txtbobotlahir = (EditText) findViewById(R.id.bobot_lhr);
        txtumur = (EditText) findViewById(R.id.umur);
        txtwarna = (EditText)findViewById(R.id.warna);
        txdate = (EditText)findViewById(R.id.date);
        txiduser = (EditText)findViewById(R.id.idu);
        add = (FloatingActionButton)findViewById(R.id.btnfoto);
        //EditTextGet
        txiduser.setText(getIntent().getStringExtra("id_user"));
        txtidSapi.setText(getIntent().getStringExtra("id_sapi"));
        txtharga.setText(getIntent().getStringExtra("umur"));
        txtumur.setText(getIntent().getStringExtra("umur"));
        txtbobotlahir.setText(getIntent().getStringExtra("bobot_lahir"));
        txtbobothidup.setText(getIntent().getStringExtra("bobot_hidup"));
        //Picasso.with(mcontext).load(getIntent().getStringExtra("foto")).resize(150, 150).into(foto);

        jenis = (Spinner) findViewById(R.id.jenis);
        indukan = (Spinner) findViewById(R.id.indukan);
        kandang = (Spinner) findViewById(R.id.kandang);
        pakan = (Spinner) findViewById(R.id.pakan);
        penyakit = (Spinner) findViewById(R.id.penyakit);
        imdate = (ImageView) findViewById(R.id.set_date);
        jeniskel = (Spinner)findViewById(R.id.piljk);

        initSpinnerJenis();
        initSpinnerKandang();
        initSpinnerIndukan();
        initSpinnerPakan();
        initSpinnerPeny();
        final List<String> status = new ArrayList<String>();
        status.add("Jantan");
        status.add("Betina");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, status);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        jeniskel.setAdapter(dataAdapter);
        jeniskel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = status.get(position).toString();
                sjk = item;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                sjk = "belum diatur";
            }
        });

        jenis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sjenis = data1.get(position).getId_jenis();
               // Toast.makeText(TambahData.this, sjenis, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                sjenis = "belum diatur";
            }
        });
        kandang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                skandang = data3.get(position).getId_kandang();
                //Toast.makeText(TambahData.this, skandang, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                skandang = "belum diatur";
            }
        });
        indukan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sindukan = data2.get(position).getId_indukan();
               // Toast.makeText(TambahData.this, sindukan, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                sindukan = "belum diatur";
            }
        });
        pakan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spakan = data4.get(position).getId_pakan();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                spakan = "belum diatur";
            }
        });
        penyakit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spenyakit = data5.get(position).getId_penyakit();
             //   Toast.makeText(TambahData.this, spenyakit, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                spenyakit = "belum diatur";
            }
        });

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imagePath!=null) {
                    simpan();
                    uploadImage();
                }else if(txtidSapi.getText().toString().isEmpty())
                    txtidSapi.setError("Id Sapi masih kosong!");
                else if(imagePath ==null) {
                    simpan();
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

        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DATE);

        mDate = mDay + "/" + mMonth + "/" + mYear;
        txdate.setText(mDate);
    }

    private void simpan() {
        //koneksi();
        String id = txtidSapi.getText().toString().trim();
        String bl = txtbobotlahir.getText().toString().trim();
        String bhdp = txtbobothidup.getText().toString().trim();
        String hr = txtharga.getText().toString().trim();
        String umur = txtumur.getText().toString().trim();
        String wr = txtwarna.getText().toString().trim();
        String jn = sjenis.trim();
        String kd = skandang.trim();
        String jkel = sjk.trim();
        String in = sindukan.trim();
        String pkn = spakan.trim();
        String pny = spakan.trim();
        String tg = mDate.toString().trim();
        String user = iduser.toString().trim();

        //validate();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService api = retrofit.create(ApiService.class);
        Call<Result> call = api.insertSapi(id, jn, kd, in, pkn, pny,jkel, tg, bl, bhdp, umur, wr,user, hr);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
//                loading.dismiss();
                if (value.equals("1")) {
                    Toast.makeText(TambahData.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TambahData.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                // progress.dismiss();
                Toast.makeText(TambahData.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinnerJenis() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService request = retrofit.create(ApiService.class);
        // Integer id = Integer.valueOf(sharedPrefManager.getSPId());
        Call<JSONResponse> call = request.getJSONJenis(iduser);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if (response.isSuccessful()) {

                    JSONResponse jsonResponse = response.body();
                    data1 = new ArrayList<>(Arrays.asList(jsonResponse.getJenis()));
                    ArrayList<Jenis> objects = new ArrayList<Jenis>();
                    //objects.add(0, je);
                    for (int i = 0; i < data1.size(); i++) {
                        Jenis obj = new Jenis();
                        obj.setAll(data1.get(i).getId_jenis(), data1.get(i).getJenis());
                        objects.add(obj);
                    }
                   // sjenis = data1.get(0).getId_jenis();
                    jenis.setAdapter(new JenisSpinner(TambahData.this, objects));
                } else {
                    Toast.makeText(TambahData.this, "Gagal mengambil data jenis", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    private void initSpinnerKandang() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService request = retrofit.create(ApiService.class);
        // Integer id = Integer.valueOf(sharedPrefManager.getSPId());
        Call<JSONResponse> call = request.getJSONKandang(iduser);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if (response.isSuccessful()) {
                    JSONResponse jsonResponse = response.body();
                    data3 = new ArrayList<>(Arrays.asList(jsonResponse.getKandang()));
                    ArrayList<Kandang> objects = new ArrayList<Kandang>();
                    for (int i = 0; i < data3.size(); i++) {
                        Kandang obj = new Kandang();
                        obj.setAll(data3.get(i).getId_kandang(), data3.get(i).getKandang());
                        objects.add(obj);
                    }
//                    skandang = data3.get(0).getId_kandang();
                    kandang.setPrompt("Pilih Kandang");
                    kandang.setAdapter(new KandangSpinner(TambahData.this, objects));

                } else {
                    Toast.makeText(TambahData.this, "Gagal mengambil data jenis", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    private void initSpinnerIndukan() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService request = retrofit.create(ApiService.class);
        // Integer id = Integer.valueOf(sharedPrefManager.getSPId());
        Call<JSONResponse> call = request.getIndukan(iduser);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if (response.isSuccessful()) {
                    JSONResponse jsonResponse = response.body();
                    data2 = new ArrayList<>(Arrays.asList(jsonResponse.getIndukan()));
                    ArrayList<Indukan> objects = new ArrayList<Indukan>();
                    for (int i = 0; i < data2.size(); i++) {
                        Indukan obj = new Indukan();
                        obj.setAll(data2.get(i).getId_indukan(), data2.get(i).getIndukan());
                        objects.add(obj);
                    }
                  //  sindukan = data2.get(0).getId_indukan();
                    indukan.setPrompt("Pilih Indukan Sapi");
                    indukan.setAdapter(new IndukanSpinner(TambahData.this, objects));

                } else {
                    Toast.makeText(TambahData.this, "Gagal mengambil data jenis", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    private void initSpinnerPakan() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService request = retrofit.create(ApiService.class);
        // Integer id = Integer.valueOf(sharedPrefManager.getSPId());
        Call<JSONResponse> call = request.getPakan(iduser);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if (response.isSuccessful()) {
                    JSONResponse jsonResponse = response.body();
                    data4 = new ArrayList<>(Arrays.asList(jsonResponse.getPakan()));
                    ArrayList<Pakan> objects = new ArrayList<Pakan>();
                    for (int i = 0; i < data4.size(); i++) {
                        Pakan obj = new Pakan();
                        obj.setAll(data4.get(i).getId_pakan(), data4.get(i).getPakan());
                        objects.add(obj);
                    }
                   // spakan = data4.get(0).getId_pakan();
                    pakan.setPrompt("Pilih Pakan Sapi");
                    pakan.setAdapter(new PakanSpinner(TambahData.this, objects));

                } else {
                    Toast.makeText(TambahData.this, "Gagal mengambil data jenis", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    private void initSpinnerPeny() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService request = retrofit.create(ApiService.class);
        // Integer id = Integer.valueOf(sharedPrefManager.getSPId());
        Call<JSONResponse> call = request.getPenyakit(iduser);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if (response.isSuccessful()) {
                    JSONResponse jsonResponse = response.body();
                    data5 = new ArrayList<>(Arrays.asList(jsonResponse.getPenyakit()));
                    ArrayList<Penyakit> objects = new ArrayList<Penyakit>();
                    for (int i = 0; i < data5.size(); i++) {
                        Penyakit obj = new Penyakit();
                        obj.setAll(data5.get(i).getId_penyakit(), data5.get(i).getPenyakit());
                        objects.add(obj);
                    }
                //    spenyakit = data5.get(0).getId_penyakit();
                    penyakit.setPrompt("Pilih Riwayat Penyakit");
                    penyakit.setAdapter(new PenyakitSpinner(TambahData.this, objects));

                } else {
                    Toast.makeText(TambahData.this, "Gagal mengambil data jenis", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear ++;
        mDay = dayOfMonth;
        mMonth = monthOfYear;
        mYear = year;
        mDate = dayOfMonth + "/" + monthOfYear + "/" + year;
        txdate.setText(mDate);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void setDate(View view) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
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
        MultipartBody.Part text = MultipartBody.Part.createFormData("id_sapi", txtidSapi.getText().toString().trim());

        ApiService request = retrofit.create(ApiService.class);
        Call<BaseResponse> call = request.uploadFotoSapi(body,text);
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

                //ifoto.setImageDrawable(null);
                imagePath = null;
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
            }
        });
        onBackPressed();
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


}


