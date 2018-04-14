package id.sapi.ktp.aplikasiktpsapi.modal;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import id.sapi.ktp.aplikasiktpsapi.R;

public class PenyakitSpinner extends BaseAdapter {
    Context c;
    ArrayList<Penyakit> penyakits;

    public PenyakitSpinner(Context context, ArrayList<Penyakit> penyakits) {
        super();
        this.c = context;
        this.penyakits = penyakits;
    }

    @Override
    public int getCount() {
        return penyakits.size();
    }

    @Override
    public Object getItem(int position) {
        return penyakits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Penyakit cur_obj = penyakits.get(position);
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View row = inflater.inflate(R.layout.isi_spinner, parent, false);
        TextView id = (TextView)row.findViewById(R.id.idspin);
        TextView label = (TextView) row.findViewById(R.id.txItemSpin);
        id.setText(cur_obj.getId_penyakit());
        label.setText(cur_obj.getPenyakit());

        return row;
    }
}
