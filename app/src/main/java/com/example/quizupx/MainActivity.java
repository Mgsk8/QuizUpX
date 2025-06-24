package com.example.quizupx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnMenuQuiz, btnRuleta, btnMemoria, btnManual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnMenuQuiz = findViewById(R.id.btnMenuQuiz);
        btnRuleta = findViewById(R.id.btnRuleta);
        btnMemoria = findViewById(R.id.btnMemoria);
        btnManual = findViewById(R.id.btnManual);

        btnMenuQuiz.setOnClickListener(v -> startActivity(new Intent(this, MenuQuiz.class)));
        btnRuleta.setOnClickListener(v -> startActivity(new Intent(this, Ruleta.class)));
        btnMemoria.setOnClickListener(v -> startActivity(new Intent(this, Memoria_juego.class)));
        btnManual.setOnClickListener(v -> {

            Intent intent = new Intent(this, ManualActivity.class);
            startActivity(intent);
        });
    }

}