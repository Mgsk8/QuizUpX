package com.example.quizupx;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Random;

public class Ruleta extends AppCompatActivity {

    LottieAnimationView lottieView;
    String[] categorias = {
            "Cultura General", "Ciencia", "Música",
            "Series",
            "Falso/Verdadero", "Pregunta Sorpresa"
    };
    MediaPlayer sonidoGiro;
    Vibrator vibrator;

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

        lottieView = findViewById(R.id.lottieRuleta);
        sonidoGiro = MediaPlayer.create(this, R.raw.giro_ruleta);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        lottieView.setOnClickListener(r -> {
            lottieView.playAnimation();
            sonidoGiro.start();
            vibrator.vibrate(150);
            lottieView.setEnabled(false);
            new Handler().postDelayed(() -> {
                int index = new Random().nextInt(categorias.length);
                String categoriaSeleccionada = categorias[index];
                Toast.makeText(this, "Categoría: " + categoriaSeleccionada, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, QuizRuleta.class);
                i.putExtra("categoria", categoriaSeleccionada);
                startActivity(i);
            }, 2500);
        });
    }
}
