package id.sapi.ktp.aplikasiktpsapi.modal;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import id.sapi.ktp.aplikasiktpsapi.edit.EditKandang;
import id.sapi.ktp.aplikasiktpsapi.R;
import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ASUS on 3/8/2018.
 */

public class KandangAdapter extends RecyclerView.Adapter<KandangAdapter.ViewHolder> {
    private ArrayList<Kandang> kandang;
    private Context context;

    public KandangAdapter(Context context, ArrayList<Kandang> kandang){
        this.kandang = kandang;
        this.context = context;
    }

    @Override
    public KandangAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.kandang_row, viewGroup, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KandangAdapter.ViewHolder viewHolder, int i){
        viewHolder.id_kandang.setText(kandang.get(i).getId_kandang());
        viewHolder.txtkandang.setText(kandang.get(i).getKandang());
    }

    @Override
    public int getItemCount() {
        return kandang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView id_kandang, txtkandang;
        ImageView edit, hapus;
        public ViewHolder(final View itemView) {
            super(itemView);
            id_kandang = (TextView)itemView.findViewById(R.id.idKandang);
            txtkandang = (TextView)itemView.findViewById(R.id.kandang);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            hapus = (ImageView) itemView.findViewById(R.id.hapus);

            //edit.setTag(R.integer.cast_libraries_material_featurehighlight_pulse_base_alpha, view);
            edit.setOnClickListener(this);
            hapus.setOnClickListener(this);
            // on item click
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        final Dialog alert1 = new Dialog(context, android.R.style.Theme_Black_NoTitleBar);
                        alert1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        alert1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99000000")));
                        alert1.setContentView(R.layout.custom_alert1);

                        TextView tjudul = (TextView)alert1.findViewById(R.id.title);
                        TextView tsub = (TextView)alert1.findViewById(R.id.subjudul);
                        TextView tisi = (TextView)alert1.findViewById(R.id.isi);
                        ImageView foto = (ImageView)alert1.findViewById(R.id.gbr);
                        if(kandang.get(pos).getFoto() != null) {
                            Picasso.with(context).load(kandang.get(pos).getFoto()).centerCrop().placeholder(R.drawable.load).resize(200, 200)
                                    .into(foto);
                        }else {
                            Picasso.with(context).load(R.drawable.ic_person_black_24dp).centerCrop().placeholder(R.drawable.load).resize(200, 200)
                                    .into(foto);
                        }

                        tjudul.setText("Detail Data Kandang");
                        tsub.setText("Nama Kandang : ");
                        tisi.setText(kandang.get(pos).getKandang());
                        Button bkembali = (Button)alert1.findViewById(R.id.kmb);
                        bkembali.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alert1.cancel();
                            }
                        });
                        alert1.show();
                    }

                }
            });
        }

        @Override
        public void onClick(View view) {
            final int posisi = getAdapterPosition();

            if (view.getId() == edit.getId()) {
                if (posisi != RecyclerView.NO_POSITION) {
                    Kandang clickedDataItem = kandang.get(posisi);
                    Intent intent = new Intent(context, EditKandang.class);
                    intent.putExtra("id_kandang", kandang.get(posisi).getId_kandang());
                    intent.putExtra("kandang", kandang.get(posisi).getKandang());
                    intent.putExtra("foto", kandang.get(posisi).getFoto());
                    context.startActivity(intent);
                    Toast.makeText(view.getContext(), "You clicked " + clickedDataItem.getKandang(), Toast.LENGTH_SHORT).show();
                }
            } else if (view.getId() == hapus.getId()) {
                if (posisi != RecyclerView.NO_POSITION) {
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext());
                    alertbox.setMessage("Apakah Anda yakin akan menghapus data ini?");
                    alertbox.setTitle("Warning");
                    alertbox.setIcon(R.drawable.ic_warning_black_24dp);

                    alertbox.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(UtilsApi.BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            ApiService api = retrofit.create(ApiService.class);
                            Call<Result> call = api.hapusKandang(kandang.get(posisi).getId_kandang());
                            call.enqueue(new Callback<Result>() {
                                @Override
                                public void onResponse(Call<Result> call, Response<Result> response) {
                                    String value = response.body().getValue();
                                    String message = response.body().getMessage();
                                    if (value.equals("1")) {
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    }
                                    kandang.remove(posisi);
                                    notifyItemRemoved(posisi);
                                    notifyItemRangeChanged(posisi, kandang.size());
                                }

                                @Override
                                public void onFailure(Call<Result> call, Throwable t) {
                                    t.printStackTrace();
                                    Toast.makeText(context, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                    alertbox.setNegativeButton("Batal",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
                                }
                            });
                    alertbox.show();
                }
            }
        }
    }
}
