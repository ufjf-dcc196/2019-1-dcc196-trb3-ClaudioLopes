package ufjf.dcc196.headhunter;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import Persistencia.BD;
import Persistencia.BibliotecaDbHelper;

public class ListaProcucao extends AppCompatActivity {
    public Cursor c;
    public static final int REQUEST_MAIN = 1;
    public static final int REQUEST_LIST = 2;
    public static final int REQUEST_EDT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_procucao);

        BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(this);

        SQLiteDatabase dbR = bibliotecaHelper.getReadableDatabase();
        String[] visao = {
                BD.Producao._ID,
                BD.Producao.COLUMN_NAME_TITULO,
                BD.Producao.COLUMN_NAME_DESCRICAO,
                BD.Producao.COLUMN_NAME_INICIO,
                BD.Producao.COLUMN_NAME_FIM,
                BD.Producao.COLUMN_NAME_ID_CANDIDATO,
        };
        String selecao = BD.Producao._ID + " >= ?";
        String[] args = {"0"};
        String sort = BD.Producao._ID + " DESC";
        c = dbR.query(BD.Producao.TABLE_NAME, visao, selecao, args, null, null  , sort);
        c.moveToFirst();

        RecyclerView recyclerView = findViewById(R.id.rvProducao);
        ProducaoAdapter producaoAdapter = new ProducaoAdapter(c, this);
        recyclerView.setAdapter(producaoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        producaoAdapter.setOnProducaoAdapterClickListener(new ProducaoAdapter.OnProducaoAdapterClickListener() {
            @Override
            public void onProducaoAdapterClick(View v, int possition) {
                c.moveToPosition(possition);
                int idxId = c.getColumnIndexOrThrow(BD.Categoria._ID);
                int idxTitulo = c.getColumnIndexOrThrow(BD.Categoria.COLUMN_NAME_TITULO);

                Intent intent = new Intent(ListaProcucao.this, EditarProducao.class);
                intent.putExtra("id", c.getString(idxId));
                intent.putExtra("titulo", c.getString(idxTitulo));
                startActivityForResult(intent, ListaProcucao.REQUEST_EDT);
            }
        });

        Button btnCadastar = findViewById(R.id.btnListarCategorias);

        btnCadastar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaProcucao.this, CadastrarCategoria.class);
                startActivityForResult(intent, ListaProcucao.REQUEST_MAIN);
            }
        });

        Button btnListar = findViewById(R.id.btnRVCategoria);

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaProcucao.this, ListarCategorias.class);
                startActivityForResult(intent, ListaProcucao.REQUEST_LIST);
            }
        });
    }
}
