package davismar98.browser.criminal.app.com.criminalbrowser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter{
    private ArrayList<Encarcelado> listEncarcelado;
    private LayoutInflater layoutInflater;
    public CustomListAdapter(Context context, ArrayList<Encarcelado> listData) {
        this.listEncarcelado = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listEncarcelado.size();
    }

    @Override
    public Object getItem(int position) {
        return listEncarcelado.get(position);
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

        Encarcelado encarcelado = listEncarcelado.get(position);
        holder.nombreView.setText(encarcelado.getNombre()+" "+encarcelado.getApellido());
        holder.paisView.setText(encarcelado.getPais());
        holder.generoView.setText(encarcelado.getGenero());
        holder.edadView.setText(encarcelado.getEdad());
        if (holder.imageView != null) {
            new ImageDownloaderTask(holder.imageView)
                    .execute(encarcelado.getFoto());
        }
        if (holder.banderaView != null) {
            new ImageDownloaderTask(holder.banderaView)
                    .execute("http://www.criminalbrowser.com/Paises/"+encarcelado.pais+".png");
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
