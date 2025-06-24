package com.example.quizupx;

import java.io.Serializable;
import java.util.List;

public class PreguntaQuiz implements Serializable {
    private String enunciado;
    private List<String> opciones;
    private int  respuestaCorrecta;

    private int puntos;



    public PreguntaQuiz(String enunciado, List<String> opciones, int respuestaCorrecta, int puntos) {
        this.enunciado = enunciado;
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta;
        this.puntos = puntos;
    }
    public String getEnunciado() { return enunciado; }
    public List<String> getOpciones() { return opciones; }
    public int getRespuestaCorrecta() { return respuestaCorrecta; }

    public int getPuntos() { return puntos; }
}
