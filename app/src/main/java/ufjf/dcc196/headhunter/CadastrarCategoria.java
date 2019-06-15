package ufjf.dcc196.headhunter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Persistencia.BD;
import Persistencia.BibliotecaDbHelper;

public class CadastrarCategoria extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_categoria);

        final Context context = this;
        final EditText etTitulo = findViewById(R.id.editTextTituloCategoria);

        Button btnCadastar = findViewById(R.id.btnCadastarCategoria);

        btnCadastar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(context);
                SQLiteDatabase db = bibliotecaHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(BD.Categoria.COLUMN_NAME_TITULO, etTitulo.getText().toString());
                long id = db.insert(BD.Categoria.TABLE_NAME, null, values);
                recycleView();
            }
        });
    }

    private void recycleView(){
        BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(this);

        SQLiteDatabase dbR = bibliotecaHelper.getReadableDatabase();
        String[] visao = {
                BD.Categoria._ID,
                BD.Categoria.COLUMN_NAME_TITULO,
        };
        String selecao = BD.Categoria._ID + " >= ?";
        String[] args = {"0"};
        String sort = BD.Categoria._ID + " DESC";
        final Cursor c = dbR.query(BD.Categoria.TABLE_NAME, visao, selecao, args, null, null  , sort);
        c.moveToFirst();

        setResult(Activity.RESULT_OK);
        finish();

    }
}
