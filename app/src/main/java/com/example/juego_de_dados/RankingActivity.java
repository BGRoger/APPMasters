package com.example.juego_de_dados;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RankingActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private TextView rankingTextView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable(); // Para manejar las suscripciones de RxJava

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        databaseHelper = new DatabaseHelper(this);
        rankingTextView = findViewById(R.id.rankingTextView);
        Button btnVolver = findViewById(R.id.btnVolver);

        // Configurar el botón de Volver para cerrar la actividad
        btnVolver.setOnClickListener(v -> finish());

        // Obtener y mostrar las puntuaciones
        mostrarRanking();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear(); // Limpiar todas las suscripciones cuando la actividad se destruye
    }

    private void mostrarRanking() {
        compositeDisposable.add(databaseHelper.obtenerPuntuaciones()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cursor -> {
                    if (cursor != null && cursor.moveToFirst()) {
                        StringBuilder ranking = new StringBuilder();
                        int position = 1;

                        do {
                            String nombre = cursor.getString(cursor.getColumnIndexOrThrow("NAME"));
                            int puntuacion = cursor.getInt(cursor.getColumnIndexOrThrow("SCORE"));
                            ranking.append(position).append(". ").append(nombre).append(": ")
                                    .append(puntuacion).append(" puntos\n");
                            position++;
                        } while (cursor.moveToNext());

                        rankingTextView.setText(ranking.toString());
                    } else {
                        rankingTextView.setText("No hay jugadores en el ranking.");
                    }

                    if (cursor != null) {
                        cursor.close();  // Cierra el cursor después de leer los datos
                    }
                }, throwable -> {
                    rankingTextView.setText("Error al cargar el ranking.");
                }));
    }
}
