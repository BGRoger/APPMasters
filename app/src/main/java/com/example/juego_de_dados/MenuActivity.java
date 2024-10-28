// MenuActivity.java
package com.example.juego_de_dados;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btnJugar = findViewById(R.id.btnJugar);
        Button btnRanking = findViewById(R.id.btnRanking);
        Button btnSalir = findViewById(R.id.btnSalir);

        // Botón para ir a la pantalla de juego
        btnJugar.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, InicioActivity.class);
            startActivity(intent);
        });

        // Botón para ver el ranking
        btnRanking.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, RankingActivity.class);
            startActivity(intent);
        });

        // Botón para salir de la aplicación
        btnSalir.setOnClickListener(v -> finish());
    }
}
