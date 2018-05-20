package id.sapi.ktp.aplikasiktpsapi.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.DrawableContainer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import az.plainpie.PieView;
import az.plainpie.animation.PieAngleAnimation;
import id.sapi.ktp.aplikasiktpsapi.R;

public class Pengaturan extends Fragment {
    RelativeLayout relativeLayoutPeternakan;
    TextView tPeternakan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_pengaturan, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Pengaturan");
        tPeternakan = (TextView)view.findViewById(R.id.textView);
        relativeLayoutPeternakan = (RelativeLayout) view.findViewById(R.id.layoutPeternakan);
        relativeLayoutPeternakan.setVisibility(View.GONE);

        tPeternakan.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View view) {
                tPeternakan.setCompoundDrawablesRelative(null, null, getResources().getDrawable(R.drawable.ic_arrow_drop_up_black_24dp), null);
                relativeLayoutPeternakan.setVisibility(relativeLayoutPeternakan.isShown() ? View.GONE : View.VISIBLE);
            }
        });

    }

}
