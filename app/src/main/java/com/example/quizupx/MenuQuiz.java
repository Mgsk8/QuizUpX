package com.example.quizupx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuQuiz extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.menu_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btnIniciarJuego = findViewById(R.id.btnIniciarJuego);
        if (!ListaPreguntas.getInstance().getPreguntas().isEmpty()) {
            btnIniciarJuego.setEnabled(true);
        }
    }

    public void opcionMultiple(View view){
        Intent intent = new Intent(MenuQuiz.this, PreguntaOpcionMultipleActivity.class);
        startActivity(intent);
    }
    public void verdaderoFalso(View view){
        Intent intent = new Intent(MenuQuiz.this, VerdaderoFalso.class);
        startActivity(intent);
    }
    public void iniciarJuego(View view){
        if (ListaPreguntas.getInstance().getPreguntas().isEmpty()) {
            Toast.makeText(this, "Debes ingresar al menos una pregunta", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, EscogerJugador.class);
        startActivity(intent);
    }
}