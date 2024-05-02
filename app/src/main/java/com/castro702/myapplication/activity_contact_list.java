package com.castro702.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class activity_contact_list extends AppCompatActivity {

    private ContactoAdapter adapter;

    private EditText editTextNombre, editTextApellido, editTextTelefono, editTextDomicilio, editTextEmail;
    private RadioGroup radioGroupGenero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        // Inicializar vistas
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellido = findViewById(R.id.editTextApellido);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        editTextDomicilio = findViewById(R.id.editTextDomicilio);
        editTextEmail = findViewById(R.id.editTextEmail);
        radioGroupGenero = findViewById(R.id.radioGroupGenero);

        adapter = new ContactoAdapter(ContactStorage.getListaContactos());

        // Configurar el listener del botón de guardar
        findViewById(R.id.saveContactIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarContacto();

            }
        });

        // Configurar el listener del botón de retroceso
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void guardarContacto() {
        // Obtener los valores de los campos de entrada
        String nombre = editTextNombre.getText().toString().trim();
        String apellido = editTextApellido.getText().toString().trim();
        String telefono = editTextTelefono.getText().toString().trim();
        String domicilio = editTextDomicilio.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String genero = obtenerGeneroSeleccionado();

        // Validar que los campos no estén vacíos
        if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || domicilio.isEmpty() || email.isEmpty() || genero.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Aquí puedes realizar la lógica para guardar el contacto en una base de datos o en otro lugar adecuado

        // Agregar el nuevo contacto a la lista (utilizando la clase ContactStorage)
        Contacto nuevoContacto = new Contacto(nombre, apellido, telefono);
        ContactStorage.agregarContacto(nuevoContacto);

        //adapter.agregarContacto(nuevoContacto);


        // Notificar al adapter que se ha actualizado el conjunto de datos
        adapter.notifyDataSetChanged();

        // Mostrar un mensaje de éxito
        Toast.makeText(this, "Contacto guardado exitosamente", Toast.LENGTH_SHORT).show();


        // Limpiar los campos después de guardar el contacto
        limpiarCampos();
    }

    private String obtenerGeneroSeleccionado() {
        // Obtener el ID del RadioButton seleccionado
        int radioButtonId = radioGroupGenero.getCheckedRadioButtonId();

        // Obtener el RadioButton seleccionado
        RadioButton radioButton = findViewById(radioButtonId);

        // Obtener el texto del RadioButton seleccionado
        return radioButton != null ? radioButton.getText().toString() : "";
    }

    private void limpiarCampos() {
        // Limpiar los campos de entrada
        editTextNombre.setText("");
        editTextApellido.setText("");
        editTextTelefono.setText("");
        editTextDomicilio.setText("");
        editTextEmail.setText("");
        radioGroupGenero.clearCheck();
    }

}