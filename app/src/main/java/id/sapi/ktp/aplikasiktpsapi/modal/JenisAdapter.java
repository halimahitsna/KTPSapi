package id.sapi.ktp.aplikasiktpsapi.modal;

import android.app.Application;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import id.sapi.ktp.aplikasiktpsapi.edit.EditJenis;
import id.sapi.ktp.aplikasiktpsapi.R;
import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by ASUS on 3/9/2018.
 */

public class JenisAdapter extends RecyclerView.Adapter<JenisAdapter.ViewHolder>{
    private ArrayList<Jenis> jenis;
    Context context;

    public JenisAdapter(Context context, ArrayList<Jenis> jenis){
        this.context = context;
        this.jenis = jenis;
    }

    public void filterList(ArrayList<Jenis> filterdNames) {
        this.jenis = filterdNames;
        notifyDataSetChanged();
    }

    @Override
    public JenisAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jenis_row, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JenisAdapter.ViewHolder holder, int position) {
holder.id_jenis.setText(jenis.get(position).getId_jenis());
holder.nmjenis.setText(jenis.get(position).getJenis());
    }

    @Override
    public int getItemCount() {
        return jenis.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView id_jenis, nmjenis;
        ImageView edit, hapus;

        public ViewHolder(final View itemView) {
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
                        final Dialog alert1 = new Dialog(itemView.getRootView().getContext(), android.R.style.Theme_Black_NoTitleBar);
                        alert1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        alert1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99000000")));
                        alert1.setContentView(R.layout.custom_alert1);

                        TextView tjudul = (TextView)alert1.findViewById(R.id.title);
                        TextView tsub = (TextView)alert1.findViewById(R.id.subjudul);
                        TextView tisi = (TextView)alert1.findViewById(R.id.isi);
                        ImageView foto = (ImageView)alert1.findViewById(R.id.gbr);
                        foto.setVisibility(View.GONE);

                        tjudul.setText("Detail Jenis Sapi");
                        tsub.setText("Nama Jenis : ");
                        tisi.setText(jenis.get(pos).getJenis());
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
                    Jenis clickedDataItem = jenis.get(posisi);
                    Intent intent = new Intent(context, EditJenis.class);
                    intent.putExtra("id_jenis", jenis.get(posisi).getId_jenis());
                    intent.putExtra("jenis", jenis.get(posisi).getJenis());
                    context.startActivity(intent);
                    Toast.makeText(view.getContext(), "You clicked " + clickedDataItem.getJenis(), Toast.LENGTH_SHORT).show();
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
                            Call<Result> call = api.hapusJenis(jenis.get(posisi).getId_jenis());
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
                                    jenis.remove(posisi);
                                    notifyItemRemoved(posisi);
                                    notifyItemRangeChanged(posisi, jenis.size());
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
