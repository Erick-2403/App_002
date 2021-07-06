package erick.app_002;

import android.content.Context;

import java.util.ArrayList;

public class ContactoLL {
    Context context;

    public ContactoLL(Context context) {
        this.context = context;
    }
    public ArrayList<Contacto> select(){
        ArrayList<Contacto> list = new ArrayList<Contacto>();
        ContactoDAL dal = new ContactoDAL(context);
        list = dal.select();
        return list;
    }
}
