package com.example.quizupx;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;
public class Ruleta extends AppCompatActivity {


    String[] categorias = {
            "Cultura General", "Ciencia", "Música",
            "Series",
            "Falso/Verdadero", "Pregunta Sorpresa"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ruleta);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        PreguntasQuizRuleta.poblarBDSiEsNecesario(this);
        findViewById(R.id.btnVolver).setOnClickListener(v -> finish());

        WheelView wheelView = findViewById(R.id.wheelView);

        wheelView.setOnClickListener(v -> {
            int index = new Random().nextInt(categorias.length);
            wheelView.spinTo(index);

            new Handler().postDelayed(() -> {
                String categoria = categorias[index];
                Toast.makeText(this, "¡Tocó: " + categoria + "!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, QuizRuleta.class);
                i.putExtra("categoria", categoria);
                startActivity(i);
            }, 4000);
        });
    }
}
