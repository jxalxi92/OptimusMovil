package pe.com.cmacica.optimusmovil.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.support.annotation.Nullable;



/**
 * Created by JHON on 27/03/2016.
 */
public class ProviderDeProducto extends ContentProvider {
    /**
     * Nombre de la base de datos
     */
    private static final String DATABASE_NAME = "dbcmacica.db";
    /**
     * Versión actual de la base de datos
     */
    private static final int DATABASE_VERSION = 1;
    /**
     * Instancia global del Content Resolver
     */
    private ContentResolver resolver;
    /**
     * Instancia del administrador de BD
     */
    private DatabaseHelper databaseHelper;
    @Override
    public boolean onCreate() {
        // Inicializando gestor BD
        databaseHelper = new DatabaseHelper(
                getContext(),
                DATABASE_NAME,
                null,
                DATABASE_VERSION
                );
        resolver = getContext().getContentResolver();
        return true;
    }

    @Override
    public Cursor query(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {

        // Obtener base de datos
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        // Comparar Uri
        int match = ContractParaProducto.uriMatcher.match(uri);

        Cursor c;
        switch (match){
            case ContractParaProducto.ALLROWS:
                // Consultando todos los registros
                c=db.query(ContractParaProducto.PRODUCTO,projection,
                        selection,selectionArgs,
                        null,null,sortOrder);
                c.setNotificationUri(
                        resolver,
                        ContractParaProducto.CONTENT_URI);
                break;
            case ContractParaProducto.SINGLE_ROW:
                // Consultando un solo registro basado en el Id del Uri
                long idProducto = ContentUris.parseId(uri);
                c=db.query(ContractParaProducto.PRODUCTO,projection,
                        ContractParaProducto.Columnas._ID + " = "+ idProducto,
                        selectionArgs,null,null,sortOrder);
                c.setNotificationUri(
                        resolver,
                        ContractParaProducto.CONTENT_URI);
                break;
            default:
                throw new IllegalArgumentException("URI no soportada: " +uri);


        }
        return c;
    }

    @Override
    public String getType(Uri uri) {

        switch (ContractParaProducto.uriMatcher.match(uri)){

            case ContractParaProducto.ALLROWS:
                return ContractParaProducto.MULTIPLE_MIME;
            case ContractParaProducto.SINGLE_ROW:
                return ContractParaProducto.SINGLE_MIME;
            default:
                throw new IllegalArgumentException("Tipo de producto desconocido: "+ uri);

        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // Validar la uri
        if (ContractParaProducto.uriMatcher.match(uri) != ContractParaProducto.ALLROWS){
            throw new IllegalArgumentException("URI desconocido: "+uri);
        }
        ContentValues contentValues;
        if (values!= null){
            contentValues= new ContentValues(values);
        }else {
            contentValues = new ContentValues();
        }
        // Inserción de nueva fila

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long rowId = db.insert(ContractParaProducto.PRODUCTO,null,contentValues);
        if (rowId> 0){
            Uri uri_producto =ContentUris.withAppendedId(ContractParaProducto.CONTENT_URI,rowId);
            resolver.notifyChange(uri_producto,null,false);
            return uri_producto;
        }
        throw new SQLException("Falla al insertar fila en : " + uri);

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int match = ContractParaProducto.uriMatcher.match(uri);
        int affected;
        switch (match){
            case ContractParaProducto.ALLROWS:
                affected = db.delete(ContractParaProducto.PRODUCTO
                ,selection,
                 selectionArgs       );
                break;
            case ContractParaProducto.SINGLE_ROW:
                long idProducto = ContentUris.parseId(uri);
                affected =db.delete(ContractParaProducto.PRODUCTO,
                        ContractParaProducto.Columnas.ID_REMOTA + "="+idProducto
                                +(!TextUtils.isEmpty(selection) ?
                        " AND ("+selection + ')' : ""),
                        selectionArgs);
                // Notificar cambio asociado a la uri
                resolver.notifyChange(uri,null,false);
                break;
            default:
                throw new IllegalArgumentException("Elemento producto desconocido: "+ uri);

        }
        return affected;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int affected;
        switch (ContractParaProducto.uriMatcher.match(uri)) {
            case ContractParaProducto.ALLROWS:
                affected = db.update(ContractParaProducto.PRODUCTO, values,
                        selection, selectionArgs);
                break;
            case ContractParaProducto.SINGLE_ROW:
                String idProducto = uri.getPathSegments().get(1);
                affected = db.update(ContractParaProducto.PRODUCTO, values,
                        ContractParaProducto.Columnas.ID_REMOTA + "=" + idProducto
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        resolver.notifyChange(uri, null, false);// si se pone solo 2 parámetros la sincronización será automática por defecto
        return affected;
    }
}
