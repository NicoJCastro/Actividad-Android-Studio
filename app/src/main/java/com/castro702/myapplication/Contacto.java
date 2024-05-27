package com.castro702.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Contacto implements Parcelable {
    private int id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String domicilio;
    private String email;
    private String genero;

    public Contacto() {}

    public Contacto(int id, String nombre, String apellido, String telefono, String domicilio, String email, String genero) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.domicilio = domicilio;
        this.email = email;
        this.genero = genero;
    }

    public Contacto(String nombre, String apellido, String telefono, String domicilio, String email, String genero) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.domicilio = domicilio;
        this.email = email;
        this.genero = genero;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    protected Contacto(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        apellido = in.readString();
        telefono = in.readString();
        domicilio = in.readString();
        email = in.readString();
        genero = in.readString();
    }

    public static final Creator<Contacto> CREATOR = new Creator<Contacto>() {
        @Override
        public Contacto createFromParcel(Parcel in) {
            return new Contacto(in);
        }

        @Override
        public Contacto[] newArray(int size) {
            return new Contacto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(telefono);
        dest.writeString(domicilio);
        dest.writeString(email);
        dest.writeString(genero);
    }
}
