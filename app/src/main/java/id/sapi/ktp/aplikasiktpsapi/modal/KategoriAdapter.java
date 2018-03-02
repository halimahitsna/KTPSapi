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

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.ViewHolder> {

    private ArrayList<Kategori> kategori;
    private Context context;

    public KategoriAdapter(Context context, ArrayList<Kategori> kategori){
        this.kategori = kategori;
        this.context = context;
    }

    @Override
    public KategoriAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.kategori_grid, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KategoriAdapter.ViewHolder viewHolder, int i){
        viewHolder.id_kategori.setText(kategori.get(i).getId_kategori() + "");
        viewHolder.nama_kategori.setText(kategori.get(i).getNama_kategori());
        Picasso.with(context)
                .load(kategori.get(i).getGambar1()).resize(100, 100)
                .into(viewHolder.gambar);
    }

    @Override
    public int getItemCount(){
        return kategori.size();
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
                        Kategori clickedDataItem = kategori.get(pos);
                        Intent intent = new Intent(context,MainActivity.class);
                        intent.putExtra("id_kategori", kategori.get(pos).getId_kategori());
                        intent.putExtra("nama_kategori", kategori.get(pos).getNama_kategori());
                        intent.putExtra("gambar1", kategori.get(pos).getGambar1());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getNama_kategori(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
