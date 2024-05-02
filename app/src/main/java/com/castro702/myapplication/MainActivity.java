package com.castro702.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de vistas
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        Button btnLog = findViewById(R.id.btnLog);

        // Manejo del botón de inicio de sesión
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                // Verificación de credenciales (ejemplo: usuario=admin, contraseña=1234)
                if (username.equals("admin") && password.equals("1234")) {
                    // Credenciales válidas, inicia la siguiente actividad o muestra un mensaje
                    Intent intent = new Intent(MainActivity.this, ContactListActivity.class);
                    startActivity(intent);
                    // Finaliza esta actividad para evitar volver a ella al presionar el botón "Atrás"
                    finish();
                } else {
                    // Credenciales incorrectas, muestra un mensaje de error
                    Toast.makeText(MainActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
