package com.example.juego_de_dados;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
                String nombre = nombreEditText.getText().toString();
                Intent intent = new Intent(InicioActivity.this, MainActivity.class);
                intent.putExtra("nombreJugador", nombre); // Pasa el nombre a MainActivity
                startActivity(intent);
                finish();
            }
        });
    }
}
