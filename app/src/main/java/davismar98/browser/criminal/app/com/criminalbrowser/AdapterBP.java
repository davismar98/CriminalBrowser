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

public class AdapterBP extends BaseAdapter{
    private ArrayList<BuscadoPais> listBP;
    private LayoutInflater layoutInflater;
    public AdapterBP(Context context, ArrayList<BuscadoPais> listData) {
        this.listBP = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listBP.size();
    }

    @Override
    public Object getItem(int position) {
        return listBP.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.buscados_pais_row, null);
            holder = new ViewHolder();
            holder.nombre = (TextView) convertView.findViewById(R.id.delito);
            holder.pais = (TextView) convertView.findViewById(R.id.pais);
            holder.recompensa = (TextView) convertView.findViewById(R.id.recomp);
            holder.foto = (ImageView) convertView.findViewById(R.id.foto);
            holder.bandera = (ImageView) convertView.findViewById(R.id.bandera);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BuscadoPais bp = listBP.get(position);
        holder.nombre.setText(bp.buscado);
        holder.pais.setText(bp.pais);
        holder.recompensa.setText("$"+bp.recompensa+" (COP)");
        if (holder.foto != null) {
            new ImageDownloaderTask(holder.foto)
                    .execute(bp.foto);
        }
        if (holder.bandera != null) {
            new ImageDownloaderTask(holder.bandera)
                    .execute("http://www.criminalbrowser.com/Paises/"+bp.pais+".png");
        }
        return convertView;
    }

    static class ViewHolder {
        TextView pais;
        TextView nombre;
        TextView recompensa;
        ImageView foto;
        ImageView bandera;

    }

}
