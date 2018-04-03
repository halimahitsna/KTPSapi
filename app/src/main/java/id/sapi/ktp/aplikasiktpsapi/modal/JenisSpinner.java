package id.sapi.ktp.aplikasiktpsapi.modal;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import id.sapi.ktp.aplikasiktpsapi.R;

public class JenisSpinner extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Jenis> jenisItems;

    public JenisSpinner(Activity activity, List<Jenis> jenisItems) {
        this.activity = activity;
        this.jenisItems = jenisItems;
    }

    @Override
    public int getCount() {
        return jenisItems.size();
    }

    @Override
    public Object getItem(int location) {
        return jenisItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        }else{
            convertView = inflater.inflate(R.layout.isi_spinner, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        Jenis m = jenisItems.get(position);
        holder.id.setText(m.getId_jenis());
        holder.txt.setText(String.valueOf(m.getJenis()));
        return convertView;
    }
    static class ViewHolder {
       TextView id, txt;

        ViewHolder(View view) {
            id = (TextView)view.findViewById(R.id.idspin);
            txt = (TextView)view.findViewById(R.id.txItemSpin);
        }
    }
}
