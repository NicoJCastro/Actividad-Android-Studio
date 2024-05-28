package com.castro702.myapplication;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactoAdapter extends RecyclerView.Adapter<ContactoAdapter.ContactoViewHolder> implements Filterable {
    private List<Contacto> listaContactos;
    private List<Contacto> listaContactosFiltrados;
    private OnContactClickListener listener;

    public ContactoAdapter(List<Contacto> listaContactos, OnContactClickListener listener) {
        this.listaContactos = listaContactos;
        this.listaContactosFiltrados = listaContactos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item, parent, false);
        return new ContactoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        Contacto contacto = listaContactosFiltrados.get(position);
        holder.nombreTextView.setText(contacto.getNombre());
        holder.apellidoTextView.setText(contacto.getApellido());
        holder.telefonoTextView.setText(contacto.getTelefono());

        holder.editButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(contacto);
            }
        });

        holder.deleteButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(contacto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaContactosFiltrados.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filtro = constraint.toString().toLowerCase().trim();
                FilterResults results = new FilterResults();
                if (filtro.isEmpty()) {
                    results.values = listaContactos;
                } else {
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
                notifyDataSetChanged();
            }
        };
    }

    public static class ContactoViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreTextView;
        public TextView apellidoTextView;
        public TextView telefonoTextView;
        public ImageView editButton;
        public ImageView deleteButton;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);
            apellidoTextView = itemView.findViewById(R.id.apellidoTextView);
            telefonoTextView = itemView.findViewById(R.id.telefonoTextView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    public void addContact(Contacto nuevoContacto) {
        this.listaContactos.add(nuevoContacto);
        this.listaContactosFiltrados = new ArrayList<>(this.listaContactos);
        notifyDataSetChanged();
    }

    public interface OnContactClickListener {
        void onEditClick(Contacto contacto);
        void onDeleteClick(Contacto contacto);
    }
}


