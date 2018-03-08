package id.sapi.ktp.aplikasiktpsapi.modal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import id.sapi.ktp.aplikasiktpsapi.EditJenis;
import id.sapi.ktp.aplikasiktpsapi.R;

/**
 * Created by ASUS on 3/8/2018.
 */

public class KandangAdapter extends RecyclerView.Adapter<KandangAdapter.ViewHolder> {
    private ArrayList<Kandang> kandang;
    private Context context;

    public KandangAdapter(Context context, ArrayList<Kandang> kandang){
        this.kandang = kandang;
        this.context = context;
    }

    @Override
    public KandangAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.jenis_row, viewGroup, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KandangAdapter.ViewHolder viewHolder, int i){
        viewHolder.id_kandang.setText(kandang.get(i).getId_kandang());
        viewHolder.kandang.setText(kandang.get(i).getKandang());
    }

    @Override
    public int getItemCount() {
        return kandang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView id_kandang, kandang;
        public ViewHolder(View itemView) {
            super(itemView);
            id_kandang = (TextView)itemView.findViewById(R.id.idJenis);
            kandang = (TextView)itemView.findViewById(R.id.jenis);
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
    }
}
