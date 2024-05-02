package com.castro702.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;

import android.widget.EditText;


import java.util.ArrayList;
import java.util.List;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContactListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContactoAdapter adapter;
    private ImageView emptyStateIcon;
    private TextView emptyStateMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerViewContactos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Crear el adaptador solo una vez
        adapter = new ContactoAdapter(ContactStorage.getListaContactos());
        recyclerView.setAdapter(adapter);

        // Inicializar elementos de estado vacío
        emptyStateIcon = findViewById(R.id.emptyStateIcon);
        emptyStateMessage = findViewById(R.id.emptyStateMessage);

        // Mostrar elementos de estado vacío si la lista de contactos está vacía
        actualizarVistaContactos();

        // Encuentra el botón flotante por su ID
        FloatingActionButton fab = findViewById(R.id.fab);

        // Establece un OnClickListener al botón flotante
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crea un Intent para abrir la activity_contact_list
                Intent intent = new Intent(ContactListActivity.this, activity_contact_list.class);
                intent.putExtra("listaContactos", new ArrayList<>(ContactStorage.getListaContactos()));
                startActivityForResult(intent, 1); // Iniciar activity_contact_list con startActivityForResult
            }
        });

        // Encuentra el EditText por su ID
        EditText searchEditText = findViewById(R.id.searchEditText);

        // Establece un TextWatcher al EditText
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No necesitamos hacer nada aquí
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Aquí es donde actualizamos el filtro de nuestro adaptador
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No necesitamos hacer nada aquí
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar la vista de acuerdo a la presencia o ausencia de contactos
        actualizarVistaContactos();
    }

    // Método para actualizar la vista de acuerdo a la presencia o ausencia de contactos
    private void actualizarVistaContactos() {
        if (ContactStorage.getListaContactos().isEmpty()) {
            // Si la lista está vacía, mostrar elementos de estado vacío
            emptyStateIcon.setVisibility(View.VISIBLE);
            emptyStateMessage.setVisibility(View.VISIBLE);
            Log.d("ContactListActivity", "Lista de contactos está vacía");
        } else {
            // Si la lista no está vacía, ocultar elementos de estado vacío
            emptyStateIcon.setVisibility(View.GONE);
            emptyStateMessage.setVisibility(View.GONE);
            Log.d("ContactListActivity", "Lista de contactos no está vacía");
        }
    }

    // Método para actualizar la lista de contactos en el adaptador
    public void actualizarListaContactos() {
        adapter.notifyDataSetChanged();
        actualizarVistaContactos();
    }

    // Método para recibir resultados de activity_contact_list
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            actualizarListaContactos();
        }
    }
}
