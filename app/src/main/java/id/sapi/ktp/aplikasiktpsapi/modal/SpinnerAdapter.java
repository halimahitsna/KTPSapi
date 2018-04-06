package id.sapi.ktp.aplikasiktpsapi.modal;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import id.sapi.ktp.aplikasiktpsapi.R;

public class SpinnerAdapter extends BaseAdapter{
    Context context;
    ArrayList<Jenis> jenis;

    public SpinnerAdapter(Context context, ArrayList<Jenis> jenis){
        super();
        this.context = context;
        this.jenis =jenis;
    }

    @Override
    public int getCount() {
        return jenis.size();
    }

    @Override
    public Object getItem(int i) {
        return jenis.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Jenis jenis1 = jenis.get(i);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(R.layout.isi_spinner, viewGroup, false);
        TextView id = (TextView) row.findViewById(R.id.idspin);
        id.setText(jenis1.getId_jenis());
        TextView text = (TextView) row.findViewById(R.id.txItemSpin);
        text.setText(jenis1.getJenis());

        return row;
    }

}
