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
import java.util.List;

public class PreguntaOpcionMultipleActivity extends AppCompatActivity {

    List<PreguntaQuiz> preguntas = new ArrayList<>();
    EditText edtPregunta, edtOpcion1, edtOpcion2, edtOpcion3, edtOpcion4, edtPuntos;
    RadioButton rb1, rb2, rb3, rb4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pregunta_opcion_multiple_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtPregunta = findViewById(R.id.edtPregunta);
        edtOpcion1 = findViewById(R.id.edtOpcion1);
        edtOpcion2 = findViewById(R.id.edtOpcion2);
        edtOpcion3 = findViewById(R.id.edtOpcion3);
        edtOpcion4 = findViewById(R.id.edtOpcion4);
        edtPuntos = findViewById(R.id.edtPuntos);

        rb1 = findViewById(R.id.rbCorrecta1);
        rb2 = findViewById(R.id.rbCorrecta2);
        rb3 = findViewById(R.id.rbCorrecta3);
        rb4 = findViewById(R.id.rbCorrecta4);

        View.OnClickListener rbClickListener = v -> {
            rb1.setChecked(false);
            rb2.setChecked(false);
            rb3.setChecked(false);
            rb4.setChecked(false);
            ((RadioButton) v).setChecked(true);
        };

        rb1.setOnClickListener(rbClickListener);
        rb2.setOnClickListener(rbClickListener);
        rb3.setOnClickListener(rbClickListener);
        rb4.setOnClickListener(rbClickListener);
    }

    public void guardar(View view){
        String pregunta = edtPregunta.getText().toString().trim();
        String opcion1 = edtOpcion1.getText().toString().trim();
        String opcion2 = edtOpcion2.getText().toString().trim();
        String opcion3 = edtOpcion3.getText().toString().trim();
        String opcion4 = edtOpcion4.getText().toString().trim();

        String puntosTexto = edtPuntos.getText().toString().trim();
        if (puntosTexto.isEmpty()) {
            Toast.makeText(this, "Ingresa los puntos que vale esta pregunta", Toast.LENGTH_SHORT).show();
            return;
        }
        int puntos = Integer.parseInt(puntosTexto);



        if (pregunta.isEmpty() || opcion1.isEmpty() || opcion2.isEmpty() || opcion3.isEmpty() || opcion4.isEmpty()) {
            Toast.makeText(PreguntaOpcionMultipleActivity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int respuestaCorrecta = -1;
        if (rb1.isChecked()) respuestaCorrecta = 0;
        else if (rb2.isChecked()) respuestaCorrecta = 1;
        else if (rb3.isChecked()) respuestaCorrecta = 2;
        else if (rb4.isChecked()) respuestaCorrecta = 3;

        if (respuestaCorrecta == -1) {
            Toast.makeText(PreguntaOpcionMultipleActivity.this, "Selecciona la respuesta correcta", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> opciones = new ArrayList<>();
        opciones.add(opcion1);
        opciones.add(opcion2);
        opciones.add(opcion3);
        opciones.add(opcion4);



        // Crear objeto pregunta (si tienes una clase)
        PreguntaQuiz preguntaObj = new PreguntaQuiz(pregunta, opciones, respuestaCorrecta, puntos);
        ListaPreguntas.getInstance().agregarPregunta(preguntaObj);

        Toast.makeText(PreguntaOpcionMultipleActivity.this, "Pregunta guardada correctamente", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MenuQuiz.class);
        startActivity(intent);
        finish();
    }

}