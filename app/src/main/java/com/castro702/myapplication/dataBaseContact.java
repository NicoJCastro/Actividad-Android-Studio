package com.castro702.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class dataBaseContact extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "contactos.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "contactos";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_APELLIDO = "apellido";
    public static final String COLUMN_TELEFONO = "telefono";
    public static final String COLUMN_DOMICILIO = "domicilio";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_GENERO = "genero";

    public dataBaseContact(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("dbHelper", "Base de datos creada");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NOMBRE + " TEXT,"
                + COLUMN_APELLIDO + " TEXT,"
                + COLUMN_TELEFONO + " TEXT,"
                + COLUMN_DOMICILIO + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_GENERO + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        Log.d("dataBaseContact", "Tabla creada");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public long agregarContact(Contacto contacto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, contacto.getNombre());
        values.put(COLUMN_APELLIDO, contacto.getApellido());
        values.put(COLUMN_TELEFONO, contacto.getTelefono());
        values.put(COLUMN_DOMICILIO, contacto.getDomicilio());
        values.put(COLUMN_EMAIL, contacto.getEmail());
        values.put(COLUMN_GENERO, contacto.getGenero());

        long resultado = db.insert(TABLE_NAME, null, values);
        if (resultado != -1) {
            Log.d("dataBaseContact", "Contacto agregado: " + contacto.getNombre() + " " + contacto.getApellido());
        } else {
            Log.e("dataBaseContact", "Error al agregar contacto: " + contacto.getNombre() + " " + contacto.getApellido());
        }

        db.close();
        return resultado;
    }
    public Contacto getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_NOMBRE, COLUMN_APELLIDO, COLUMN_TELEFONO, COLUMN_DOMICILIO, COLUMN_EMAIL, COLUMN_GENERO},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        Contacto contacto = null;

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int nombreIndex = cursor.getColumnIndex(COLUMN_NOMBRE);
            int apellidoIndex = cursor.getColumnIndex(COLUMN_APELLIDO);
            int telefonoIndex = cursor.getColumnIndex(COLUMN_TELEFONO);
            int domicilioIndex = cursor.getColumnIndex(COLUMN_DOMICILIO);
            int emailIndex = cursor.getColumnIndex(COLUMN_EMAIL);
            int generoIndex = cursor.getColumnIndex(COLUMN_GENERO);

            if (idIndex != -1 && nombreIndex != -1 && apellidoIndex != -1 && telefonoIndex != -1 && domicilioIndex != -1 && emailIndex != -1 && generoIndex != -1) {
                contacto = new Contacto(
                        cursor.getInt(idIndex),
                        cursor.getString(nombreIndex),
                        cursor.getString(apellidoIndex),
                        cursor.getString(telefonoIndex),
                        cursor.getString(domicilioIndex),
                        cursor.getString(emailIndex),
                        cursor.getString(generoIndex)
                );
                }
                cursor.close();
            }
        return contacto;
        }


        public List<Contacto> getAllContacts() {
            List<Contacto> contactList = new ArrayList<>();
            String selectQuery = "SELECT  * FROM " + TABLE_NAME;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int idIndex = cursor.getColumnIndex(COLUMN_ID);
                    int nombreIndex = cursor.getColumnIndex(COLUMN_NOMBRE);
                    int apellidoIndex = cursor.getColumnIndex(COLUMN_APELLIDO);
                    int telefonoIndex = cursor.getColumnIndex(COLUMN_TELEFONO);
                    int domicilioIndex = cursor.getColumnIndex(COLUMN_DOMICILIO);
                    int emailIndex = cursor.getColumnIndex(COLUMN_EMAIL);
                    int generoIndex = cursor.getColumnIndex(COLUMN_GENERO);

                    if (idIndex != -1 && nombreIndex != -1 && apellidoIndex != -1 && telefonoIndex != -1 && domicilioIndex != -1 && emailIndex != -1 && generoIndex != -1) {
                        Contacto contacto = new Contacto();
                        contacto.setId(cursor.getInt(idIndex));
                        contacto.setNombre(cursor.getString(nombreIndex));
                        contacto.setApellido(cursor.getString(apellidoIndex));
                        contacto.setTelefono(cursor.getString(telefonoIndex));
                        contacto.setDomicilio(cursor.getString(domicilioIndex));
                        contacto.setEmail(cursor.getString(emailIndex));
                        contacto.setGenero(cursor.getString(generoIndex));
                        contactList.add(contacto);
                    }
                } while (cursor.moveToNext());
            }
            if (cursor != null) {
                cursor.close();
            }
            return contactList;
        }

    public int updateContact(Contacto contacto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, contacto.getNombre());
        values.put(COLUMN_APELLIDO, contacto.getApellido());
        values.put(COLUMN_TELEFONO, contacto.getTelefono());
        values.put(COLUMN_DOMICILIO, contacto.getDomicilio());
        values.put(COLUMN_EMAIL, contacto.getEmail());
        values.put(COLUMN_GENERO, contacto.getGenero());

        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(contacto.getId())});
    }

    public void deleteContact(int contactId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("contactos", "id = ?", new String[]{String.valueOf(contactId)});
        db.close();
    }

}
