package com.example.juego_de_dados;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        EditText nombreEditText = findViewById(R.id.nombreEditText);
        Button comenzarButton = findViewById(R.id.comenzarButton);

        comenzarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = nombreEditText.getText().toString().trim();

                if (TextUtils.isEmpty(nombre)) { // Verifica si el campo está vacío
                    nombreEditText.setError("Por favor, ingrese su nombre"); // Muestra un mensaje en el EditText
                    Toast.makeText(InicioActivity.this, "Debe ingresar su nombre para comenzar el juego", Toast.LENGTH_SHORT).show();
                } else {
                    // Pasa el nombre a MainActivity solo si no está vacío
                    Intent intent = new Intent(InicioActivity.this, MainActivity.class);
                    intent.putExtra("nombreJugador", nombre);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
