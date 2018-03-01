package id.sapi.ktp.aplikasiktpsapi.tab;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import az.plainpie.PieView;
import az.plainpie.animation.PieAngleAnimation;
import id.sapi.ktp.aplikasiktpsapi.R;

public class tab1 extends android.support.v4.app.Fragment {
    public static tab1 newInstance(){
        return new tab1();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){


        View view = inflater.inflate(R.layout.activity_tab1,container, false);


        PieView pieView = (PieView)view.findViewById(R.id.suhu);
        pieView.setPercentageBackgroundColor(getResources().getColor(R.color.plainChart));
        //pieView.setInnerText("Suhu");
        PieView animatedPie = (PieView)view.findViewById(R.id.suhu);
        pieView.setPercentage(72);
        PieAngleAnimation animation = new PieAngleAnimation(animatedPie);
        animation.setDuration(3000); //This is the duration of the animation in millis
        animatedPie.startAnimation(animation);



        PieView pieView2 = (PieView)view.findViewById(R.id.kelembapan);
        pieView2.setPercentageBackgroundColor(getResources().getColor(R.color.plainChart2));
        // pieView2.setInnerText("Kelembaban");
        PieView animatedPie2 = (PieView)view.findViewById(R.id.kelembapan);
        pieView2.setPercentage(60);

        PieAngleAnimation animation2 = new PieAngleAnimation(animatedPie2);
        animation2.setDuration(3000); //This is the duration of the animation in millis
        animatedPie2.startAnimation(animation2);
        return view;
    }
}