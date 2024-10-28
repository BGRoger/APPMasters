package com.example.juego_de_dados;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 2000; // Duración de la pantalla splash en milisegundos (2 segundos)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // Asegúrate de tener un layout llamado activity_splash

        // Iniciar MenuActivity después de 2 segundos
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MenuActivity.class);
            startActivity(intent);
            finish(); // Finaliza SplashActivity para que no quede en la pila de actividades
        }, SPLASH_DURATION);
    }
}