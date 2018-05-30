package id.sapi.ktp.aplikasiktpsapi.activities;

import android.content.Intent;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import id.sapi.ktp.aplikasiktpsapi.R;

public class SplashScreen extends AwesomeSplash{
    @Override
    public void initSplash(ConfigSplash configSplash) {
        configSplash.setBackgroundColor(R.color.background);
        configSplash.setAnimCircularRevealDuration(1500);
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM);

        configSplash.setLogoSplash(R.drawable.sapi);
        configSplash.setAnimLogoSplashDuration(2000);
        configSplash.setAnimLogoSplashTechnique(Techniques.BounceIn);
        configSplash.setTitleTextSize(0);
    }

    @Override
    public void animationsFinished() {
        Intent intent = new Intent(getApplicationContext(),HalamanLogin.class);
        intent.putExtra("berhasil",1);
        startActivity(intent);
        finish();
    }

}
