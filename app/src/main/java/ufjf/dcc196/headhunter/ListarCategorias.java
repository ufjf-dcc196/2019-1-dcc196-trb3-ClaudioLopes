package ufjf.dcc196.headhunter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import Persistencia.BD;
import Persistencia.BibliotecaDbHelper;

public class ListarCategorias extends AppCompatActivity {
    public static final int REQUEST_MAIN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_categorias);

        recycleView();

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if (requestCode == ListarCategorias.REQUEST_MAIN) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(this);

                ContentValues values = new ContentValues();
                try {
                    SQLiteDatabase db = bibliotecaHelper.getWritableDatabase();
                    values.put(BD.Categoria.COLUMN_NAME_TITULO, bundle.get("titulo").toString());
                    String select = BD.Categoria._ID + " = ?";
                    String[] selectArgs = {bundle.get("id").toString()};
                    db.update(BD.Categoria.TABLE_NAME, values, select, selectArgs);
                }catch (NullPointerException ex){
                    SQLiteDatabase db = bibliotecaHelper.getWritableDatabase();
                    String select = BD.Categoria._ID + " = ?";
                    String[] selectArgs = {bundle.get("id").toString()};
                    db.delete(BD.Categoria.TABLE_NAME, select, selectArgs);
                }
            }
        }
        recycleView();
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

        RecyclerView recyclerView = findViewById(R.id.rvCategoria);
        CategoriaAdapter categoriaAdapter = new CategoriaAdapter(c, this);
        recyclerView.setAdapter(categoriaAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoriaAdapter.setOnCategoriaAdapterClickListener(new CategoriaAdapter.OnCategoriaAdapterClickListener() {
            @Override
            public void onCategoriaAdapterClick(View v, int possition) {
                c.moveToPosition(possition);
                int idxId = c.getColumnIndexOrThrow(BD.Categoria._ID);
                int idxTitulo = c.getColumnIndexOrThrow(BD.Categoria.COLUMN_NAME_TITULO);

                Intent intent = new Intent(ListarCategorias.this, EditarCategoria.class);
                intent.putExtra("id", c.getString(idxId));
                intent.putExtra("titulo", c.getString(idxTitulo));
                startActivityForResult(intent, ListarCategorias.REQUEST_MAIN);
            }
        });
    }
}
