package com.example.juego_de_dados;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    private boolean isMusicOn = true;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btnJugar = findViewById(R.id.btnJugar);
        Button btnRanking = findViewById(R.id.btnRanking);
        Button btnSalir = findViewById(R.id.btnSalir);
        ToggleButton toggleMusic = findViewById(R.id.toggleMusic);

        // Inicializar MediaPlayer con el archivo de música en raw
        mediaPlayer = MediaPlayer.create(this, R.raw.casino);
        mediaPlayer.setLooping(true);  // Repetir la música en bucle
        mediaPlayer.start();  // Comenzar la música

        // Configurar el botón de alternancia de música
        toggleMusic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isMusicOn = isChecked;
            if (isMusicOn) {
                mediaPlayer.start();
            } else {
                mediaPlayer.pause();
            }
        });

        // Botón para ir a la pantalla de ingreso del nombre del jugador
        btnJugar.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, InicioActivity.class);
            intent.putExtra("isMusicOn", isMusicOn);
            startActivity(intent);
        });

        // Botón para ver el ranking
        btnRanking.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, RankingActivity.class);
            startActivity(intent);
        });

        // Botón para salir de la aplicación
        btnSalir.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            finish();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pausar la música si la actividad no está en primer plano
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reanudar la música si la actividad vuelve al primer plano
        if (mediaPlayer != null && isMusicOn) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberar el MediaPlayer al destruir la actividad
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
