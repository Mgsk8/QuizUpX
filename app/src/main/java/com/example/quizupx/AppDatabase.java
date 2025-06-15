package com.example.quizupx;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {Pregunta.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PreguntaDao preguntaDao();

}
