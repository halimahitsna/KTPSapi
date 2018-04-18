package id.sapi.ktp.aplikasiktpsapi.modal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import id.sapi.ktp.aplikasiktpsapi.R;
import id.sapi.ktp.aplikasiktpsapi.activities.DetailData;
import id.sapi.ktp.aplikasiktpsapi.activities.DetailMonitoringKandang;
import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.edit.EditData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hali on 25/08/2017.
 */

public class KandangSlide extends RecyclerView.Adapter<KandangSlide.ViewHolder> {
    private ArrayList<Kandang> kandangs;
    private Context context;

    public KandangSlide(Context context, ArrayList<Kandang> kandangs) {
        this.kandangs = kandangs;
        this.context = context;
    }

    @Override
    public KandangSlide.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.beranda_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KandangSlide.ViewHolder viewHolder, int i) {
        viewHolder.id.setText(kandangs.get(i).getId_kandang());
        viewHolder.nama.setText(kandangs.get(i).getKandang());

        if(kandangs.get(i).getSuhu() != null)
            viewHolder.asuhu.setProgress(Integer.valueOf(kandangs.get(i).getSuhu()));
        else
            viewHolder.asuhu.setProgress(0);

        if(kandangs.get(i).getGas_amonia() != null)
            viewHolder.agas.setProgress(Integer.valueOf(kandangs.get(i).getGas_amonia()));
        else
            viewHolder.agas.setProgress(0);

        if(kandangs.get(i).getKelembapan() != null)
            viewHolder.akelembapan.setProgress(Integer.valueOf(kandangs.get(i).getKelembapan()));
        else
            viewHolder.akelembapan.setProgress(0);

//        if(kandangs.get(i).getKandang() != null) {
//            Picasso.with(context).load(datas.get(i).getFoto()).placeholder(R.drawable.load).resize(100, 100)
//                    .into(viewHolder.foto);
//        }else {
//            Picasso.with(context).load(R.drawable.ic_person_black_24dp).placeholder(R.drawable.load).resize(100, 100)
//                    .into(viewHolder.foto);
//        }
    }

    @Override
    public int getItemCount() {
        return kandangs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView id, nama;
        ImageView foto;
        ArcProgress asuhu, agas, akelembapan;

        public ViewHolder(View view) {
            super(view);

            id = (TextView) view.findViewById(R.id.idkandang);
            nama = (TextView) view.findViewById(R.id.kandang);
            foto = (ImageView) view.findViewById(R.id.foto);
            asuhu = (ArcProgress) view.findViewById(R.id.suhu);
            agas = (ArcProgress) view.findViewById(R.id.gas);
            akelembapan = (ArcProgress)view.findViewById(R.id.kelembapan);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int posisi = getAdapterPosition();

                    Kandang clickedDataItem = kandangs.get(posisi);
                    Intent intent = new Intent(context, DetailMonitoringKandang.class);
                    intent.putExtra("id_kandang", kandangs.get(posisi).getId_kandang());
                    intent.putExtra("kandang", kandangs.get(posisi).getKandang());
                    intent.putExtra("suhu", kandangs.get(posisi).getSuhu());
                    intent.putExtra("kelembapan", kandangs.get(posisi).getKelembapan());
                    intent.putExtra("gas", kandangs.get(posisi).getGas_amonia());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }

            });
        }

        @Override
        public void onClick(View view) {

        }
    }
}
