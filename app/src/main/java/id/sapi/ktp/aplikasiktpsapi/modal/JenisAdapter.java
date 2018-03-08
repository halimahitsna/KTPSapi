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

import id.sapi.ktp.aplikasiktpsapi.EditJenis;
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.jenis_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JenisAdapter.ViewHolder viewHolder, int i){
        viewHolder.id_jenis.setText(jenis.get(i).getId_jenis());
        viewHolder.nmjenis.setText(jenis.get(i).getJenis());


    }

    @Override
    public int getItemCount(){
        return jenis.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id_jenis, nmjenis;

        public ViewHolder(View view){
            super(view);

            id_jenis = (TextView)view.findViewById(R.id.idJenis);
            nmjenis = (TextView)view.findViewById(R.id.jenis);

            // on item click
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        Jenis clickedDataItem = jenis.get(pos);
                        Intent intent = new Intent(context,EditJenis.class);
                        intent.putExtra("id_jenis", jenis.get(pos).getId_jenis());
                        intent.putExtra("jenis", jenis.get(pos).getJenis());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getJenis(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
