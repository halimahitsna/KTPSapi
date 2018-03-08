package id.sapi.ktp.aplikasiktpsapi.modal;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import id.sapi.ktp.aplikasiktpsapi.R;

/**
 * Created by ASUS on 3/7/2018.
 */

public class JenisList extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Jenis> item;

    public JenisList(Activity activity, List<Jenis> item){
        this.activity = activity;
        this.item = item;
    }
    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int i) {
        return item.get(i);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = inflater.inflate(R.layout.isi_spinner, null);

        TextView pendidikan = (TextView) view.findViewById(R.id.txItemSpin);

        Jenis data;
        data = item.get(i);

        pendidikan.setText(data.getJenis());

        return view;
    }
}
