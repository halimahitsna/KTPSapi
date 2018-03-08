package id.sapi.ktp.aplikasiktpsapi;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import az.plainpie.PieView;
import az.plainpie.animation.PieAngleAnimation;

public class Beranda extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        //Drawerbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.layoututama);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View header=navigationView.getHeaderView(0);

        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }
        setupNavigationDrawerContent(navigationView);

        PieView pieView = (PieView)findViewById(R.id.suhu);
        pieView.setPercentageBackgroundColor(getResources().getColor(R.color.plainChart));
        //pieView.setInnerText("Suhu");
        PieView animatedPie = (PieView)findViewById(R.id.suhu);
        pieView.setPercentage(72);
        PieAngleAnimation animation = new PieAngleAnimation(animatedPie);
        animation.setDuration(3000); //This is the duration of the animation in millis
        animatedPie.startAnimation(animation);



        PieView pieView2 = (PieView)findViewById(R.id.kelembapan);
        pieView2.setPercentageBackgroundColor(getResources().getColor(R.color.plainChart2));
        // pieView2.setInnerText("Kelembaban");
        PieView animatedPie2 = (PieView)findViewById(R.id.kelembapan);
        pieView2.setPercentage(60);

        PieAngleAnimation animation2 = new PieAngleAnimation(animatedPie2);
        animation2.setDuration(3000); //This is the duration of the animation in millis
        animatedPie2.startAnimation(animation2);

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
                                //Intent a = new Intent(HalamanUtama.this, HalamanUtama.class);
                                //startActivity(a);
                                return true;
                            case R.id.menu_manajemen:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                /*Intent b = new Intent(HalamanUtama.this, RiwayatUjian.class);
                                startActivity(b);*/
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
                                /*Intent e = new Intent(HalamanUtama.this, TentangKami.class);
                                startActivity(e);*/
                                return true;
                        }
                        return true;
                    }
                });
    }
}
