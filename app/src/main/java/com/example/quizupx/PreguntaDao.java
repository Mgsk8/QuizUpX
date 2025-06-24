package com.example.quizupx;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PreguntaDao {
    @Query("SELECT * FROM pregunta WHERE categoria = :categoria")
    List<Pregunta> getPreguntasPorCategoria(String categoria);
    @Insert
    void insertarPregunta(Pregunta pregunta);

    @Query("SELECT * FROM Pregunta")
    List<Pregunta> getTodasLasPreguntas();
}
