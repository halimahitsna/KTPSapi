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

import id.sapi.ktp.aplikasiktpsapi.DetailMonitoringKandang;
import id.sapi.ktp.aplikasiktpsapi.EditJenis;
import id.sapi.ktp.aplikasiktpsapi.EditPakan;
import id.sapi.ktp.aplikasiktpsapi.R;
import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ASUS on 3/26/2018.
 */

public class PakanAdapter extends RecyclerView.Adapter<PakanAdapter.ViewHolder>{
    private ArrayList<Pakan> pakan;
    Context context;

    public PakanAdapter(Context context, ArrayList<Pakan> pakan){
        this.context = context;
        this.pakan = pakan;
    }
    @Override
    public PakanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pakan_row, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PakanAdapter.ViewHolder holder, int position) {
        holder.id_pakan.setText(pakan.get(position).getId_pakan());
        holder.nmpakan.setText(pakan.get(position).getPakan());
        holder.txstat.setText(pakan.get(position).getStatus());
        holder.txjml.setText(pakan.get(position).getJumlah());
    }

    @Override
    public int getItemCount() {
        return pakan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView id_pakan, nmpakan, txjml, txstat;
        ImageView edit, hapus;

        public ViewHolder(View itemView) {
            super(itemView);
            id_pakan = (TextView)itemView.findViewById(R.id.idPakan);
            nmpakan = (TextView)itemView.findViewById(R.id.pakan);
            txjml = (TextView)itemView.findViewById(R.id.jml);
            txstat = (TextView)itemView.findViewById(R.id.stat);
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
                        Pakan clickedDataItem = pakan.get(pos);
                        Intent intent = new Intent(context,DetailMonitoringKandang.class);
                        intent.putExtra("id_pakan", pakan.get(pos).getId_pakan());
                        intent.putExtra("pakan", pakan.get(pos).getPakan());
                        intent.putExtra("jumlah", pakan.get(pos).getJumlah());
                        intent.putExtra("status", pakan.get(pos).getStatus());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getPakan(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public void onClick(View view) {
            final int posisi = getAdapterPosition();

            if (view.getId() == edit.getId()) {
                if (posisi != RecyclerView.NO_POSITION) {
                    Pakan clickedDataItem = pakan.get(posisi);
                    Intent intent = new Intent(context, EditPakan.class);
                    intent.putExtra("id_pakan", pakan.get(posisi).getId_pakan());
                    intent.putExtra("pakan", pakan.get(posisi).getPakan());
                    context.startActivity(intent);
                    Toast.makeText(view.getContext(), "You clicked " + clickedDataItem.getPakan(), Toast.LENGTH_SHORT).show();
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
                            Call<Result> call = api.hapusPakan(pakan.get(posisi).getId_pakan());
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
