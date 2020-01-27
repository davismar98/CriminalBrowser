package davismar98.browser.criminal.app.com.criminalbrowser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by DavidM on 8/11/2016.
 */

public class AdapterDenuncias extends BaseAdapter{
    private ArrayList<Denuncia> listDenuncias;
    private LayoutInflater layoutInflater;

    public AdapterDenuncias(Context context, ArrayList<Denuncia> listData) {
        this.listDenuncias = listData;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return listDenuncias.size();
    }

    @Override
    public Object getItem(int position) {
        return listDenuncias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterDenuncias.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.denuncia_row, null);
            holder = new ViewHolder();
            holder.id = (TextView) convertView.findViewById(R.id.id);
            holder.fecha=(TextView) convertView.findViewById(R.id.fecha);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Denuncia denuncia = listDenuncias.get(position);
        holder.id.setText(denuncia.id+"");
        holder.fecha.setText(denuncia.fecha);
        return convertView;
    }

    static class ViewHolder {
        TextView id;
        TextView fecha;
    }

}
