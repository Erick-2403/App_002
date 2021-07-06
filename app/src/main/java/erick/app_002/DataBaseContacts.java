package erick.app_002;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseContacts extends SQLiteOpenHelper {
    public String create_table_contactos="CREATE TABLE Contactos " +
            "(Codigo INTEGER PRIMARY KEY AUTOINCREMENT,Nombre TEXT,Apellido TEXT, Edad Integer)";
    public DataBaseContacts(Context context,String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //crear la estructura de la base de datos(tablas)
        //agregar datos(insert)
        //precio real


        sqLiteDatabase.execSQL(create_table_contactos);

        //carga datos en las tablas
        //INSERT INTO Contactos()........
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //actualizar la estructura de la/s tabla/s
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Contactos");
        //nueva estructura
        sqLiteDatabase.execSQL(create_table_contactos);

    }

}
