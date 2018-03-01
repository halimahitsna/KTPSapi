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

import id.sapi.ktp.aplikasiktpsapi.Beranda;
import id.sapi.ktp.aplikasiktpsapi.HalamanData;
import id.sapi.ktp.aplikasiktpsapi.R;

/**
 * Created by ASUS on 2/28/2018.
 */
public class SapiAdapter extends RecyclerView.Adapter<SapiAdapter.ViewHolder> {

    private ArrayList<Sapi> sapis;
    private Context context;

    public SapiAdapter(Context context, ArrayList<Sapi> sapis){
        this.sapis = sapis;
        this.context = context;
    }

    @Override
    public SapiAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sapi_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SapiAdapter.ViewHolder viewHolder, int i){
        viewHolder.id_sapi.setText(sapis.get(i).getId_sapi()+"");
        viewHolder.id_jenis.setText(sapis.get(i).getId_jenis()+"");
        viewHolder.id_indukan.setText(sapis.get(i).getId_indukan()+"");
        Picasso.with(context)
                .load(sapis.get(i).getFoto()).resize(100, 100)

                .into(viewHolder.foto);
    }

    @Override
    public int getItemCount(){
        return sapis.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id_sapi, id_jenis, id_indukan;
        ImageView foto;

        public ViewHolder(View view){

            super(view);

            id_sapi = (TextView)view.findViewById(R.id.idSapi);
            id_jenis = (TextView)view.findViewById(R.id.jenis);
            id_indukan = (TextView) view.findViewById(R.id.indukan);
            foto = (ImageView) view.findViewById(R.id.gbr);
            // on item click
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        Sapi clickedDataItem = sapis.get(pos);
                        Intent intent = new Intent(context,HalamanData.class);
                        intent.putExtra("id_sapi", sapis.get(pos).getId_sapi());
                        intent.putExtra("id_jenis", sapis.get(pos).getId_jenis());
                        intent.putExtra("id_indukan", sapis.get(pos).getId_indukan());
                        intent.putExtra("foto", sapis.get(pos).getFoto());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getId_sapi(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

}