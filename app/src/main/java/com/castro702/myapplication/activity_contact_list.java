package com.castro702.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class activity_contact_list extends AppCompatActivity {

    private EditText editTextNombre, editTextApellido, editTextTelefono, editTextDomicilio, editTextEmail;
    private RadioGroup radioGroupGenero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);


        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellido = findViewById(R.id.editTextApellido);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        editTextDomicilio = findViewById(R.id.editTextDomicilio);
        editTextEmail = findViewById(R.id.editTextEmail);
        radioGroupGenero = findViewById(R.id.radioGroupGenero);


        findViewById(R.id.saveContactIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContacto();
            }
        });


        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void saveContacto() {

        String nombre = editTextNombre.getText().toString().trim();
        String apellido = editTextApellido.getText().toString().trim();
        String telefono = editTextTelefono.getText().toString().trim();
        String domicilio = editTextDomicilio.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String genero = getSelectedGenre();


        if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || domicilio.isEmpty() || email.isEmpty() || genero.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        Contacto newContacto = new Contacto(nombre, apellido, telefono);
        ContactStorage.agregarContacto(newContacto);
        setResult(RESULT_OK);
        finish();
    }

    private String getSelectedGenre() {
        int radioButtonId = radioGroupGenero.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioButtonId);
        return radioButton != null ? radioButton.getText().toString() : "";
    }
}
