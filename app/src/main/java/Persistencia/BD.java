package Persistencia;

import android.provider.BaseColumns;
import android.widget.TextView;

public final class BD {
    public static final String Text_Type = " TEXT";
    public static final String Int_Type = " INTEGER";
    public static final String Data_Type = " DATE";
    public static final String Sep = ",";

    public static final String SQL_CREATE_CANDIDATO = "CREATE TABLE " + Candidato.TABLE_NAME + " (" +
            Candidato._ID + Int_Type + " PRIMARY KEY AUTOINCREMENT" + Sep +
            Candidato.COLUMN_NAME_NOME + Text_Type + Sep +
            Candidato.COLUMN_NAME_DATA_NASCIMENTO + Data_Type + Sep +
            Candidato.COLUMN_NAME_PERFIL + Text_Type + Sep +
            Candidato.COLUMN_NAME_TELEFONE + Text_Type + Sep +
            Candidato.COLUMN_NAME_EMAIL + Text_Type + ")";
    public static final String SQL_DROP_CANDIDATO = "DROP TABLE IF EXISTS " + Candidato.TABLE_NAME;

    public static final String SQL_CREATE_PRODUCAO = "CREATE TABLE " + Producao.TABLE_NAME + " (" +
            Producao._ID + Int_Type + " PRIMARY KEY AUTOINCREMENT" + Sep +
            Producao.COLUMN_NAME_TITULO + Text_Type + Sep +
            Producao.COLUMN_NAME_DESCRICAO + Text_Type + Sep +
            Producao.COLUMN_NAME_INICIO + Data_Type + Sep +
            Producao.COLUMN_NAME_FIM + Data_Type + ")";
    public static final String SQL_DROP_PRODUCAO = "DROP TABLE IF EXISTS " + Producao.TABLE_NAME;

    public static final String SQL_CREATE_CATEGORIA = "CREATE TABLE " + Categoria.TABLE_NAME + " (" +
            Categoria._ID + Int_Type + " PRIMARY KEY AUTOINCREMENT" + Sep +
            Categoria.COLUMN_NAME_TITULO + ")";
    public static final String SQL_DROP_CATEGORIA = "DROP TABLE IF EXISTS " + Categoria.TABLE_NAME;

    public static final String SQL_CREATE_ATIVIDADE = "CREATE TABLE " + Atividade.TABLE_NAME + " (" +
            Atividade._ID + Int_Type + " PRIMARY KEY AUTOINCREMENT" + Sep +
            Atividade.COLUMN_NAME_DESCRICAO + Text_Type + Sep +
            Atividade.COLUMN_NAME_DATA + Data_Type + Sep +
            Atividade.COLUMN_NAME_HORAS + Int_Type + ")";
    public static final String SQL_DROP_ATIVIDADE = "DROP TABLE IF EXISTS " + Atividade.TABLE_NAME;

    public static final class Candidato implements BaseColumns{
        public static final String TABLE_NAME = "candidato";
        public static final String COLUMN_NAME_NOME = "nome";
        public static final String COLUMN_NAME_DATA_NASCIMENTO = "data_nascimento";
        public static final String COLUMN_NAME_PERFIL = "perfil";
        public static final String COLUMN_NAME_TELEFONE = "telefone";
        public static final String COLUMN_NAME_EMAIL = "email";
    }

    public static final class Producao implements BaseColumns{
        public static final String TABLE_NAME = "producao";
        public static final String COLUMN_NAME_TITULO = "titulo";
        public static final String COLUMN_NAME_DESCRICAO = "descricao";
        public static final String COLUMN_NAME_INICIO = "inicio";
        public static final String COLUMN_NAME_FIM = "fim";
    }

    public static final class Categoria implements BaseColumns{
        public static final String TABLE_NAME = "categoria";
        public static final String COLUMN_NAME_TITULO = "titulo";
    }

    public static final class Atividade implements BaseColumns{
        public static final String TABLE_NAME = "atividade";
        public static final String COLUMN_NAME_DESCRICAO = "descricao";
        public static final String COLUMN_NAME_DATA = "data";
        public static final String COLUMN_NAME_HORAS = "horas";
    }
}
