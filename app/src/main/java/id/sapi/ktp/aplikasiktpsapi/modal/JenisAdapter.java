package id.sapi.ktp.aplikasiktpsapi.modal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import id.sapi.ktp.aplikasiktpsapi.R;

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
    @Override
    public JenisAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jenis_row, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JenisAdapter.ViewHolder holder, int position) {
holder.id_jenis.setText(jenis.get(position).getId_jenis());
holder.jenis.setText(jenis.get(position).getJenis());
    }

    @Override
    public int getItemCount() {
        return jenis.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_jenis, jenis;

        public ViewHolder(View itemView) {
            super(itemView);
            id_jenis = (TextView)itemView.findViewById(R.id.idJenis);
            jenis = (TextView)itemView.findViewById(R.id.jenis);
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
