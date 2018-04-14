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

public class KandangSpinner extends BaseAdapter {
    Context c;
    ArrayList<Kandang> kandangs;

    public KandangSpinner(Context context, ArrayList<Kandang> kandangs) {
        super();
        this.c = context;
        this.kandangs = kandangs;
    }

    @Override
    public int getCount() {
        return kandangs.size();
    }

    @Override
    public Object getItem(int position) {
        return kandangs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Kandang cur_obj = kandangs.get(position);
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View row = inflater.inflate(R.layout.isi_spinner, parent, false);
        TextView id = (TextView)row.findViewById(R.id.idspin);
        TextView label = (TextView) row.findViewById(R.id.txItemSpin);
        id.setText(cur_obj.getId_kandang());
        label.setText(cur_obj.getKandang());

        return row;
    }
}
