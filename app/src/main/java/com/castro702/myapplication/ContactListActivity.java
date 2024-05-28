package com.castro702.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ContactListActivity extends AppCompatActivity implements ContactoAdapter.OnContactClickListener {

    private RecyclerView recyclerView;
    private ContactoAdapter adapter;
    private ImageView emptyStateIcon;
    private TextView emptyStateMessage;
    private dataBaseContact dataBaseContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);

        dataBaseContact = new dataBaseContact(this);

        recyclerView = findViewById(R.id.recyclerViewContactos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ContactoAdapter(dataBaseContact.getAllContacts(), this);
        recyclerView.setAdapter(adapter);

        emptyStateIcon = findViewById(R.id.emptyStateIcon);
        emptyStateMessage = findViewById(R.id.emptyStateMessage);

        updateContactsView();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(ContactListActivity.this, activity_contact_list.class);
            startActivityForResult(intent, 1);
        });

        EditText searchEditText = findViewById(R.id.searchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        ImageView closeSession = findViewById(R.id.closeSession);
        closeSession.setOnClickListener(v -> {
            Log.d("ContactListActivity", "Botón de cerrar sesión pulsado");
            SharedPreferences preferences = getSharedPreferences("Nico", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.apply();
            Log.d("ContactListActivity", "Estado de sesión actualizado en SharedPreferences");

            Intent intent = new Intent(ContactListActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateContactsView();
    }

    private void updateContactsView() {
        List<Contacto> contactos = dataBaseContact.getAllContacts();
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
        adapter = new ContactoAdapter(dataBaseContact.getAllContacts(), this);
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

    @Override
    public void onEditClick(Contacto contacto) {
        Intent intent = new Intent(this, EditContactActivity.class);
        intent.putExtra("contact_id", contacto.getId());
        startActivityForResult(intent, 1);
    }

    @Override
    public void onDeleteClick(Contacto contacto) {
        dataBaseContact.deleteContact(contacto.getId());
        updateContactsList();
        Toast.makeText(this, "Contacto eliminado", Toast.LENGTH_SHORT).show();
    }
}
