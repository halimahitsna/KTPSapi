package id.sapi.ktp.aplikasiktpsapi.modal;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import id.sapi.ktp.aplikasiktpsapi.Beranda;
import id.sapi.ktp.aplikasiktpsapi.R;

/**
 * Created by ASUS on 3/22/2018.
 */

public class KandangSlide extends PagerAdapter {
    private ArrayList<Kandang> kandang;
    private LayoutInflater inflater;
    private Context context;

    public KandangSlide(Context context, ArrayList<Kandang> kandang){
        this.kandang = kandang;
        this.context = context;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return kandang.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {

        TextView id, txtkandang, suhu, kelemb, gas;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.slide_kandang, view,
                false);

        id = (TextView)itemView.findViewById(R.id.idkandang);
        txtkandang = (TextView)itemView.findViewById(R.id.kandang);
        suhu = (TextView)itemView.findViewById(R.id.suhu);
        kelemb = (TextView)itemView.findViewById(R.id.kelembapan);
        gas = (TextView)itemView.findViewById(R.id.amonia);

        id.setText(kandang.get(position).getId_kandang());
        txtkandang.setText(kandang.get(position).getKandang());
        suhu.setText(kandang.get(position).getSuhu());
        kelemb.setText(kandang.get(position).getKelembapan());
        gas.setText(kandang.get(position).getGas_amonia());


        // Add viewpager_item.xml to ViewPager
        ((ViewPager) view).addView(itemView);

        return itemView;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
