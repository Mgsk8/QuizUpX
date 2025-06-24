package com.example.quizupx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class EscogerJugador extends AppCompatActivity {

    Spinner spinnerCantidad;
    EditText[] camposNombres;
    TextInputLayout[] contenedoresNombres;
    Button btnIniciarJuego;
    MaterialCardView configLayout, juegoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_escoger_jugador);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spinnerCantidad = findViewById(R.id.spinnerCantidad);
        btnIniciarJuego = findViewById(R.id.btnIniciarJuego);
        configLayout = findViewById(R.id.configLayout);
        juegoLayout = findViewById(R.id.LY);

        // Referencia a los EditText
        camposNombres = new EditText[]{
                findViewById(R.id.nombreJugador1),
                findViewById(R.id.nombreJugador2),
                findViewById(R.id.nombreJugador3),
                findViewById(R.id.nombreJugador4),
                findViewById(R.id.nombreJugador5)
        };

        // Referencia a los contenedores TextInputLayout
        contenedoresNombres = new TextInputLayout[]{
                findViewById(R.id.layoutJugador1),
                findViewById(R.id.layoutJugador2),
                findViewById(R.id.layoutJugador3),
                findViewById(R.id.layoutJugador4),
                findViewById(R.id.layoutJugador5)
        };

        // Configurar spinner con cantidades
        Integer[] opciones = {1, 2, 3, 4, 5};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCantidad.setAdapter(adapter);

        spinnerCantidad.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                int cantidad = (int) parent.getItemAtPosition(position);
                mostrarCampos(cantidad);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        btnIniciarJuego.setOnClickListener(v -> {
            int cantidad = (int) spinnerCantidad.getSelectedItem();
            ArrayList<String> nombres = new ArrayList<>();

            for (int i = 0; i < cantidad; i++) {
                String nombre = camposNombres[i].getText().toString().trim();
                if (nombre.isEmpty()) {
                    Toast.makeText(this, "Nombre del jugador " + (i + 1) + " está vacío", Toast.LENGTH_SHORT).show();
                    return;
                }
                nombres.add(nombre);
            }

            Intent intent = new Intent(this, JuegoActivity.class);
            intent.putStringArrayListExtra("nombres", nombres);
            startActivity(intent);
            finish(); // opcional
        });
    }

    private void mostrarCampos(int cantidad) {
        for (int i = 0; i < camposNombres.length; i++) {
            if (i < cantidad) {
                contenedoresNombres[i].setVisibility(View.VISIBLE);
            } else {
                contenedoresNombres[i].setVisibility(View.GONE);
                camposNombres[i].setText("");
            }
        }
    }
}
