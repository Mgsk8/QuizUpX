package com.example.quizupx;

import java.util.ArrayList;
import java.util.List;

public class ListaPreguntas {
        private static ListaPreguntas instancia;
        private List<PreguntaQuiz> preguntas;

        private ListaPreguntas() {
            preguntas = new ArrayList<>();
        }

        public static ListaPreguntas getInstance() {
            if (instancia == null) {
                instancia = new ListaPreguntas();
            }
            return instancia;
        }

        public void agregarPregunta(PreguntaQuiz pregunta) {
            preguntas.add(pregunta);
        }

        public List<PreguntaQuiz> getPreguntas() {
            return preguntas;
        }
}


