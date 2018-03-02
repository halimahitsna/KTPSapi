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
    ProgressDialog loading;
    Context mContext;
    private SharedPreferences pref;
    private static final int REQ_CODE = 9001;
    Toolbar toolbar;
    TextView textToolbar;
    SharedPrefManager sharedPrefManager;
    ApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_login);

        mContext = this;

        textToolbar = (TextView)findViewById(R.id.toolbar_title);

        loading = new ProgressDialog(this, R.style.AppTheme);
        DoubleBounce doubleBounce = new DoubleBounce();
        loading.setIndeterminate(true);
        loading.setIndeterminateDrawable(doubleBounce);
        loading.setMessage("Please wait...");
        loading.setCancelable(false);

        Euser = (EditText) findViewById(R.id.username);
        Epass = (EditText)findViewById(R.id.password);
        btnlogin = (Button)findViewById(R.id.btnMasuk);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if(validate()){
                    login();
                }*/
            }
        });
    }
   /* private boolean validate(){
            boolean valid = true;
            String user = Euser.getText().toString();
            String password = Epass.getText().toString();

            if(user .isEmpty()){
                Euser.setError("Username masih kosong!");
                valid = false;
            }else {
                Euser.setError(null);
            }
            if(password.isEmpty()){
                Epass.setError("Password masih kosong!");
                valid = false;
            }
            return valid;
    }

    private void login(){
        //showpDialog();
        final String nama = Euser.getText().toString().trim();
        final String password = Epass.getText().toString().trim();
        ApiService service = UtilsApi.getAPIService();
        final Call<UserData> userDataCall = service.loginRequest(nama, password);
        userDataCall.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
               // hidepDialog();
                Toast.makeText(HalamanLogin.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                *//*if(response.body().getStatus() == "true"){
                    hidepDialog();
                    Toast.makeText(HalamanLogin.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                *//**//*startActivity(new Intent(HalamanLogin.this, HalamanData.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();*//**//*
                }else {
                    hidepDialog();
                    Toast.makeText(HalamanLogin.this,response.body().getMsg(),Toast.LENGTH_SHORT);
                }*//*
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                hidepDialog();
                Toast.makeText(HalamanLogin.this,"Failure",Toast.LENGTH_SHORT);

            }
        });


    }
    private void hidepDialog() {
        if (loading.isShowing())
            loading.dismiss();
    }

    private void showpDialog() {
        if (!loading.isShowing())
            loading.show();
    }*/

}
