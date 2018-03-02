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

    public SapiAdapter(Context context, ArrayList<Sapi> sapi){
        this.sapi = sapi;
        this.context = context;
    }

    @Override
    public SapiAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.kategori_grid, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SapiAdapter.ViewHolder viewHolder, int i){
        viewHolder.id_kategori.setText(sapi.get(i).getId_sapi());
        viewHolder.nama_kategori.setText(sapi.get(i).getId_jenis());
        Picasso.with(context)
                .load(sapi.get(i).getFoto()).resize(100, 100)
                .into(viewHolder.gambar);
    }

    @Override
    public int getItemCount(){
        return sapi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id_kategori, nama_kategori;
        ImageView gambar;

        public ViewHolder(View view){
            super(view);

            id_kategori = (TextView)view.findViewById(R.id.idPaket);
            nama_kategori = (TextView)view.findViewById(R.id.nmPaket);
            gambar = (ImageView)view.findViewById(R.id.gbr);

            // on item click
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        Sapi clickedDataItem = sapi.get(pos);
                        Intent intent = new Intent(context,MainActivity.class);
                        intent.putExtra("id_sapi", sapi.get(pos).getId_sapi());
                        intent.putExtra("id_jenis", sapi.get(pos).getId_jenis());
                        intent.putExtra("foto", sapi.get(pos).getFoto());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getId_sapi(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
