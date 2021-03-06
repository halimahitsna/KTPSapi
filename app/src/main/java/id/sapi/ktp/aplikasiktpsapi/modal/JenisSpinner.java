package id.sapi.ktp.aplikasiktpsapi.modal;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.sapi.ktp.aplikasiktpsapi.R;

public class JenisSpinner extends BaseAdapter {
    Context c;
    ArrayList<Jenis> jenisArrayList;

    public JenisSpinner(Context context, ArrayList<Jenis> jenisArrayList) {
        super();
        this.c = context;
        this.jenisArrayList = jenisArrayList;
    }

    @Override
    public int getCount() {
        return jenisArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return jenisArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Jenis cur_obj = jenisArrayList.get(position);
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View row = inflater.inflate(R.layout.isi_spinner, parent, false);
        TextView id = (TextView)row.findViewById(R.id.idspin);
        TextView label = (TextView) row.findViewById(R.id.txItemSpin);
        id.setText(cur_obj.getId_jenis());
        label.setText(cur_obj.getJenis());

        return row;
    }
}
