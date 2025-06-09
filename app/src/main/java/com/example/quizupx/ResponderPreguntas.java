package com.example.quizupx;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class ResponderPreguntas extends AppCompatActivity {

    TextView textViewPregunta;
    RadioButton radio1, radio2, radio3, radio4;
    Button btnResponder;
    Pregunta preguntaActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_responder_preguntas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textViewPregunta = findViewById(R.id.textViewPregunta);
        radio1 = findViewById(R.id.radioOpcion1);
        radio2 = findViewById(R.id.radioOpcion2);
        radio3 = findViewById(R.id.radioOpcion3);
        radio4 = findViewById(R.id.radioOpcion4);
        btnResponder = findViewById(R.id.btnResponder);

        // Recibe la pregunta enviada desde otra activity
        preguntaActual = (Pregunta) getIntent().getSerializableExtra("pregunta");

        if (preguntaActual != null) {
            mostrarPregunta(preguntaActual);
        }

        btnResponder.setOnClickListener(v -> verificarRespuesta());
    }

    private void mostrarPregunta(Pregunta pregunta) {
        textViewPregunta.setText(pregunta.getEnunciado());
        List<String> opciones = pregunta.getOpciones();

        radio1.setText(opciones.get(0));
        radio2.setText(opciones.get(1));
        radio3.setText(opciones.get(2));
        radio4.setText(opciones.get(3));
    }

    private void verificarRespuesta() {
        int seleccion = -1;
        if (radio1.isChecked()) seleccion = 0;
        else if (radio2.isChecked()) seleccion = 1;
        else if (radio3.isChecked()) seleccion = 2;
        else if (radio4.isChecked()) seleccion = 3;

        if (seleccion == preguntaActual.getRespuestaCorrecta()) {
            Toast.makeText(this, "¡Respuesta correcta!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Respuesta incorrecta", Toast.LENGTH_SHORT).show();
        }
    }
}