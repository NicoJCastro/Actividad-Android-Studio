package com.castro702.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactoAdapter extends RecyclerView.Adapter<ContactoAdapter.ContactoViewHolder> implements Filterable {
    private List<Contacto> listaContactos;
    private List<Contacto> listaContactosFiltrados; // Lista para almacenar los contactos filtrados

    public ContactoAdapter(List<Contacto> listaContactos) {
        this.listaContactos = listaContactos;
        this.listaContactosFiltrados = listaContactos; // Inicializar la lista filtrada con la lista original
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el diseño del elemento de lista
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item, parent, false);
        return new ContactoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        Contacto contacto = listaContactosFiltrados.get(position); // Obtener el contacto de la lista filtrada
        holder.nombreTextView.setText(contacto.getNombre());
        holder.apellidoTextView.setText(contacto.getApellido());
        holder.telefonoTextView.setText(contacto.getTelefono());
    }

    @Override
    public int getItemCount() {
        return listaContactosFiltrados.size(); // Usar la lista filtrada para determinar el número de elementos en el RecyclerView
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filtro = constraint.toString().toLowerCase().trim();

                FilterResults results = new FilterResults();

                if (filtro.isEmpty()) {
                    // Si el filtro está vacío, mostrar la lista original
                    results.values = listaContactos;
                } else {
                    // Filtrar la lista de contactos según el texto ingresado
                    List<Contacto> listaFiltrada = new ArrayList<>();
                    for (Contacto contacto : listaContactos) {
                        if (contacto.getNombre().toLowerCase().contains(filtro) ||
                                contacto.getApellido().toLowerCase().contains(filtro) ||
                                contacto.getTelefono().contains(filtro)) {
                            listaFiltrada.add(contacto);
                        }
                    }
                    results.values = listaFiltrada;
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listaContactosFiltrados = (List<Contacto>) results.values;
                notifyDataSetChanged(); // Notificar al RecyclerView que los datos han cambiado
            }
        };
    }

    public static class ContactoViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreTextView;
        public TextView apellidoTextView;
        public TextView telefonoTextView;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);
            apellidoTextView = itemView.findViewById(R.id.apellidoTextView);
            telefonoTextView = itemView.findViewById(R.id.telefonoTextView);
        }
    }
    public void agregarContacto(Contacto nuevoContacto) {
        // Agregar el nuevo contacto a la lista
        this.listaContactos.add(nuevoContacto);

        // Actualizar la lista filtrada
        this.listaContactosFiltrados = this.listaContactos;

        // Notificar al RecyclerView que los datos han cambiado
        notifyDataSetChanged();
    }
}


