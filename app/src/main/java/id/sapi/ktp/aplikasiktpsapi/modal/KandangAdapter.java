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
        viewHolder.txbsuhu.setText(kandang.get(i).getBatas_suhu());
        viewHolder.txbkelem.setText(kandang.get(i).getBatas_kelembapan());
        viewHolder.txbgas.setText(kandang.get(i).getBatas_gas());
    }

    @Override
    public int getItemCount() {
        return kandang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView id_kandang, txtkandang, txbsuhu, txbkelem, txbgas;
        ImageView edit, hapus;
        public ViewHolder(final View itemView) {
            super(itemView);
            id_kandang = (TextView)itemView.findViewById(R.id.idKandang);
            txtkandang = (TextView)itemView.findViewById(R.id.kandang);
            txbsuhu = (TextView)itemView.findViewById(R.id.bsuhu);
            txbkelem = (TextView)itemView.findViewById(R.id.bkelembapan);
            txbgas = (TextView)itemView.findViewById(R.id.bgas);
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
                        Kandang clickedDataItem = kandang.get(pos);
//                        Intent intent = new Intent(context,DetailMonitoringKandang.class);
//                        intent.putExtra("id_kandang", kandang.get(pos).getId_kandang());
//                        intent.putExtra("kandang", kandang.get(pos).getKandang());
//                        intent.putExtra("bsuhu", kandang.get(pos).getBatas_suhu());
//                        intent.putExtra("bkelembapan", kandang.get(pos).getBatas_kelembapan());
//                        intent.putExtra("bgas", kandang.get(pos).getBatas_gas());
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(intent);
//                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getKandang(), Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder alertbox = new AlertDialog.Builder(itemView.getRootView().getContext());
                        alertbox.setMessage("Kandang : " + kandang.get(pos).getKandang());
                        alertbox.setTitle("Detail Kandang");
                        alertbox.setIcon(R.drawable.ic_business_black_24dp);
                        alertbox.setCancelable(true);
                        alertbox.setNeutralButton("Kembali", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                            }
                        });
                        alertbox.show();
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
