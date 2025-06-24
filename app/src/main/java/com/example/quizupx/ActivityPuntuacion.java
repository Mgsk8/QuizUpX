package com.example.quizupx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ActivityPuntuacion extends AppCompatActivity {

    LinearLayout layoutPuntuaciones;
    Button btnVolverMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_puntuacion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        layoutPuntuaciones = findViewById(R.id.layoutPuntuaciones);
        btnVolverMenu = findViewById(R.id.btnVolverMenu);

        // Recibir nombres y puntajes (puedes usar otra estructura también)
        ArrayList<String> nombres = getIntent().getStringArrayListExtra("nombres");
        ArrayList<Integer> puntajes = getIntent().getIntegerArrayListExtra("puntajes");

        if (nombres != null && puntajes != null) {
            for (int i = 0; i < nombres.size(); i++) {
                TextView txt = new TextView(this);
                txt.setText(nombres.get(i) + ": " + puntajes.get(i) + " puntos");
                txt.setTextSize(18f);
                txt.setPadding(0, 8, 0, 8);
                layoutPuntuaciones.addView(txt, layoutPuntuaciones.getChildCount() - 1);
            }
        }

        btnVolverMenu.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class); // o MainActivity
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}