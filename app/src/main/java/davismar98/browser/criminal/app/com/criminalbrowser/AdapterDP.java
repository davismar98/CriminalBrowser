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

public class AdapterDP extends BaseAdapter{
    private ArrayList<DelitoPais> listDP;
    private LayoutInflater layoutInflater;
    public AdapterDP(Context context, ArrayList<DelitoPais> listData) {
        this.listDP= listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listDP.size();
    }

    @Override
    public Object getItem(int position) {
        return listDP.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.delitos_pais_row, null);
            holder = new ViewHolder();
            holder.delito = (TextView) convertView.findViewById(R.id.delito);
            holder.pais = (TextView) convertView.findViewById(R.id.pais);
            holder.coincidencias = (TextView) convertView.findViewById(R.id.coincidencias);
            holder.bandera = (ImageView) convertView.findViewById(R.id.bandera);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DelitoPais dp = listDP.get(position);
        holder.delito.setText(dp.delito);
        holder.pais.setText(dp.pais);
        holder.coincidencias.setText(dp.coincidencias+"");
        if (holder.bandera != null) {
            new ImageDownloaderTask(holder.bandera)
                    .execute("http://www.criminalbrowser.com/Paises/"+dp.pais+".png");
        }
        return convertView;
    }

    static class ViewHolder {
        TextView pais;
        TextView delito;
        TextView coincidencias;
        ImageView bandera;

    }

}
