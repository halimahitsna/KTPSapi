package id.sapi.ktp.aplikasiktpsapi.modal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import id.sapi.ktp.aplikasiktpsapi.EditData;
import id.sapi.ktp.aplikasiktpsapi.HalamanDaftar;
import id.sapi.ktp.aplikasiktpsapi.HalamanData;
import id.sapi.ktp.aplikasiktpsapi.MainActivity;
import id.sapi.ktp.aplikasiktpsapi.R;
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
    }

    @Override
    public int getItemCount() {
        return sapi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView id_sapi, id_jenis;
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
            int posisi = getAdapterPosition();

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
                    Sapi clickedDataItem = sapi.get(posisi);
                    Intent intent = new Intent(context, EditData.class);
                    intent.putExtra("id_sapi", sapi.get(posisi).getId_sapi());
                    context.startActivity(intent);
                    Toast.makeText(view.getContext(), "You clicked " + clickedDataItem.getId_sapi(), Toast.LENGTH_SHORT).show();

                }
            }
        }
    }
}
