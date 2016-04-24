package pe.com.cmacica.optimusmovil.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JHON on 27/03/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);// Crear la tabla "producto"


    }
    private void createTable(SQLiteDatabase database){
        String cmd = "CREATE TABLE" + ContractParaProducto.PRODUCTO +" ("+
                ContractParaProducto.Columnas._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ContractParaProducto.Columnas.PRECIO + " TEXT, "+
                ContractParaProducto.Columnas.CATEGORIA + " TEXT, "+
                ContractParaProducto.Columnas.FECHA + " TEXT, "+
                ContractParaProducto.Columnas.DESCRIPCION + " TEXT, "+
                ContractParaProducto.Columnas.ID_REMOTA + " TEXT UNIQUE, "+
                ContractParaProducto.Columnas.ESTADO + " INTEGER NOT NULL DEFAULT "+ ContractParaProducto.ESTADO_OK+ ","+
                ContractParaProducto.Columnas.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";

        database.execSQL(cmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            db.execSQL("drop table " + ContractParaProducto.PRODUCTO);
        }catch (SQLiteException e){}
        onCreate(db);

    }
}
