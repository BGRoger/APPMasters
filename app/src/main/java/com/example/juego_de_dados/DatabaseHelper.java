package com.example.juego_de_dados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ranking.db";
    private static final String TABLE_NAME = "puntuaciones";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "NAME";
    private static final String COL_3 = "SCORE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creación de la tabla puntuaciones con las columnas ID, NAME y SCORE
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, SCORE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Inserta una puntuación de manera asíncrona usando Completable
    public Completable insertarPuntuacion(String nombre, int puntuacion) {
        return Completable.fromAction(() -> {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2, nombre); // Columna NAME
            contentValues.put(COL_3, puntuacion); // Columna SCORE
            db.insert(TABLE_NAME, null, contentValues);
        });
    }

    // Obtiene las puntuaciones de manera asíncrona usando Single
    public Single<Cursor> obtenerPuntuaciones() {
        return Single.fromCallable(() -> {
            SQLiteDatabase db = this.getReadableDatabase();
            return db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY SCORE DESC", null);
        });
    }
}
