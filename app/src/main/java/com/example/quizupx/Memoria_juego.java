package com.example.quizupx;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Memoria_juego extends AppCompatActivity {
    TextView txtNivel;
    FrameLayout secuenciaContainer;
    GridLayout opcionesGrid;
    Button btnReiniciar;

    int nivelActual = 1;
    List<Integer> imagenesDisponibles;
    List<Integer> secuenciaCorrecta = new ArrayList<>();
    List<Integer> seleccionUsuario = new ArrayList<>();

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_memoria_juego);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtNivel = findViewById(R.id.txtNivel);
        secuenciaContainer = findViewById(R.id.secuenciaContainer);
        opcionesGrid = findViewById(R.id.opcionesGrid);
        btnReiniciar = findViewById(R.id.btnReiniciar);

        imagenesDisponibles = new ArrayList<>();
        imagenesDisponibles.add(R.drawable.img1);
        imagenesDisponibles.add(R.drawable.img2);
        imagenesDisponibles.add(R.drawable.img3);
        imagenesDisponibles.add(R.drawable.img4);
        imagenesDisponibles.add(R.drawable.img5);
        imagenesDisponibles.add(R.drawable.img6);

        //imagenes aleatorias y lista de secuencia correcta
        List<Integer> copia = new ArrayList<>(imagenesDisponibles);
        Collections.shuffle(copia);
        int cantidad = nivelActual == 1 ? 3 : (nivelActual == 2 ? 5 : 6);
        secuenciaCorrecta = copia.subList(0, cantidad);
        mostrarSecuencia(0);
    }

    private void mostrarSecuencia(int index) {
        if (index >= secuenciaCorrecta.size()) {
            handler.postDelayed(this::mostrarOpciones, 500);
            return;
        }

        secuenciaContainer.removeAllViews();
        ImageView image = new ImageView(this);
        image.setImageResource(secuenciaCorrecta.get(index));
        image.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        secuenciaContainer.addView(image);

        handler.postDelayed(() -> mostrarSecuencia(index + 1), 1200);
    }
    private void mostrarOpciones() {
        secuenciaContainer.setVisibility(View.GONE);
        opcionesGrid.setVisibility(View.VISIBLE);
        opcionesGrid.removeAllViews();
        seleccionUsuario.clear();

        List<Integer> opciones = new ArrayList<>(secuenciaCorrecta);
        Collections.shuffle(opciones);

        for (Integer resId : opciones) {
            FrameLayout container = new FrameLayout(this);

            ImageView img = new ImageView(this);
            img.setImageResource(resId);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            container.setLayoutParams(params);


            img.setAdjustViewBounds(true);
            img.setBackgroundResource(R.drawable.image_background); // usa un drawable redondeado
            img.setClipToOutline(true);
            img.setScaleType(ImageView.ScaleType.FIT_CENTER);
            img.setPadding(10, 10, 10, 10);
            img.setId(View.generateViewId());

            TextView ordenLabel = new TextView(this);
            ordenLabel.setText("");
            ordenLabel.setTextColor(0xFFFFFFFF);
            ordenLabel.setTextSize(14);
            ordenLabel.setBackgroundResource(R.drawable.badge_background);
            ordenLabel.setPadding(16, 8, 16, 8);
            ordenLabel.setVisibility(View.INVISIBLE);

            FrameLayout.LayoutParams badgeParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            badgeParams.setMargins(8, 8, 0, 0);
            ordenLabel.setLayoutParams(badgeParams);

            container.addView(img);
            container.addView(ordenLabel);

            img.setOnClickListener(v -> {
                manejarSeleccion(resId, img);
                ordenLabel.setText(String.valueOf(seleccionUsuario.size()));
                ordenLabel.setVisibility(View.VISIBLE);
            });

            opcionesGrid.addView(container);
        }
    }
        private void manejarSeleccion(int resId, ImageView view) {
            seleccionUsuario.add(resId);
            view.setAlpha(0.4f);
            view.setEnabled(false);

            if (seleccionUsuario.size() == secuenciaCorrecta.size()) {
                verificarResultado();
            }
        }

        private void verificarResultado() {
            if (seleccionUsuario.equals(secuenciaCorrecta)) {
                Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show();
                nivelActual++;
                handler.postDelayed(this::reiniciarJuego, 1500);
            } else {
                Toast.makeText(this, "¡Incorrecto!", Toast.LENGTH_SHORT).show();
                nivelActual = 1;
                btnReiniciar.setVisibility(View.VISIBLE);
                btnReiniciar.setOnClickListener(v -> reiniciarJuego());
            }

        }


    private void reiniciarJuego() {
        txtNivel.setText("Nivel " + nivelActual);
        btnReiniciar.setVisibility(View.GONE);
        opcionesGrid.setVisibility(View.GONE);
        secuenciaContainer.setVisibility(View.VISIBLE);

        List<Integer> copia = new ArrayList<>(imagenesDisponibles);
        Collections.shuffle(copia);
        int cantidad = nivelActual == 1 ? 3 : (nivelActual == 2 ? 5 : 6);
        secuenciaCorrecta = new ArrayList<>(copia.subList(0, cantidad));

        mostrarSecuencia(0);
    }
}