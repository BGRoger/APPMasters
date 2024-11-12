package com.example.juego_de_dados;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class InicioActivity extends AppCompatActivity {

    private boolean isMusicOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        // Utilizamos los IDs que tienes definidos en tu archivo XML
        EditText nombreEditText = findViewById(R.id.nombreEditText);
        Button comenzarButton = findViewById(R.id.comenzarButton);

        // Recibir el estado de la música desde MenuActivity
        isMusicOn = getIntent().getBooleanExtra("isMusicOn", true);

        comenzarButton.setOnClickListener(v -> {
            String nombreJugador = nombreEditText.getText().toString();
            if (!nombreJugador.isEmpty()) {
                Intent intent = new Intent(InicioActivity.this, MainActivity.class);
                intent.putExtra("nombreJugador", nombreJugador);
                intent.putExtra("isMusicOn", isMusicOn);
                startActivity(intent);
            }
        });
    }
}
