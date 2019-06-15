package ufjf.dcc196.headhunter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import Persistencia.BD;
import Persistencia.BibliotecaDbHelper;

public class EditarProducao extends AppCompatActivity {
    private Context context = this;
    private Cursor cursor;

    public static final int REQUEST_MAIN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producao);

        final Intent intent = new Intent();
        final Bundle bundle = intent.getExtras();

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
        int idxTitulo = c.getColumnIndexOrThrow(BD.Categoria.COLUMN_NAME_TITULO);

        String[] itens = new String[c.getCount()];
        int i = 0;
        while (i < c.getCount()){
            itens[i] = (c.getString(idxTitulo));
            i ++;
            c.moveToNext();
        }

        final Spinner etCategoria = findViewById(R.id.plCategoria);
        ArrayAdapter adapterTag = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, itens);
        adapterTag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etCategoria.setAdapter(adapterTag);

        Button btnCategoria = findViewById(R.id.btnAssociaCategoria);
        btnCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(context);
                ContentValues values = new ContentValues();
                SQLiteDatabase db = bibliotecaHelper.getWritableDatabase();
                try {
                    values.put(BD.Categoria.COLUMN_NAME_ID_PRODUCAO, Integer.valueOf(bundle.get("id").toString()));
                }catch (NullPointerException ex){
                    values.put(BD.Categoria.COLUMN_NAME_ID_PRODUCAO, 0);
                }

                String select = BD.Categoria._ID + " = ?";
                String[] selectArgs = {String.valueOf(etCategoria.getSelectedItemId())};
                db.update(BD.Categoria.TABLE_NAME, values, select, selectArgs);

                setResult(Activity.RESULT_OK);
                finish();
            }
        });

        Button btnCadastar = findViewById(R.id.btnCadastrarAtividade);
        btnCadastar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditarProducao.this, CadastrarAtividade.class);
                startActivityForResult(intent, EditarProducao.REQUEST_MAIN);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if (requestCode == MainActivity.REQUEST_MAIN) {
            if (resultCode == Activity.RESULT_OK) {
                recycleView();
            }
        }
    }

    private void recycleView(){
        BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(this);
        SQLiteDatabase dbR = bibliotecaHelper.getReadableDatabase();
        String[] visao = {
                BD.Atividade._ID,
                BD.Atividade.COLUMN_NAME_DESCRICAO,
                BD.Atividade.COLUMN_NAME_DATA,
                BD.Atividade.COLUMN_NAME_HORAS,
        };
        String selecao = BD.Atividade._ID + " >= ?";
        String[] args = {"0"};
        String sort = BD.Atividade._ID + " DESC";
        cursor = dbR.query(BD.Atividade.TABLE_NAME, visao, selecao, args, null, null  , sort);

        RecyclerView recyclerView = findViewById(R.id.rvAtividades);
        AtividadeAdapter atividadeAdapter = new AtividadeAdapter(cursor, this);
        recyclerView.setAdapter(atividadeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        atividadeAdapter.setOnAtividadeAdapterClickListener(new AtividadeAdapter.OnAtividadeAdapterClickListener() {
            @Override
            public void onAtividadeAdapterClick(View v, int possition) {
                TextView tvAtividade = findViewById(R.id.textViewAtividades);

                cursor.moveToPosition(possition);
                int idxDescricao = cursor.getColumnIndexOrThrow(BD.Atividade.COLUMN_NAME_DESCRICAO);
                tvAtividade.setText(cursor.getString(idxDescricao) + ",");
            }
        });
    }
}
