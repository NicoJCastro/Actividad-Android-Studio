package com.castro702.myapplication;

import java.util.ArrayList;
import java.util.List;

public class ContactStorage {
    public static List<Contacto> listaContactos = new ArrayList<>();
    public static List<Contacto> getListaContactos() {
        return listaContactos;
    }
    public static void agregarContacto(Contacto contacto) {
        listaContactos.add(contacto);
    }
}