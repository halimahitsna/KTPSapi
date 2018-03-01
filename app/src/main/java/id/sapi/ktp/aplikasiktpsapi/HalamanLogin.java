package id.sapi.ktp.aplikasiktpsapi;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Typeface;
        import android.net.ConnectivityManager;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;


        import com.github.ybq.android.spinkit.style.DoubleBounce;
        import com.google.android.gms.common.ConnectionResult;

        import org.json.JSONObject;

        import java.util.Arrays;

import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.modal.UserData;
import id.sapi.ktp.aplikasiktpsapi.util.SharedPrefManager;
import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;




public class HalamanLogin extends AppCompatActivity {

    Button btnlogin, btnDaftar;//btn masuk
    EditText Euser, Epass;
    ProgressDialog pDialog;
    Context mContext;
    private SharedPreferences pref;
    private static final int REQ_CODE = 9001;
    Toolbar toolbar;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_login);

        mContext = this;
        sharedPrefManager = new SharedPrefManager(this);

        if (sharedPrefManager.getLogin()){
            startActivity(new Intent(HalamanLogin.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            //finish();
        }


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //login
        pDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        DoubleBounce doubleBounce = new DoubleBounce();
        pDialog.setIndeterminate(true);
        pDialog.setIndeterminateDrawable(doubleBounce);
        pDialog.setMessage("Please Wait...");
        pDialog.setCancelable(false);

        Euser = (EditText) findViewById(R.id.username);
        Epass = (EditText) findViewById(R.id.password);
        btnlogin = (Button) findViewById(R.id.btnMasuk);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    loginByServer();
                }
            }
        });
        btnDaftar = (Button)findViewById(R.id.linkdaftar);
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dft = new Intent(HalamanLogin.this, HalamanDaftar.class);
                startActivity(dft);
            }
        });
    }

    private boolean validate() {
        boolean valid = true;

        String user = Euser.getText().toString();
        String password = Epass.getText().toString();

        if (user.isEmpty()) {
            Euser.setError("Username is empty");
            valid = false;
        } else {
            Euser.setError(null);
        }

        if (password.isEmpty()) {
            Epass.setError("Password is empty");
            valid = false;
        } else {
            Epass.setError(null);
        }
        return valid;
    }

    private void loginByServer() {
        showpDialog();
        koneksi();
        sharedPrefManager.saveSPBoolean(SharedPrefManager.KEY_LOGIN, true);
        final String name = Euser.getText().toString();
        final String password = Epass.getText().toString();
        ApiService service = UtilsApi.getClient().create(ApiService.class);
        final Call<UserData> userCall = service.loginRequest(name,password);
        userCall.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                if(response.body().getStatus() == "true") {
                    hidepDialog();
                    Toast.makeText(HalamanLogin.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    sharedPrefManager.saveSPString(SharedPrefManager.KEY_USER_USER, name);
                    sharedPrefManager.saveSPString(SharedPrefManager.KEY_PASS, password);
                    // Shared Pref untuk trigger session login
                    sharedPrefManager.saveSPBoolean(SharedPrefManager.KEY_LOGIN, true);
                    startActivity(new Intent(HalamanLogin.this, MainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                }else {
                    hidepDialog();
                    Toast.makeText(HalamanLogin.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private boolean adaInternet(){
        ConnectivityManager koneksi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }
    private void koneksi(){
        if(adaInternet()){
//            Toast.makeText(SignIn.this, "Terhubung ke internet", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(HalamanLogin.this, "Tidak ada koneksi internet", Toast.LENGTH_LONG).show();
        }
    }
}
