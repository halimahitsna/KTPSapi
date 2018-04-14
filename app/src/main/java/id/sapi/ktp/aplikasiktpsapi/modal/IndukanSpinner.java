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

public class IndukanSpinner extends BaseAdapter {
    Context c;
    ArrayList<Indukan> indukans;

    public IndukanSpinner(Context context, ArrayList<Indukan> indukans) {
        super();
        this.c = context;
        this.indukans = indukans;
    }

    @Override
    public int getCount() {
        return indukans.size();
    }

    @Override
    public Object getItem(int position) {
        return indukans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Indukan cur_obj = indukans.get(position);
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View row = inflater.inflate(R.layout.isi_spinner, parent, false);
        TextView id = (TextView)row.findViewById(R.id.idspin);
        TextView label = (TextView) row.findViewById(R.id.txItemSpin);
        id.setText(cur_obj.getId_indukan());
        label.setText(cur_obj.getIndukan());

        return row;
    }
}
