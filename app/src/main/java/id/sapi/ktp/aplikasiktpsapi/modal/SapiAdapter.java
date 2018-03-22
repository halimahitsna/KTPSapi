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

import id.sapi.ktp.aplikasiktpsapi.EditData;
import id.sapi.ktp.aplikasiktpsapi.R;
import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hali on 25/08/2017.
 */

public class SapiAdapter extends RecyclerView.Adapter<SapiAdapter.ViewHolder> {
    private ArrayList<Sapi> sapi;
    private Context context;

    public SapiAdapter(Context context, ArrayList<Sapi> sapi) {
        this.sapi = sapi;
        this.context = context;
    }

    @Override
    public SapiAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sapi_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SapiAdapter.ViewHolder viewHolder, int i) {
        viewHolder.id_sapi.setText(sapi.get(i).getId_sapi());
        viewHolder.id_jenis.setText(sapi.get(i).getId_jenis());
        Picasso.with(context).load(sapi.get(i).getFoto()).resize(100, 100)
                .into(viewHolder.foto);
       /* viewHolder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext());
                alertbox.setMessage("No Internet Connection");
                alertbox.setTitle("Warning");
                alertbox.setIcon(R.drawable.ic_warning_black_24dp);

                alertbox.setNeutralButton("OK",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {

                            }
                        });
                alertbox.show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return sapi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView id_jenis, id_sapi;
        ImageView foto, edit, hapus;

        public ViewHolder(View view) {
            super(view);

            id_jenis = (TextView) view.findViewById(R.id.idSapi);
            id_sapi = (TextView) view.findViewById(R.id.jenis);
            foto = (ImageView) view.findViewById(R.id.gbr);
            edit = (ImageView) view.findViewById(R.id.edit);
            hapus = (ImageView) view.findViewById(R.id.hapus);

            //edit.setTag(R.integer.cast_libraries_material_featurehighlight_pulse_base_alpha, view);
            edit.setOnClickListener(this);
            hapus.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    int posisi = getAdapterPosition();
//
//                    if(posisi != RecyclerView.NO_POSITION){
//                        Sapi clickedDataItem = sapi.get(posisi);
//                        Intent intent = new Intent(context, EditData.class);
//                        intent.putExtra("id_sapi", sapi.get(posisi).getId_sapi());
//                        intent.putExtra("id_jenis", sapi.get(posisi).getId_jenis());
//                        intent.putExtra("id_indukan", sapi.get(posisi).getId_indukan());
//                        intent.putExtra("id_kandang", sapi.get(posisi).getId_kandang());
//                        intent.putExtra("id_pakan", sapi.get(posisi).getId_pakan());
//                        intent.putExtra("bobot_lahir", sapi.get(posisi).getBobot_lahir());
//                        intent.putExtra("bobot_hidup", sapi.get(posisi).getBobot_hidup());
//                        intent.putExtra("warna", sapi.get(posisi).getWarna());
//                        intent.putExtra("umur", sapi.get(posisi).getUmur());
//                        intent.putExtra("foto", sapi.get(posisi).getFoto());
//                        intent.putExtra("tgl_lahir", sapi.get(posisi).getTgl_lahir());
//                        context.startActivity(intent);
//                        Toast.makeText(view.getContext(), "You clicked " +clickedDataItem.getId_sapi(),Toast.LENGTH_SHORT).show();
//                    }
                }
            });
        }

        @Override
        public void onClick(View view) {
            final int posisi = getAdapterPosition();

            if (view.getId() == edit.getId()) {
                if (posisi != RecyclerView.NO_POSITION) {
                    Sapi clickedDataItem = sapi.get(posisi);
                    Intent intent = new Intent(context, EditData.class);
                    intent.putExtra("id_sapi", sapi.get(posisi).getId_sapi());
                    intent.putExtra("id_jenis", sapi.get(posisi).getId_jenis());
                    intent.putExtra("id_indukan", sapi.get(posisi).getId_indukan());
                    intent.putExtra("id_kandang", sapi.get(posisi).getId_kandang());
                    intent.putExtra("id_pakan", sapi.get(posisi).getId_pakan());
                    intent.putExtra("bobot_lahir", sapi.get(posisi).getBobot_lahir());
                    intent.putExtra("bobot_hidup", sapi.get(posisi).getBobot_hidup());
                    intent.putExtra("warna", sapi.get(posisi).getWarna());
                    intent.putExtra("umur", sapi.get(posisi).getUmur());
                    intent.putExtra("foto", sapi.get(posisi).getFoto());
                    intent.putExtra("tgl_lahir", sapi.get(posisi).getTgl_lahir());
                    context.startActivity(intent);
                    Toast.makeText(view.getContext(), "You clicked " + clickedDataItem.getId_sapi(), Toast.LENGTH_SHORT).show();
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
                            Call<Result> call = api.hapus(sapi.get(posisi).getId_sapi());
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
