package com.example.juego_de_dados;

import java.util.Random;

public class JuegoDeDados {

    private Random random;
    private int tiradasRestantes;
    private int puntuacionTotal;

    public JuegoDeDados() {
        random = new Random();
        tiradasRestantes = 5; // Máximo de 5 tiradas
        puntuacionTotal = 0; // Inicializa la puntuación total
    }

    // Método que lanza dos dados y calcula la puntuación
    public Resultado lanzarDados() {
        if (tiradasRestantes > 0) {
            int dado1 = random.nextInt(6) + 1;
            int dado2 = random.nextInt(6) + 1;
            int suma = dado1 + dado2;
            int puntos = calcularPuntos(dado1, dado2, suma);

            puntuacionTotal += puntos; // Solo suma puntos especiales a la puntuación total
            tiradasRestantes--; // Reduce las tiradas restantes

            return new Resultado(dado1, dado2, suma, puntos, tiradasRestantes, puntuacionTotal);
        } else {
            return null; // No hay más tiradas disponibles
        }
    }

    // Método para calcular los puntos según las reglas
    private int calcularPuntos(int dado1, int dado2, int suma) {
        if (dado1 == dado2) {
            return 20; // 20 puntos si los dados son iguales
        } else if (suma == 7) {
            return 10; // 10 puntos si la suma es 7
        } else {
            return 0; // Sin puntos si no cumplen ninguna condición
        }
    }

    // Método para obtener la puntuación total
    public int getPuntuacionTotal() {
        return puntuacionTotal;
    }

    // Clase interna para representar el resultado de una tirada
    public class Resultado {
        public final int dado1;
        public final int dado2;
        public final int suma;
        public final int puntos;
        public final int tiradasRestantes;
        public final int puntuacionTotal;

        public Resultado(int dado1, int dado2, int suma, int puntos, int tiradasRestantes, int puntuacionTotal) {
            this.dado1 = dado1;
            this.dado2 = dado2;
            this.suma = suma;
            this.puntos = puntos;
            this.tiradasRestantes = tiradasRestantes;
            this.puntuacionTotal = puntuacionTotal;
        }
    }
}
