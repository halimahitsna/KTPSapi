package id.sapi.ktp.aplikasiktpsapi.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.AddFloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

import id.sapi.ktp.aplikasiktpsapi.R;
import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.JSONResponse;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.modal.Data;
import id.sapi.ktp.aplikasiktpsapi.modal.DataAdapter;
import id.sapi.ktp.aplikasiktpsapi.tambah.TambahData;
import id.sapi.ktp.aplikasiktpsapi.util.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Manajemen extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Toolbar toolbar;
    ActionBar actionBar;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;
    private RecyclerView recyclerView;
    private ArrayList<Data> data1;
    private DataAdapter adapter1;
    SharedPrefManager sharedPrefManager;
    public TextView tjudul, nama, tkoneksi;
    public ImageView image;
    String iduser;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_data);

        Intent i = getIntent();
        iduser = i.getStringExtra("id_user");

        sharedPrefManager = new SharedPrefManager(this);

        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        AddFloatingActionButton add = (AddFloatingActionButton) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(Manajemen.this, TambahData.class);
                a.putExtra("id_user", iduser);
                startActivity(a);
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        tjudul = (TextView)findViewById(R.id.toolbar_title);
        tjudul.setText("Data Sapi");
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swiperefresh);
        tkoneksi = (TextView)findViewById(R.id.txtkoneksi);
        tkoneksi.setVisibility(View.INVISIBLE);

        initViews();

    }

    @Override
    public void onRefresh() {
        loadJSON();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.card_recycle_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);

        loadJSON();
    }

    private void loadJSON() {
        swipeRefreshLayout.setRefreshing(true);
        koneksi();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService request = retrofit.create(ApiService.class);
        Call<JSONResponse> call = request.getData(iduser);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                swipeRefreshLayout.setRefreshing(false);
                JSONResponse jsonResponse = response.body();
                data1 = new ArrayList<>(Arrays.asList(jsonResponse.getData()));
                adapter1 = new DataAdapter(getApplicationContext(), data1);
                recyclerView.setAdapter(adapter1);
                //swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        loadJSON();
    }

    @Override
    public void onResume(){
        super.onResume();
        loadJSON();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);
//        MenuItem search = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
//        search(searchView);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }else if(item.getItemId() == R.id.action_search){
            tjudul.setVisibility(View.INVISIBLE);
            handleMenuSearch();
            return true;
        }else
            tjudul.setVisibility(View.VISIBLE);

        return super.onOptionsItemSelected(item);
        //return false;
    }

    protected void handleMenuSearch(){
        ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(false); //show the title in the action bar
            tjudul.setVisibility(View.VISIBLE);

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_search_black_24dp));

            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText)action.getCustomView().findViewById(R.id.edtSearch); //the text editor

            edtSeach.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    //after the change calling the method and passing the search input
                    doSearch(editable.toString());
                }
            });


            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);


            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_close_black_24dp));

            isSearchOpened = true;
        }
    }

    private void doSearch(String text){
        //new array list that will hold the filtered data
        ArrayList<Data> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (Data data : data1) {
            //if the existing elements contains the search input
            if (data.getId_sapi().toLowerCase().contains(text.toLowerCase())) {

                filterdNames.add(data);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter1.filterList(filterdNames);
    }

    private boolean adaInternet(){
        ConnectivityManager koneksi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }
    private void koneksi(){
        if(adaInternet()){
            //Toast.makeText(HalamanUtama.this, "Terhubung ke internet", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(Manajemen.this, "Tidak ada koneksi internet", Toast.LENGTH_LONG).show();
            tkoneksi.setVisibility(View.VISIBLE);
            tkoneksi.setText("Tidak ada koneksi internet!");
        }
    }
}
