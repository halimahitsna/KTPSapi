package id.sapi.ktp.aplikasiktpsapi.modal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import id.sapi.ktp.aplikasiktpsapi.EditJenis;
import id.sapi.ktp.aplikasiktpsapi.R;
import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.database.LaporanDB;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LaporanAdapter extends RecyclerView.Adapter<LaporanAdapter.NoteVH> {
    Context context;
    List<LaporanDB> laporanDBList;

    OnItemClickListener clickListener;

    public LaporanAdapter(Context context, List<LaporanDB> laporanDBS) {
        this.context = context;
        this.laporanDBList = laporanDBS;

    }

    @Override
    public NoteVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.laporan_row, parent, false);
        NoteVH viewHolder = new NoteVH(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NoteVH holder, int position) {
        holder.id.setText(laporanDBList.get(position).getId_laporan());
        holder.judul.setText(laporanDBList.get(position).getJudul_laporan());
        holder.isi.setText(laporanDBList.get(position).getIsi_laporan());
        holder.tgl.setText(laporanDBList.get(position).getTanggal());
    }

    @Override
    public int getItemCount() {
        return laporanDBList.size();
    }

    class NoteVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView id,judul, isi, tgl ;
        ImageView edit, hapus;

        public NoteVH(View itemView) {
            super(itemView);

            id = (TextView) itemView.findViewById(R.id.idlap);
            judul = (TextView) itemView.findViewById(R.id.judul);
            isi = (TextView) itemView.findViewById(R.id.isi);
            tgl = (TextView) itemView.findViewById(R.id.tgl);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            hapus = (ImageView) itemView.findViewById(R.id.hapus);

            edit.setOnClickListener(this);
            hapus.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        LaporanDB clickedDataItem = laporanDBList.get(pos);

                    }
                }
            });
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(view, getAdapterPosition());
            final int posisi = getAdapterPosition();

            if (view.getId() == edit.getId()) {
                if (posisi != RecyclerView.NO_POSITION) {
                    LaporanDB clickedDataItem = laporanDBList.get(posisi);
                    Intent intent = new Intent(context, EditJenis.class);
                    intent.putExtra("id_laporan", laporanDBList.get(posisi).getId_laporan());
                    intent.putExtra("judul_laporan", laporanDBList.get(posisi).getJudul_laporan());
                    intent.putExtra("isi_laporan", laporanDBList.get(posisi).getIsi_laporan());
                    intent.putExtra("tanggal", laporanDBList.get(posisi).getTanggal());
                    context.startActivity(intent);
                    Toast.makeText(view.getContext(), "You clicked " + clickedDataItem.getJudul_laporan(), Toast.LENGTH_SHORT).show();
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
                            LaporanDB laporan = LaporanDB.findById(LaporanDB.class, Long.valueOf(laporanDBList.get(posisi).getId_laporan()));
                            laporan.delete();
                            laporanDBList.remove(posisi);
                            notifyItemRemoved(posisi);
                            notifyItemRangeChanged(posisi, laporanDBList.size());
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

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }


}
