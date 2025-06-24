package com.example.quizupx;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class JuegoActivity extends AppCompatActivity {

    private TextView txtPregunta, txtTemporizador;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3, rb4;
    private Button btnSiguiente;

    private List<PreguntaQuiz> preguntas;
    private List<String> nombresJugadores;
    private List<Integer> puntajesJugadores;

    private int jugadorActual = 0;
    private int indicePregunta = 0;

    private CountDownTimer temporizador;
    private int tiempoTotal = 30_000; // 30 segundos
    private int intervalo = 1_000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_juego);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Enlazar vistas
        txtPregunta = findViewById(R.id.txtPregunta);
        txtTemporizador = findViewById(R.id.txtTemporizador);
        radioGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        btnSiguiente = findViewById(R.id.btnSiguiente);

        // Obtener preguntas
        preguntas = ListaPreguntas.getInstance().getPreguntas();

        // Obtener nombres de jugadores desde Intent
        nombresJugadores = getIntent().getStringArrayListExtra("nombres");
        if (nombresJugadores == null) nombresJugadores = List.of("Jugador 1");

        // Inicializar puntajes
        puntajesJugadores = new ArrayList<>();
        for (int i = 0; i < nombresJugadores.size(); i++) {
            puntajesJugadores.add(0);
        }

        mostrarPregunta();

        btnSiguiente.setOnClickListener(v -> {
            if (temporizador != null) temporizador.cancel();

            int idSeleccionado = radioGroup.getCheckedRadioButtonId();
            int respuestaSeleccionada = -1;

            if (idSeleccionado == rb1.getId()) respuestaSeleccionada = 0;
            else if (idSeleccionado == rb2.getId()) respuestaSeleccionada = 1;
            else if (idSeleccionado == rb3.getId()) respuestaSeleccionada = 2;
            else if (idSeleccionado == rb4.getId()) respuestaSeleccionada = 3;

            PreguntaQuiz pregunta = preguntas.get(indicePregunta);
            if (respuestaSeleccionada == pregunta.getRespuestaCorrecta()) {
                int puntajeActual = puntajesJugadores.get(jugadorActual);
                    puntajesJugadores.set(jugadorActual, puntajeActual + pregunta.getPuntos());
            }

            indicePregunta++;
            mostrarPregunta();
        });
    }

    private void mostrarPregunta() {
        if (indicePregunta >= preguntas.size()) {
            jugadorActual++;
            if (jugadorActual < nombresJugadores.size()) {
                indicePregunta = 0;
                mostrarPregunta();
            } else {
                // Todos los jugadores han terminado
                Intent intent = new Intent(this, ActivityPuntuacion.class);
                intent.putStringArrayListExtra("nombres", new ArrayList<>(nombresJugadores));
                intent.putIntegerArrayListExtra("puntajes", new ArrayList<>(puntajesJugadores));
                startActivity(intent);
                finish();
            }
            return;
        }

        PreguntaQuiz pregunta = preguntas.get(indicePregunta);
        txtPregunta.setText("Jugador: " + nombresJugadores.get(jugadorActual) + "\n\n" + pregunta.getEnunciado());

        List<String> opciones = pregunta.getOpciones();

        rb1.setVisibility(View.GONE);
        rb2.setVisibility(View.GONE);
        rb3.setVisibility(View.GONE);
        rb4.setVisibility(View.GONE);
        radioGroup.clearCheck();

        if (opciones.size() >= 1) {
            rb1.setText(opciones.get(0));
            rb1.setVisibility(View.VISIBLE);
        }
        if (opciones.size() >= 2) {
            rb2.setText(opciones.get(1));
            rb2.setVisibility(View.VISIBLE);
        }
        if (opciones.size() >= 3) {
            rb3.setText(opciones.get(2));
            rb3.setVisibility(View.VISIBLE);
        }
        if (opciones.size() >= 4) {
            rb4.setText(opciones.get(3));
            rb4.setVisibility(View.VISIBLE);
        }

        iniciarTemporizador();
    }

    private void iniciarTemporizador() {
        temporizador = new CountDownTimer(tiempoTotal, intervalo) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtTemporizador.setText("Tiempo: " + millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                indicePregunta++;
                mostrarPregunta();
            }
        }.start();
    }
}
