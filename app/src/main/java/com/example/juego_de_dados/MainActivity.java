package com.example.juego_de_dados;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private JuegoDeDados juego;
    private DatabaseHelper databaseHelper;
    private String nombreJugador;
    private TextView resultText;
    private TextView scoreText;
    private ImageView diceImage1;
    private ImageView diceImage2;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombreJugador = getIntent().getStringExtra("nombreJugador");
        databaseHelper = new DatabaseHelper(this);
        juego = new JuegoDeDados();

        diceImage1 = findViewById(R.id.diceImage1);
        diceImage2 = findViewById(R.id.diceImage2);
        Button rollButton = findViewById(R.id.rollButton);
        resultText = findViewById(R.id.resultText);
        scoreText = findViewById(R.id.scoreText);

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JuegoDeDados.Resultado resultado = juego.lanzarDados();

                if (resultado != null) {
                    actualizarImagenDado(diceImage1, resultado.dado1);
                    actualizarImagenDado(diceImage2, resultado.dado2);

                    resultText.setText("Suma: " + resultado.suma +
                            "\nPuntos de esta tirada: " + resultado.puntos +
                            "\nPuntuación total: " + resultado.puntuacionTotal);
                    scoreText.setText("Tiradas restantes: " + resultado.tiradasRestantes);
                } else {
                    Log.d("MainActivity", "Finalizando tiradas. Guardando puntuación.");
                    guardarPuntuacionFinal();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Limpiar suscripciones al destruir la actividad
        compositeDisposable.clear();
    }

    private void actualizarImagenDado(ImageView imageView, int valorDado) {
        int resId = getResources().getIdentifier("dado" + valorDado, "drawable", getPackageName());
        imageView.setImageResource(resId);
    }

    private void guardarPuntuacionFinal() {
        int puntuacionFinal = juego.getPuntuacionTotal();
        compositeDisposable.add(
                databaseHelper.insertarPuntuacion(nombreJugador, puntuacionFinal)
                        .subscribeOn(Schedulers.io())  // Operación en el hilo de I/O
                        .observeOn(AndroidSchedulers.mainThread())  // Resultado en el hilo principal
                        .subscribe(
                                () -> {
                                    Log.d("MainActivity", "Puntuación insertada correctamente.");
                                    mostrarRanking();
                                },
                                throwable -> {
                                    Log.e("MainActivity", "Error al insertar puntuación.", throwable);
                                    Toast.makeText(MainActivity.this, "Error al guardar puntuación", Toast.LENGTH_SHORT).show();
                                }
                        )
        );
    }

    private void mostrarRanking() {
        Log.d("MainActivity", "Iniciando RankingActivity.");
        Intent intent = new Intent(MainActivity.this, RankingActivity.class);
        startActivity(intent);
    }
}