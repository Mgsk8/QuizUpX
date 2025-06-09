package com.example.quizupx;

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

public class CrearPregunta extends AppCompatActivity {

    EditText editPregunta, edit1, edit2, edit3, edit4;
    RadioButton radio1, radio2, radio3, radio4;

    public static List<Pregunta> preguntas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear_pregunta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editPregunta = findViewById(R.id.editPregunta);
        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        edit3 = findViewById(R.id.edit3);
        edit4 = findViewById(R.id.edit4);

        radio1 = findViewById(R.id.radio1);
        radio2 = findViewById(R.id.radio2);
        radio3 = findViewById(R.id.radio3);
        radio4 = findViewById(R.id.radio4);
    }

    public void GuardarPregunta(View view){
        String enunciado = editPregunta.getText().toString();
        List<String> opciones = new ArrayList<>();
        opciones.add(edit1.getText().toString());
        opciones.add(edit2.getText().toString());
        opciones.add(edit3.getText().toString());
        opciones.add(edit4.getText().toString());


        if (enunciado.isEmpty()) {
            Toast.makeText(this, "Escribe una pregunta", Toast.LENGTH_SHORT).show();
            return;
        }

        int correcta = -1;

        if (radio1.isChecked()) correcta = 1;
        else if (radio2.isChecked()) correcta = 2;
        else if (radio3.isChecked()) correcta = 3;
        else if (radio4.isChecked()) correcta = 4;

        if (correcta != -1) {
            preguntas.add(new Pregunta(enunciado, opciones, correcta));
            Toast.makeText(this, "Pregunta guardada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Selecciona la respuesta correcta", Toast.LENGTH_SHORT).show();
        }



        // Aquí puedes guardar los datos...

        finish(); // Volver al menú
    }
}