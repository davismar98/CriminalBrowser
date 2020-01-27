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

public class AdapterGenerosC extends BaseAdapter {

    ArrayList<GenerosCarcel> listGC ;
    private LayoutInflater layoutInflater;

    public AdapterGenerosC(Context context, ArrayList<GenerosCarcel> listData){
        this.listGC = listData;
        layoutInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return listGC.size();
    }

    @Override
    public Object getItem(int position) {
        return listGC.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.genero_carcel_row, null);
            holder = new ViewHolder();
            holder.carcelView = (TextView) convertView.findViewById(R.id.carcel);
            holder.hombresView=(TextView) convertView.findViewById(R.id.hombres);
            holder.mujeresView=(TextView) convertView.findViewById(R.id.mujeres);
            holder.indefView=(TextView) convertView.findViewById(R.id.indefinido);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GenerosCarcel gc = listGC.get(position);
        holder.carcelView.setText(gc.carcel);
        holder.hombresView.setText(gc.hombres+"");
        holder.mujeresView.setText(gc.mujeres+"");
        holder.indefView.setText(gc.indefinido+"");

        return convertView;
    }
    static class ViewHolder {
        TextView carcelView;
        TextView hombresView;
        TextView mujeresView;
        TextView indefView;

    }
}
