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
import android.app.AlertDialog;
import android.media.MediaPlayer;

import cn.pedant.SweetAlert.SweetAlertDialog;



public class QuizRuleta extends AppCompatActivity {
    TextView txtPregunta;
    Button btn1, btn2, btn3;

    List<Pregunta> preguntas;
    int preguntaIndex = 0;

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
            finish();
        }

        View.OnClickListener listener = v -> {
            Button b = (Button) v;
            Pregunta actual = preguntas.get(preguntaIndex);
            boolean esCorrecta = b.getText().toString().equals(actual.respuestaCorrecta);

            int sonido = esCorrecta ? R.raw.correct : R.raw.wrong;
            MediaPlayer mediaPlayer = MediaPlayer.create(this, sonido);
            mediaPlayer.start();


            int tipoAlerta = esCorrecta ? SweetAlertDialog.SUCCESS_TYPE : SweetAlertDialog.ERROR_TYPE;
            SweetAlertDialog dialogo = new SweetAlertDialog(this, tipoAlerta);
            dialogo.setTitleText(esCorrecta ? "¡Correcto! 🎉" : "¡Incorrecto! ❌");
            dialogo.setContentText(esCorrecta ? "¡Muy bien!" : "La respuesta correcta era:\n" + actual.respuestaCorrecta);
            dialogo.setConfirmText("Siguiente");
            dialogo.setCancelable(false);
            dialogo.setConfirmClickListener(sDialog -> {
                sDialog.dismissWithAnimation();
                preguntaIndex++;
                if (preguntaIndex < preguntas.size()) {
                    mostrarPregunta();
                } else {
                    mostrarDialogoFinal();
                }
            });
            dialogo.show();
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
    private void mostrarDialogoFinal() {
        SweetAlertDialog dialogoFinal = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        dialogoFinal.setTitleText("¡Quiz completado! 🏆");
        dialogoFinal.setContentText("¡Felicitaciones! Has terminado el juego.");
        dialogoFinal.setConfirmText("Volver al inicio");
        dialogoFinal.setCancelable(false);
        dialogoFinal.setConfirmClickListener(sDialog -> {
            sDialog.dismissWithAnimation();
            finish(); // Cierra la actividad actual o navega a la pantalla principal
        });
        dialogoFinal.show();
    }

}