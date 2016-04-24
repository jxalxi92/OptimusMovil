package pe.com.cmacica.optimusmovil.Model;

import java.util.Date;

/**
 * Created by JHON on 27/03/2016.
 */
public class Producto {

    public String nIdProducto;
    public Date dFecha;
    public double nPrecio;
    public String cDescripcion;
    public String cCategoria;
    public Producto(String nIdProducto,Date dFecha,double nPrecio,String cDescripcion,String cCategoria){
        this.nIdProducto= nIdProducto;
        this.dFecha=dFecha;
        this.nPrecio=nPrecio;
        this.cCategoria= cCategoria;
        this.cDescripcion = cDescripcion;
    }

}
