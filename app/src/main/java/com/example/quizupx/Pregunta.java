package com.example.quizupx;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Pregunta {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String categoria;
    public String pregunta;
    public String opcion1;
    public String opcion2;
    public String opcion3;
    public String respuestaCorrecta;
}
