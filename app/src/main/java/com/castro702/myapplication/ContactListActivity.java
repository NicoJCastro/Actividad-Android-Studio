package com.castro702.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
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

        recyclerView = findViewById(R.id.recyclerViewContactos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


         //ACA PUEDE ESTAR EL PROBLEMA

        dbHelper dbHelper = new dbHelper(this);
        adapter = new ContactoAdapter(dbHelper.getAllContacts());
        recyclerView.setAdapter(adapter);

        emptyStateIcon = findViewById(R.id.emptyStateIcon);
        emptyStateMessage = findViewById(R.id.emptyStateMessage);


        updateContactsView();

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ContactListActivity.this, activity_contact_list.class);
               // intent.putExtra("listaContactos", new ArrayList<>(ContactStorage.getListaContactos()));
                startActivityForResult(intent, 1);
            }
        });

        EditText searchEditText = findViewById(R.id.searchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                adapter.getFilter().filter(s);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ImageView closeSession = findViewById(R.id.closeSession);
        closeSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ContactListActivity", "Botón de cerrar sesión pulsado");
                SharedPreferences preferences = getSharedPreferences("Nico", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.apply();
                Log.d("ContactListActivity", "Estado de sesión actualizado en SharedPreferences");

                Intent intent = new Intent(ContactListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        updateContactsView();
    }

    private void updateContactsView() {
        dbHelper dbHelper = new dbHelper(this);
        List<Contacto> contactos = dbHelper.getAllContacts();

        if (contactos.isEmpty()) {
            emptyStateIcon.setVisibility(View.VISIBLE);
            emptyStateMessage.setVisibility(View.VISIBLE);
            Log.d("ContactListActivity", "Lista de contactos está vacía");
        } else {
            emptyStateIcon.setVisibility(View.GONE);
            emptyStateMessage.setVisibility(View.GONE);
            Log.d("ContactListActivity", "Lista de contactos no está vacía");
        }
    }
    private void updateContactsList() {
        dbHelper dbHelper = new dbHelper(this);
        adapter = new ContactoAdapter(dbHelper.getAllContacts());
        recyclerView.setAdapter(adapter);
        updateContactsView();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            updateContactsList();
        }
    }


}
