package com.castro702.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class EditContactActivity extends AppCompatActivity {

    private EditText nombreEditText;
    private EditText apellidoEditText;
    private EditText telefonoEditText;

    private EditText domicilioEditText;
    private EditText emailEditText;
    private RadioButton masculinoRadioButton;
    private RadioButton femeninoRadioButton;
    private Button saveButton;
    private dataBaseContact dataBaseContact;
    private int contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        dataBaseContact = new dataBaseContact(this);

        nombreEditText = findViewById(R.id.nombreEditText);
        apellidoEditText = findViewById(R.id.apellidoEditText);
        telefonoEditText = findViewById(R.id.telefonoEditText);
        domicilioEditText = findViewById(R.id.domicilioEditText);
        emailEditText = findViewById(R.id.emailEditText);
        masculinoRadioButton = findViewById(R.id.masculinoRadioButton);
        femeninoRadioButton = findViewById(R.id.femeninoRadioButton);
        saveButton = findViewById(R.id.saveButton);

        contactId = getIntent().getIntExtra("contact_id", -1);
        if (contactId != -1) {
            Contacto contacto = dataBaseContact.getContact(contactId);
            if (contacto != null) {
                nombreEditText.setText(contacto.getNombre());
                apellidoEditText.setText(contacto.getApellido());
                telefonoEditText.setText(contacto.getTelefono());
                domicilioEditText.setText(contacto.getDomicilio());
                emailEditText.setText(contacto.getEmail());

               String genero = contacto.getGenero();
               if (genero != null) {
                   if (genero.equals("Masculino")) {
                       masculinoRadioButton.setChecked(true);
                   } else {
                       femeninoRadioButton.setChecked(true);
                   }
               }

            }
        }

        saveButton.setOnClickListener(v -> saveContact());
    }

    private void saveContact() {
        String nombre = nombreEditText.getText().toString().trim();
        String apellido = apellidoEditText.getText().toString().trim();
        String telefono = telefonoEditText.getText().toString().trim();
        String domicilio = domicilioEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String genero = masculinoRadioButton.isChecked() ? "Masculino" : "Femenino";

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(apellido) || TextUtils.isEmpty(telefono) || TextUtils.isEmpty(domicilio) || TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!masculinoRadioButton.isChecked() && !femeninoRadioButton.isChecked()) {
            Toast.makeText(this, "Por favor, seleccione un género", Toast.LENGTH_SHORT).show();
            return;
        }

        Contacto contacto = new Contacto(contactId, nombre, apellido, telefono, domicilio, email, genero);

        dataBaseContact.updateContact(contacto);

        Toast.makeText(this, "Contacto actualizado", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }
}
