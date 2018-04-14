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

import id.sapi.ktp.aplikasiktpsapi.activities.DetailMonitoringKandang;
import id.sapi.ktp.aplikasiktpsapi.R;

/**
 * Created by ASUS on 3/8/2018.
 */

public class MonitoringAdapter extends RecyclerView.Adapter<MonitoringAdapter.ViewHolder> {
    private ArrayList<Kandang> kandang;
    private Context context;

    public MonitoringAdapter(Context context, ArrayList<Kandang> kandang){
        this.kandang = kandang;
        this.context = context;
    }

    @Override
    public MonitoringAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.monitoring_row, viewGroup, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MonitoringAdapter.ViewHolder viewHolder, int i){
        viewHolder.id_kandang.setText(kandang.get(i).getId_kandang());
        viewHolder.txtkandang.setText(kandang.get(i).getKandang());
        viewHolder.txtsuhu.setText(kandang.get(i).getSuhu());
        viewHolder.txtkel.setText(kandang.get(i).getKelembapan());
        viewHolder.txtgas.setText(kandang.get(i).getGas_amonia());
    }

    @Override
    public int getItemCount() {
        return kandang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_kandang, txtkandang, txtsuhu, txtkel, txtgas;
        public ViewHolder(View itemView) {
            super(itemView);
            id_kandang = (TextView)itemView.findViewById(R.id.idKandang);
            txtkandang = (TextView)itemView.findViewById(R.id.kandang);
            txtsuhu= (TextView)itemView.findViewById(R.id.suh);
            txtkel = (TextView)itemView.findViewById(R.id.kel);
            txtgas = (TextView)itemView.findViewById(R.id.gas);

            // on item click
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        Kandang clickedDataItem = kandang.get(pos);
                        Intent intent = new Intent(context,DetailMonitoringKandang.class);
                        intent.putExtra("id_kandang", kandang.get(pos).getId_kandang());
                        intent.putExtra("kandang", kandang.get(pos).getKandang());
                        intent.putExtra("suhu", kandang.get(pos).getSuhu());
                        intent.putExtra("bsuhu", kandang.get(pos).getBatas_suhu());
                        intent.putExtra("kelembapan", kandang.get(pos).getKelembapan());
                        intent.putExtra("bkelembapan", kandang.get(pos).getBatas_kelembapan());
                        intent.putExtra("gas", kandang.get(pos).getGas_amonia());
                        intent.putExtra("bgas", kandang.get(pos).getBatas_gas());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getKandang(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
