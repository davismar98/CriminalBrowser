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
 * Created by DavidM on 22/10/2016.
 */

public class CustomListAdapterB extends BaseAdapter{
    private ArrayList<Buscado> listBuscado;
    private LayoutInflater layoutInflater;

    public CustomListAdapterB(Context context, ArrayList<Buscado> listData) {
        this.listBuscado = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listBuscado.size();
    }

    @Override
    public Object getItem(int position) {
        return listBuscado.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_row_layout, null);
            holder = new ViewHolder();
            holder.nombreView = (TextView) convertView.findViewById(R.id.delito);
            holder.paisView=(TextView) convertView.findViewById(R.id.pais);
            holder.generoView=(TextView) convertView.findViewById(R.id.genero);
            holder.edadView=(TextView) convertView.findViewById(R.id.edad);
            holder.imageView = (ImageView) convertView.findViewById(R.id.thumbImage2);
            holder.banderaView = (ImageView) convertView.findViewById(R.id.bandera);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Buscado buscado = listBuscado.get(position);
        holder.nombreView.setText(buscado.getNombre()+" "+buscado.getApellido());
        holder.paisView.setText(buscado.getPais());
        holder.generoView.setText(buscado.getGenero());
        holder.edadView.setText(buscado.getEdad());

        if (holder.imageView != null) {
            new ImageDownloaderTask(holder.imageView)
                    .execute(buscado.getFoto());
        }
        if (holder.banderaView != null) {
            new ImageDownloaderTask(holder.banderaView)
                    .execute("http://www.criminalbrowser.com/Paises/"+buscado.pais+".png");
        }

        return convertView;
    }

    static class ViewHolder {
        TextView nombreView;
        TextView paisView;
        TextView generoView;
        TextView edadView;
        ImageView imageView;
        ImageView banderaView;
    }
}
