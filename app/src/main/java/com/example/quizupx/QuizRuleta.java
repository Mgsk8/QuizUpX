package com.example.quizupx;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.util.Collections;
import java.util.List;

public class QuizRuleta extends AppCompatActivity {
    TextView txtPregunta;
    Button btn1, btn2, btn3;

    List<Pregunta> preguntas;  // lista de preguntas
    int preguntaIndex = 0;     // índice actual

    AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz_ruleta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtPregunta = findViewById(R.id.txtPregunta);
        btn1 = findViewById(R.id.btnOpcion1);
        btn2 = findViewById(R.id.btnOpcion2);
        btn3 = findViewById(R.id.btnOpcion3);
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "preguntas-db").allowMainThreadQueries().build();

        String categoria = getIntent().getStringExtra("categoria");

        preguntas = db.preguntaDao().getPreguntasPorCategoria(categoria);
        Collections.shuffle(preguntas);

        if (preguntas != null && !preguntas.isEmpty()) {
            mostrarPregunta();
        } else {
            Toast.makeText(this, "No hay preguntas para esta categoría", Toast.LENGTH_SHORT).show();
            finish(); // salir si no hay preguntas
        }

        View.OnClickListener listener = v -> {
            Button b = (Button) v;
            Pregunta actual = preguntas.get(preguntaIndex);
            if (b.getText().toString().equals(actual.respuestaCorrecta)) {
                Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Incorrecto", Toast.LENGTH_SHORT).show();
            }

            // Mostrar siguiente pregunta
            preguntaIndex++;
            if (preguntaIndex < preguntas.size()) {
                mostrarPregunta();
            } else {
                Toast.makeText(this, "¡Fin del cuestionario!", Toast.LENGTH_SHORT).show();
                finish(); // O puedes redirigir a otra pantalla
            }
        };

        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);

    }

    private void mostrarPregunta() {
        Pregunta p = preguntas.get(preguntaIndex);
        txtPregunta.setText(p.pregunta);
        btn1.setText(p.opcion1);
        btn2.setText(p.opcion2);
        btn3.setText(p.opcion3);
    }
}