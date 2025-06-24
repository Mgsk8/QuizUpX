package com.example.quizupx;

import android.content.Context;

import androidx.room.Room;

import java.util.List;

public class PreguntasQuizRuleta {
    public static void poblarBDSiEsNecesario(Context context) {
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "preguntas-db").allowMainThreadQueries().build();

        List<Pregunta> todas = db.preguntaDao().getTodasLasPreguntas();
        if (todas.isEmpty()) {
            insertarPreguntas(db);
        }
    }
    private static void insertarPreguntas(AppDatabase db) {
        db.preguntaDao().insertarPregunta(crear("Cultura General", "¿En qué país se encuentra la Torre Eiffel?", "Italia", "Francia", "Alemania", "Francia"));
        db.preguntaDao().insertarPregunta(crear("Cultura General", "¿Cuál es el idioma más hablado en el mundo?", "Inglés", "Chino mandarín", "Español", "Chino mandarín"));
        db.preguntaDao().insertarPregunta(crear("Cultura General", "¿Qué océano es el más grande?", "Atlántico", "Pacífico", "Índico", "Pacífico"));
        db.preguntaDao().insertarPregunta(crear("Cultura General", "¿Quién escribió 'Don Quijote de la Mancha'?", "Gabriel García Márquez", "Miguel de Cervantes", "Pablo Neruda", "Miguel de Cervantes"));
        db.preguntaDao().insertarPregunta(crear("Cultura General", "¿Cuál es la moneda de Japón?", "Yen", "Won", "Yuan", "Yen"));

        // Ciencia
        db.preguntaDao().insertarPregunta(crear("Ciencia", "¿Qué órgano humano bombea sangre?", "Cerebro", "Hígado", "Corazón", "Corazón"));
        db.preguntaDao().insertarPregunta(crear("Ciencia", "¿Cuál es la fórmula del agua?", "H2O", "CO2", "NaCl", "H2O"));
        db.preguntaDao().insertarPregunta(crear("Ciencia", "¿Qué fuerza nos mantiene en el suelo?", "Inercia", "Gravedad", "Fricción", "Gravedad"));
        db.preguntaDao().insertarPregunta(crear("Ciencia", "¿Cuántos planetas tiene el sistema solar?", "7", "8", "9", "8"));
        db.preguntaDao().insertarPregunta(crear("Ciencia", "¿Qué animal pone huevos?", "Perro", "Gato", "Gallina", "Gallina"));

        // Música
        db.preguntaDao().insertarPregunta(crear("Música", "¿Quién es el 'Rey del Pop'?", "Michael Jackson", "Justin Bieber", "Elvis Presley", "Michael Jackson"));
        db.preguntaDao().insertarPregunta(crear("Música", "¿Qué grupo cantó 'Bohemian Rhapsody'?", "The Beatles", "Queen", "Pink Floyd", "Queen"));
        db.preguntaDao().insertarPregunta(crear("Música", "¿En qué género musical se destaca Bad Bunny?", "Reggaetón", "Rock", "Pop", "Reggaetón"));
        db.preguntaDao().insertarPregunta(crear("Música", "¿Qué instrumento tiene cuerdas?", "Piano", "Guitarra", "Trompeta", "Guitarra"));
        db.preguntaDao().insertarPregunta(crear("Música", "¿Qué artista lanzó el álbum '1989'?", "Adele", "Taylor Swift", "Katy Perry", "Taylor Swift"));

        // Series
        db.preguntaDao().insertarPregunta(crear("Series", "¿En qué serie aparece Walter White?", "Breaking Bad", "The Office", "Stranger Things", "Breaking Bad"));
        db.preguntaDao().insertarPregunta(crear("Series", "¿Qué personaje tiene poderes psíquicos en 'Stranger Things'?", "Max", "Eleven", "Mike", "Eleven"));
        db.preguntaDao().insertarPregunta(crear("Series", "¿Cuál es el apellido de Sheldon en 'The Big Bang Theory'?", "Jones", "Cooper", "Brown", "Cooper"));
        db.preguntaDao().insertarPregunta(crear("Series", "¿Qué ciudad es el escenario principal de 'Friends'?", "Los Ángeles", "Nueva York", "Chicago", "Nueva York"));
        db.preguntaDao().insertarPregunta(crear("Series", "¿Quién es el protagonista en 'The Witcher'?", "Geralt de Rivia", "Jon Snow", "Aragorn", "Geralt de Rivia"));

        // Falso/Verdadero
        db.preguntaDao().insertarPregunta(crear("Falso/Verdadero", "El sol gira alrededor de la Tierra", "Verdadero", "Falso", "Depende", "Falso"));
        db.preguntaDao().insertarPregunta(crear("Falso/Verdadero", "Los humanos tienen cinco sentidos", "Verdadero", "Falso", "No se sabe", "Verdadero"));
        db.preguntaDao().insertarPregunta(crear("Falso/Verdadero", "La sangre humana es siempre azul dentro del cuerpo", "Verdadero", "Falso", "Roja", "Falso"));
        db.preguntaDao().insertarPregunta(crear("Falso/Verdadero", "El oxígeno es necesario para respirar", "Verdadero", "Falso", "Opcional", "Verdadero"));
        db.preguntaDao().insertarPregunta(crear("Falso/Verdadero", "El hielo se derrite a 0°C", "Verdadero", "Falso", "Solo si hace sol", "Verdadero"));

        // Pregunta Sorpresa
        db.preguntaDao().insertarPregunta(crear("Pregunta Sorpresa", "¿Cuál es el número más alto en una ruleta clásica?", "36", "38", "40", "36"));
        db.preguntaDao().insertarPregunta(crear("Pregunta Sorpresa", "¿Qué color se forma al mezclar azul y amarillo?", "Verde", "Rojo", "Morado", "Verde"));
        db.preguntaDao().insertarPregunta(crear("Pregunta Sorpresa", "¿Cuál es el animal más rápido del mundo?", "Tigre", "Guepardo", "Águila", "Guepardo"));
        db.preguntaDao().insertarPregunta(crear("Pregunta Sorpresa", "¿Cuántos minutos hay en una hora?", "60", "90", "120", "60"));
        db.preguntaDao().insertarPregunta(crear("Pregunta Sorpresa", "¿Qué deporte se juega con raqueta y pelota?", "Tenis", "Fútbol", "Golf", "Tenis"));
    }

    private static Pregunta crear(String categoria, String pregunta, String op1, String op2, String op3, String correcta) {
        Pregunta p = new Pregunta();
        p.categoria = categoria;
        p.pregunta = pregunta;
        p.opcion1 = op1;
        p.opcion2 = op2;
        p.opcion3 = op3;
        p.respuestaCorrecta = correcta;
        return p;
    }
}
