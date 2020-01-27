package davismar98.browser.criminal.app.com.criminalbrowser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by DavidM on 8/11/2016.
 */

public class AdapterVisitas extends BaseAdapter {
    private ArrayList<Visita> listVistas;
    private LayoutInflater layoutInflater;

    public AdapterVisitas(Context context, ArrayList<Visita> listData) {
        this.listVistas = listData;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return listVistas.size();
    }

    @Override
    public Object getItem(int position) {
        return listVistas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.visita_row, null);
            holder = new ViewHolder();
            holder.id = (TextView) convertView.findViewById(R.id.id);
            holder.fecha=(TextView) convertView.findViewById(R.id.fecha);
            holder.hora=(TextView) convertView.findViewById(R.id.hora);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Visita visita = listVistas.get(position);
        holder.id.setText(visita.id+"");
        holder.fecha.setText(visita.fecha);
        holder.hora.setText(visita.hora);
        return convertView;
    }

    static class ViewHolder {
        TextView id;
        TextView fecha;
        TextView hora;
    }


}
