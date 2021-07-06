package erick.app_002;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editTextCodigo;
    EditText editTextNombre;
    EditText editTextApellido;
    EditText editTextEdad;
    Button buttonInsertar;
    Button buttonBuscarPorCodigo;
    Button buttonEliminar;
    Button buttonModificar;
    Button buttonListaDeContactos;

    ListView listViewContactos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextCodigo=findViewById(R.id.editTextCodigo);
        editTextNombre=findViewById(R.id.editTextNombre);
        editTextApellido=findViewById(R.id.editTextApellido);
        editTextEdad=findViewById(R.id.editTextEdad);

        buttonBuscarPorCodigo=findViewById(R.id.buttonBuscarPorCodigo);
        buttonInsertar=findViewById(R.id.buttonInsertar);
        buttonEliminar=findViewById(R.id.buttonEliminar);
        buttonModificar=findViewById(R.id.buttonModificar);
        buttonListaDeContactos=findViewById(R.id.buttonListaDeContactos);

        listViewContactos=findViewById(R.id.listViewContactos);
    }
    public void onCLicInsertar(View view){
        insertarConClases();
    }

    private void insertarConClases() {
        ContactoDAL dal = new ContactoDAL(this);
        dal.open();
        String nombre = editTextNombre.getText().toString();
        String apellido = editTextApellido.getText().toString();
        String edad = editTextEdad.getText().toString();//20

        Contacto contacto = new Contacto();
        contacto.setNombre(nombre);
        contacto.setApellido(apellido);
        contacto.setEdad(Integer.parseInt(edad));

        long cantidad = dal.insert(contacto);

        if(cantidad==0)
            Toast.makeText(this,"Contacto NO Insertado",Toast.LENGTH_LONG).show();

        limpiarControles();
    }

    private void insertarSinClases() {
        //Crear la base de datos
        DataBaseContacts baseDatos = new DataBaseContacts(this,"contactosBD",
                null,1);
        //Abrir la base de datos
        SQLiteDatabase sql = baseDatos.getWritableDatabase();
        String nombre = editTextNombre.getText().toString();
        String apellido = editTextApellido.getText().toString();
        String edad = editTextEdad.getText().toString();//20

        //sql.execSQL("INSERT INTO Contactos(Nombre, Apellido, Edad) VALUES('John','Doe',20)");
        //sql.execSQL("INSERT INTO Contactos(Nombre, Apellido, Edad) VALUES('"+nombre+"','"+apellido+"',"+edad+")");

        ContentValues values = new ContentValues();
        values.put("Nombre",nombre);
        values.put("Apellido",apellido);
        values.put("Edad",edad);

        //Enviar a la base de datos
        long cantidad =sql.insert("Contactos",null,values);
        //long cantidad
        if(cantidad>0){
            Toast.makeText(this,"Contacto Insertado",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Contacto NO Insertado",Toast.LENGTH_LONG).show();
}
        //cerrar la base
        sql.close();

        limpiarControles();
        //AUTONUMERICO
        //SELECT last_insert_rowid();
        //SELECT CODIGO FROM COUNT
        //Toast.makeText(this,"Contacto Insertado",Toast.LENGTH_LONG).show();
    }

    private void limpiarControles(){
        editTextCodigo.setText("");
        editTextNombre.setText("");
        editTextApellido.setText("");
        editTextEdad.setText("");
    }
    public void onCLicBuscarPorCodigo(View view){
        buscarConClases();
    }

    private void buscarConClases() {
        //Crear la base de datos
        ContactoDAL dal = new ContactoDAL(this);
        //Abrir la base de datos
        dal.open();
        int codigo = Integer.parseInt(editTextCodigo.getText().toString());
        Contacto contacto = dal.selectByCodigo(codigo);
        if(contacto.getCodigo() == codigo){
            editTextEdad.setText(contacto.getNombre());
            editTextApellido.setText(contacto.getApellido());
            editTextEdad.setText(contacto.getEdad());
        }else{
            Toast.makeText(this, "ERROR: no existe un contacto con el id requerido", Toast.LENGTH_SHORT).show();
            limpiarControles();

        }
    }

    private void buscarSinClases() {
        //Crear la base de datos
        DataBaseContacts baseDatos = new DataBaseContacts(this,"contactosBD",
                null,1);
        //Abrir la base de datos
        SQLiteDatabase sql = baseDatos.getReadableDatabase();
        String codigo = editTextCodigo.getText().toString();
        //buscar el registro en la base de datos
        //String select = "SELECT * FROM Contactos WHERE Codigo="+codigo;
        String select = "SELECT Nombre, Apellido, Edad " +
                "FROM Contactos " +
                "WHERE Codigo="+codigo;
        Cursor cursor = sql.rawQuery(select,null);
        //where(cursor.moveToNext())
        if(cursor.moveToFirst()){
            //mostrar la fila de datos (VECTOR DE COLUMNAS)
            editTextNombre.setText(cursor.getString(0));
            editTextApellido.setText(cursor.getString(1));
            editTextEdad.setText(cursor.getString(2));
        }else{
            limpiarControles();
            Toast.makeText(this,"No existe un contacto con el codigo requerido",Toast.LENGTH_LONG).show();
        }
        cursor.close();
        sql.close();
    }

    public void onCLicEliminar(View view){
        eliminarConClases();
    }

    private void eliminarConClases() {
        //Crear la base de datos
        ContactoDAL dal = new ContactoDAL(this);
        //Abrir la base de datos
        dal.open();
        int codigo = Integer.parseInt(editTextCodigo.getText().toString());
        long cantidad = dal.delete(codigo);
        if(cantidad==0){
            Toast.makeText(this,"Contacto no eliminado",Toast.LENGTH_SHORT).show();
        }
        limpiarControles();
    }

    private void eliminarSinClases() {
        //Crear la base de datos
        DataBaseContacts baseDatos = new DataBaseContacts(this,"contactosBD",
                null,1);
        //Abrir la base de datos
        SQLiteDatabase sql = baseDatos.getWritableDatabase();
        String codigo = editTextCodigo.getText().toString();
        //sql.execSQL("DELETE WHERE Codigo = " +codigo+ "");
        int cantidad = sql.delete("Contactos","Codigo="+codigo,null);
        if(cantidad>0){
            Toast.makeText(this,"Contacto eliminado",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Contacto no eliminado",Toast.LENGTH_SHORT).show();
        }
        limpiarControles();
        sql.close();
    }

    public void onCLicModificar(View view){
        modificarConClases();

    }

    private void modificarConClases() {
        //Crear la base de datos
        ContactoDAL dal = new ContactoDAL(this);
        //Abrir la base de datos
        dal.open();
        int codigo = Integer.parseInt(editTextCodigo.getText().toString());
        String nombre = editTextNombre.getText().toString();
        String apellido = editTextApellido.getText().toString();
        String edad = editTextEdad.getText().toString();//20

        Contacto contacto = new Contacto();
        contacto.setNombre(nombre);
        contacto.setApellido(apellido);
        contacto.setEdad(Integer.parseInt(edad));

        //Enviar a la base de datos
        long cantidad = dal.update(contacto);
        //long cantidad
        if(cantidad==0){
            Toast.makeText(this,"Contacto NO modificado",Toast.LENGTH_LONG).show();
        }
    }

    private void modificarSinClases() {
        //Crear la base de datos
        DataBaseContacts baseDatos = new DataBaseContacts(this,"contactosBD",
                null,1);
        //Abrir la base de datos
        SQLiteDatabase sql = baseDatos.getWritableDatabase();
        String codigo = editTextCodigo.getText().toString();
        String nombre = editTextNombre.getText().toString();
        String apellido = editTextApellido.getText().toString();
        String edad = editTextEdad.getText().toString();//20

        ContentValues values = new ContentValues();
        values.put("Nombre",nombre);
        values.put("Apellido",apellido);
        values.put("Edad",edad);
        values.put("Codigo",codigo);

        //Enviar a la base de datos
        int cantidad = sql.update("Contactos",values,"Codigo="+codigo,null);
        //long cantidad
        if(cantidad>0){
            Toast.makeText(this,"Contacto modificado",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Contacto NO modificado",Toast.LENGTH_LONG).show();
        }
        //cerrar la base
        sql.close();

        //limpiarControles();
    }

    public void onCLicListaDeContactos(View view){
        listarConClases();

    }

    private void listarConClases() {
        ContactoDAL dal =  new ContactoDAL(this);
        dal.open();
        ArrayAdapter<Contacto> adapter = new ArrayAdapter<Contacto>(this,
                android.R.layout.simple_list_item_1,dal.select());
        listViewContactos.setAdapter(adapter);
    }

    private void listarSinClases() {
        //Crear la base de datos
        DataBaseContacts baseDatos = new DataBaseContacts(this,"contactosBD",
                null,1);
        //Abrir la base de datos
        SQLiteDatabase sql = baseDatos.getWritableDatabase();
    }
}