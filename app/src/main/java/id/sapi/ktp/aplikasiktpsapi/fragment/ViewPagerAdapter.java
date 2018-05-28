package id.sapi.ktp.aplikasiktpsapi.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import id.sapi.ktp.aplikasiktpsapi.R;
import id.sapi.ktp.aplikasiktpsapi.activities.DetailMonitoringKandang;
import id.sapi.ktp.aplikasiktpsapi.modal.Kandang;

public class ViewPagerAdapter extends PagerAdapter  {

    Context context;
    private LayoutInflater layoutInflater;
    ArrayList<Kandang> kandang;

    public ViewPagerAdapter(Context context, ArrayList<Kandang> kandangs) {
        this.context = context;
        this.kandang = kandangs;
    }

    @Override
    public int getCount() {
        return kandang.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final TextView id, nama, tvSuhu, tvKel, tvGas;
        final ImageView foto;
        final ArcProgress asuhu, agas, akelembapan;
        final CardView cardView;
        final String url;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.beranda_row, null);
        cardView = (CardView)view.findViewById(R.id.card);
        id = (TextView) view.findViewById(R.id.idkandang);
        nama = (TextView) view.findViewById(R.id.kandang);
        tvSuhu = (TextView)view.findViewById(R.id.tvsuhu);
        tvKel = (TextView)view.findViewById(R.id.tvkelembapan);
        tvGas = (TextView)view.findViewById(R.id.tvgas);
        foto = (ImageView) view.findViewById(R.id.foto);
        asuhu = (ArcProgress) view.findViewById(R.id.suhu);
        asuhu.setTextSize(0);
        agas = (ArcProgress) view.findViewById(R.id.gas);
        akelembapan = (ArcProgress)view.findViewById(R.id.kelembapan);
        id.setText(kandang.get(position).getId_kandang());
        nama.setText(kandang.get(position).getKandang());
        if(kandang.get(position).getSuhu() != null) {
            asuhu.setProgress((int) Double.parseDouble(kandang.get(position).getSuhu()));
            tvSuhu.setText(kandang.get(position).getSuhu() +"\n   "+ (char) 0x00B0 +"C");
        }else {
            asuhu.setProgress(0);
            tvSuhu.setText("0" + (char) 0x00B0 + "C");
        }

        if(kandang.get(position).getGas_amonia() != null) {
            agas.setProgress((int) Double.parseDouble(kandang.get(position).getGas_amonia()));
            tvGas.setText(kandang.get(position).getGas_amonia()+"\n PPm");
        }else {
            agas.setProgress(0);
            tvGas.setText("0PPm");
        }
        if(kandang.get(position).getKelembapan() != null) {
            akelembapan.setProgress((int) Double.parseDouble(kandang.get(position).getKelembapan()));
            tvKel.setText(kandang.get(position).getKelembapan() +"\n    %");
        }else {
            akelembapan.setProgress(0);
            tvKel.setText("0%");
        }
        if(kandang.get(position).getFoto() != null) {
            Picasso.with(context).load(kandang.get(position).getFoto()).placeholder(R.drawable.load).into(foto);
        }else {
            Picasso.with(context).load(R.drawable.ic_person_black_24dp).placeholder(R.drawable.load).into(foto);
        }
        url = kandang.get(position).getFoto();


        final ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posisi = vp.getCurrentItem();
                Intent intent = new Intent(context, DetailMonitoringKandang.class);
                intent.putExtra("id_kandang", id.getText().toString());
                intent.putExtra("kandang", nama.getText().toString());
                intent.putExtra("suhu", String.valueOf(asuhu.getProgress()));
                intent.putExtra("kelembapan",String.valueOf(akelembapan.getProgress()));
                intent.putExtra("gas", String.valueOf(agas.getProgress()));
                intent.putExtra("foto", url);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}