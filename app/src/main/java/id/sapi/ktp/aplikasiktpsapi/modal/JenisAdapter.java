package id.sapi.ktp.aplikasiktpsapi.modal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import id.sapi.ktp.aplikasiktpsapi.HalamanData;
import id.sapi.ktp.aplikasiktpsapi.R;

/**
 * Created by ASUS on 3/1/2018.
 */

public class JenisAdapter extends RecyclerView.Adapter<JenisAdapter.ViewHolder> {

    private ArrayList<Jenis> jenis;
    private Context context;

    public JenisAdapter(Context context, ArrayList<Jenis> jenis){
        this.jenis = jenis;
        this.context = context;
    }

    @Override
    public JenisAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sapi_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JenisAdapter.ViewHolder viewHolder, int i){
        viewHolder.id_kategori.setText(jenis.get(i).getId_kategori() + "");
        viewHolder.nama_kategori.setText(jenis.get(i).getNama_kategori());
        Picasso.with(context)
                .load(jenis.get(i).getGambar1()).resize(100, 100)

                .into(viewHolder.gambar);
    }

    @Override
    public int getItemCount(){
        return jenis.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id_kategori, nama_kategori;
        ImageView gambar;

        public ViewHolder(View view){
            super(view);

            id_kategori = (TextView)view.findViewById(R.id.idSapi);
            nama_kategori = (TextView)view.findViewById(R.id.jenis);
            gambar = (ImageView)view.findViewById(R.id.gbr);

            // on item click
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        Jenis clickedDataItem = jenis.get(pos);
                        Intent intent = new Intent(context,HalamanData.class);
                        intent.putExtra("id_kategori", jenis.get(pos).getId_kategori());
                        intent.putExtra("nama_kategori", jenis.get(pos).getNama_kategori());
                        intent.putExtra("gambar1", jenis.get(pos).getGambar1());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getNama_kategori(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
