package Persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BibliotecaDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Tarefas.db";

    public BibliotecaDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(BD.SQL_CREATE_CANDIDATO);
        sqLiteDatabase.execSQL(BD.SQL_CREATE_ATIVIDADE);
        sqLiteDatabase.execSQL(BD.SQL_CREATE_CATEGORIA);
        sqLiteDatabase.execSQL(BD.SQL_CREATE_PRODUCAO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(BD.SQL_DROP_CANDIDATO);
        sqLiteDatabase.execSQL(BD.SQL_DROP_ATIVIDADE);
        sqLiteDatabase.execSQL(BD.SQL_DROP_CATEGORIA);
        sqLiteDatabase.execSQL(BD.SQL_DROP_PRODUCAO);
        onCreate(sqLiteDatabase);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
