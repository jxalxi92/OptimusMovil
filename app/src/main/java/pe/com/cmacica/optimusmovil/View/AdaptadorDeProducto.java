package pe.com.cmacica.optimusmovil.View;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pe.com.cmacica.optimusmovil.R;

/**
 * Created by JHON on 27/03/2016.
 */
public class AdaptadorDeProducto extends RecyclerView.Adapter<AdaptadorDeProducto.ViewHolder> {
    private Cursor cursor;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView monto;
        public TextView etiqueta;
        public TextView fecha;


        public ViewHolder(View v) {
            super(v);
            monto = (TextView) v.findViewById(R.id.monto);
            etiqueta = (TextView) v.findViewById(R.id.etiqueta);
            fecha = (TextView) v.findViewById(R.id.fecha);

        }
    }

    public AdaptadorDeProducto(Context context) {
        this.context= context;

    }

    @Override
    public int getItemCount() {
        if (cursor!=null)
            return cursor.getCount();
        return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        cursor.moveToPosition(i);

        String monto;
        String etiqueta;
        String fecha;

        monto = cursor.getString(1);
        etiqueta = cursor.getString(2);
        fecha = cursor.getString(3);

        viewHolder.monto.setText("$"+monto);
        viewHolder.etiqueta.setText(etiqueta);
        viewHolder.fecha.setText(fecha);
    }

    public void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return cursor;
    }
}
