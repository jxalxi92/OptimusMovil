package pe.com.cmacica.optimusmovil.View;


import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import pe.com.cmacica.optimusmovil.R;
import pe.com.cmacica.optimusmovil.provider.ContractParaProducto;
import pe.com.cmacica.optimusmovil.sync.SyncAdapter;
import pe.com.cmacica.optimusmovil.utils.Utilidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class InsertActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText monto;
    Spinner etiqueta;
    TextView fecha;
    EditText descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(this,
                "onCreate",
                Toast.LENGTH_LONG).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
      /*  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        monto = (EditText) findViewById(R.id.monto);
        etiqueta = (Spinner) findViewById(R.id.categoria);
        fecha = (TextView) findViewById(R.id.fecha);
        descripcion = (EditText) findViewById(R.id.descripcion);

        fecha.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        new DateDialog().show(getSupportFragmentManager(), "DatePicker");
                    }
                }
        );
    }

    public void alClickearBoton(View v) {
        Toast.makeText(this,
                "alClickearBoton 1",
                Toast.LENGTH_LONG).show();
        String montoText = monto.getText().toString();
        String etiquetaText = etiqueta.getSelectedItem().toString();
        String fechaText = fecha.getText().toString();
        String descripcionText = descripcion.getText().toString();
        Toast.makeText(this,
                "alClickearBoton 2",
                Toast.LENGTH_LONG).show();
        ContentValues values = new ContentValues();
        values.put(ContractParaProducto.Columnas.PRECIO, montoText);
        values.put(ContractParaProducto.Columnas.DESCRIPCION, etiquetaText);
        values.put(ContractParaProducto.Columnas.FECHA, fechaText);
        values.put(ContractParaProducto.Columnas.DESCRIPCION, descripcionText);
        values.put(ContractParaProducto.Columnas.PENDIENTE_INSERCION, 1);
        Toast.makeText(this,
                "alClickearBoton 3",
                Toast.LENGTH_LONG).show();

        try {
            getContentResolver().insert(ContractParaProducto.CONTENT_URI, values);
        }catch (Exception e){
            Log.i("error",e.getMessage());
            Toast.makeText(this,
                    e.toString()+e.getMessage(),
                    Toast.LENGTH_LONG).show();

        }


        //SyncAdapter.sincronizarAhora(this, true);

        if (Utilidades.materialDesign())
            finishAfterTransition();
        else finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(year + "-" + monthOfYear + "-" + dayOfMonth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outDate = dateFormat.format(date);

        fecha.setText(outDate);
    }

}
