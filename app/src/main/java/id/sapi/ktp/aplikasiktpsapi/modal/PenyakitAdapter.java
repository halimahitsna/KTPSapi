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

import id.sapi.ktp.aplikasiktpsapi.EditJenis;
import id.sapi.ktp.aplikasiktpsapi.EditPenyakit;
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

public class PenyakitAdapter extends RecyclerView.Adapter<PenyakitAdapter.ViewHolder>{
    private ArrayList<Penyakit> penyakit;
    Context context;

    public PenyakitAdapter(Context context, ArrayList<Penyakit> penyakit){
        this.context = context;
        this.penyakit = penyakit;
    }
    @Override
    public PenyakitAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jenis_row, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PenyakitAdapter.ViewHolder holder, int position) {
        holder.id_jenis.setText(penyakit.get(position).getId_penyakit());
        holder.nmjenis.setText(penyakit.get(position).getPenyakit());
    }

    @Override
    public int getItemCount() {
        return penyakit.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView id_jenis, nmjenis;
        ImageView edit, hapus;

        public ViewHolder(View itemView) {
            super(itemView);
            id_jenis = (TextView)itemView.findViewById(R.id.idJenis);
            nmjenis = (TextView)itemView.findViewById(R.id.jenis);
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
//                        Jenis clickedDataItem = jenis.get(pos);
//                        Intent intent = new Intent(context,EditJenis.class);
//                        intent.putExtra("id_jenis", jenis.get(pos).getId_jenis());
//                        intent.putExtra("jenis", jenis.get(pos).getJenis());
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(intent);
//                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getJenis(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public void onClick(View view) {
            final int posisi = getAdapterPosition();

            if (view.getId() == edit.getId()) {
                if (posisi != RecyclerView.NO_POSITION) {
                    Penyakit clickedDataItem = penyakit.get(posisi);
                    Intent intent = new Intent(context, EditPenyakit.class);
                    intent.putExtra("id_penyakit", penyakit.get(posisi).getId_penyakit());
                    intent.putExtra("penyakit", penyakit.get(posisi).getPenyakit());
                    context.startActivity(intent);
                    Toast.makeText(view.getContext(), "You clicked " + clickedDataItem.getPenyakit(), Toast.LENGTH_SHORT).show();
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
                            Call<Result> call = api.hapusPenyakit(penyakit.get(posisi).getId_penyakit());
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
                                    penyakit.remove(posisi);
                                    notifyItemRemoved(posisi);
                                    notifyItemRangeChanged(posisi, penyakit.size());
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
