package com.example.quizupx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class VerdaderoFalso extends AppCompatActivity {
    private EditText edtPreguntaVF;
    private RadioButton rbVerdadero, rbFalso;

    private EditText edtPuntosVF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pregunta_verdadero_falso_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtPreguntaVF = findViewById(R.id.edtPregunta2);
        rbVerdadero = findViewById(R.id.radioVerdadero);
        rbFalso = findViewById(R.id.radioFalso);
        edtPuntosVF = findViewById(R.id.edtPuntosVF);

    }
    public void guardarPreguntaVF(View view){
        String enunciado = edtPreguntaVF.getText().toString().trim();
        String puntosTexto = edtPuntosVF.getText().toString().trim();

        if (enunciado.isEmpty() || puntosTexto.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int respuestaCorrecta = -1;
        if (rbVerdadero.isChecked()) {
            respuestaCorrecta = 0;
        } else if (rbFalso.isChecked()) {
            respuestaCorrecta = 1;
        } else {
            Toast.makeText(this, "Selecciona la respuesta correcta", Toast.LENGTH_SHORT).show();
            return;
        }

        int puntos;
        try {
            puntos = Integer.parseInt(puntosTexto);
            if (puntos <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Los puntos deben ser un número positivo", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<String> opciones = new ArrayList<>(Arrays.asList("Verdadero", "Falso"));
        PreguntaQuiz pregunta = new PreguntaQuiz(enunciado, opciones, respuestaCorrecta, puntos);

        ListaPreguntas.getInstance().agregarPregunta(pregunta);

        Toast.makeText(this, "Pregunta guardada", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MenuQuiz.class);
        startActivity(intent);
        finish();
    }
}