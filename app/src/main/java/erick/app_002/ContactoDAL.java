package erick.app_002;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class ContactoDAL {
    private DataBaseContacts dataBaseContacts; //crear la base de datos
    private SQLiteDatabase sql;
    private Context context;

    public ContactoDAL(Context context) {
        this.context = context;
    }

    public void open(){
        dataBaseContacts  = new DataBaseContacts(context,"BD_contactos",null,1);
        sql = dataBaseContacts.getWritableDatabase();
    }
    public void close(){
        sql.close();
    }
    public long insert(Contacto contacto){
        open();
        long count =0;
        try{
            ContentValues values = new ContentValues();
            values.put("Nombre",contacto.getNombre());
            values.put("Apellido",contacto.getApellido());
            values.put("Edad",contacto.getEdad());
            count = sql.insert("Contactos",null,values);
        }catch (Exception e){
            throw e;
        }
        finally {
            sql.close();
        }
        return count;
    }
    public Contacto selectByCodigo(int codigo){
        Contacto contacto = null;
        open();
        try{
            String select = "SELECT Nombre, Apellido, Edad " +
                    "FROM Contactos " +
                    "WHERE Codigo="+codigo;
            Cursor cursor = sql.rawQuery(select,null);

            if(cursor.moveToFirst()){
                //mostrar la fila de datos (VECTOR DE COLUMNAS)
                contacto.setNombre(cursor.getString(0));
                contacto.setApellido(cursor.getString(1));
                contacto.setEdad(Integer.parseInt(cursor.getString(2)));
            }
        }catch (Exception e){
            throw e;
        }
        finally {
            sql.close();
        }
        return contacto;
    }
    public ArrayList<Contacto> select(){
        ArrayList<Contacto> listaContactos = null;
        open();
        try{
            String select = "SELECT Codigo, Nombre, Apellido, Edad" +
                    "FROM Contactos";
            Cursor cursor = sql.rawQuery(select,null);
            Contacto contacto = new Contacto();
            if(cursor.moveToFirst()){
                listaContactos = new ArrayList<Contacto>();
                do{
                    contacto.setNombre(cursor.getString(0));
                    contacto.setApellido(cursor.getString(1));
                    contacto.setEdad(Integer.parseInt(cursor.getString(2)));
                    listaContactos.add(contacto);
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            throw e;
        }
        finally {
            sql.close();
        }
        return listaContactos;
    }
    public int delete(int codigo){
        int count=0;
        open();
        try{
            count = sql.delete("Contactos","Codigo="+codigo,null);
        }catch (Exception e){
            throw e;
        }
        finally {
            sql.close();
        }
        return count;
    }
    public int update(Contacto contacto){
        int count=0;
        open();
        try{
            ContentValues values = new ContentValues();
            values.put("Nombre",contacto.getNombre());
            values.put("Apellido",contacto.getApellido());
            values.put("Edad",contacto.getEdad());
            //values.put("Codigo",contacto.getCodigo());
            count = sql.update("Contactos",values,"Codigo="+contacto.getCodigo(),null);
        }catch (Exception  e){
            throw e;
        }finally {
            sql.close();
        }
        return count;
    }
}
