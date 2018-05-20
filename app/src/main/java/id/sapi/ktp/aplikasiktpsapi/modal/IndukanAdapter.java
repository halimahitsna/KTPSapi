package id.sapi.ktp.aplikasiktpsapi.modal;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import id.sapi.ktp.aplikasiktpsapi.edit.EditIndukan;
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

public class IndukanAdapter extends RecyclerView.Adapter<IndukanAdapter.ViewHolder>{
    private ArrayList<Indukan> indukan;
    Context context;

    public IndukanAdapter(Context context, ArrayList<Indukan> indukan){
        this.context = context;
        this.indukan = indukan;
    }

    public void filterList(ArrayList<Indukan> filterdNames) {
        this.indukan = filterdNames;
        notifyDataSetChanged();
    }

    @Override
    public IndukanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jenis_row, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IndukanAdapter.ViewHolder holder, int position) {
        holder.id_jenis.setText(indukan.get(position).getId_indukan());
        holder.nmjenis.setText(indukan.get(position).getIndukan());
    }

    @Override
    public int getItemCount() {
        return indukan.size();
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
                @SuppressLint("RestrictedApi")
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        Indukan clickedDataItem = indukan.get(pos);
                        final Dialog alert1 = new Dialog(itemView.getRootView().getContext(), android.R.style.Theme_Black_NoTitleBar);
                        alert1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        alert1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99000000")));
                        alert1.setContentView(R.layout.custom_alert1);

                        TextView tjudul = (TextView)alert1.findViewById(R.id.title);
                        TextView tsub = (TextView)alert1.findViewById(R.id.subjudul);
                        TextView tisi = (TextView)alert1.findViewById(R.id.isi);
                        ImageView foto = (ImageView)alert1.findViewById(R.id.gbr);
                        foto.setVisibility(View.GONE);

                        tjudul.setText("Detail Indukan Sapi");
                        tsub.setText("Nama Indukan : ");
                        tisi.setText(indukan.get(pos).getIndukan());
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
                    Indukan clickedDataItem = indukan.get(posisi);
                    Intent intent = new Intent(context, EditIndukan.class);
                    intent.putExtra("id_indukan", indukan.get(posisi).getId_indukan());
                    intent.putExtra("indukan", indukan.get(posisi).getIndukan());
                    context.startActivity(intent);
                    Toast.makeText(view.getContext(), "You clicked " + clickedDataItem.getIndukan(), Toast.LENGTH_SHORT).show();
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
                            Call<Result> call = api.hapusIndukan(indukan.get(posisi).getId_indukan());
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
                                    indukan.remove(posisi);
                                    notifyItemRemoved(posisi);
                                    notifyItemRangeChanged(posisi, indukan.size());
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
