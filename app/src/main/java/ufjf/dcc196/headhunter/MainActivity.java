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
import android.widget.Button;

import Persistencia.BD;
import Persistencia.BibliotecaDbHelper;

public class MainActivity extends AppCompatActivity {
    public Cursor c;
    public static final int REQUEST_MAIN = 1;
    public static final int REQUEST_EDT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(this);

        SQLiteDatabase dbR = bibliotecaHelper.getReadableDatabase();
        String[] visao = {
                BD.Candidato._ID,
                BD.Candidato.COLUMN_NAME_NOME,
                BD.Candidato.COLUMN_NAME_DATA_NASCIMENTO,
                BD.Candidato.COLUMN_NAME_TELEFONE,
                BD.Candidato.COLUMN_NAME_PERFIL,
                BD.Candidato.COLUMN_NAME_EMAIL,
        };
        String selecao = BD.Candidato._ID + " >= ?";
        String[] args = {"0"};
        String sort = BD.Candidato._ID + " DESC";
        c = dbR.query(BD.Candidato.TABLE_NAME, visao, selecao, args, null, null  , sort);
        c.moveToFirst();

        Button btnCadastar = findViewById(R.id.btnCadastarCandidato);

        btnCadastar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, CadastrarCandidato.class);
                startActivityForResult(intent, MainActivity.REQUEST_MAIN);
            }
        });

        recycleView();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if (requestCode == MainActivity.REQUEST_MAIN) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(this);
                SQLiteDatabase db = bibliotecaHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(BD.Candidato.COLUMN_NAME_NOME, bundle.get("nome").toString());
                values.put(BD.Candidato.COLUMN_NAME_DATA_NASCIMENTO, bundle.get("data").toString());
                values.put(BD.Candidato.COLUMN_NAME_TELEFONE, bundle.get("telefone").toString());
                values.put(BD.Candidato.COLUMN_NAME_PERFIL, bundle.get("perfil").toString());
                values.put(BD.Candidato.COLUMN_NAME_EMAIL, bundle.get("email").toString());
                long id = db.insert(BD.Candidato.TABLE_NAME, null, values);
            }
        }
        if (requestCode == MainActivity.REQUEST_EDT) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(this);

                ContentValues values = new ContentValues();
                try {
                    SQLiteDatabase db = bibliotecaHelper.getWritableDatabase();
                    values.put(BD.Candidato.COLUMN_NAME_NOME, bundle.get("nome").toString());
                    values.put(BD.Candidato.COLUMN_NAME_DATA_NASCIMENTO, bundle.get("data").toString());
                    values.put(BD.Candidato.COLUMN_NAME_TELEFONE, bundle.get("telefone").toString());
                    values.put(BD.Candidato.COLUMN_NAME_PERFIL, bundle.get("perfil").toString());
                    values.put(BD.Candidato.COLUMN_NAME_EMAIL, bundle.get("email").toString());
                    String select = BD.Candidato._ID + " = ?";
                    String[] selectArgs = {bundle.get("id").toString()};
                    db.update(BD.Candidato.TABLE_NAME, values, select, selectArgs);
                }catch (NullPointerException ex){
                    SQLiteDatabase db = bibliotecaHelper.getWritableDatabase();
                    String select = BD.Candidato._ID + " = ?";
                    String[] selectArgs = {bundle.get("id").toString()};
                    db.delete(BD.Candidato.TABLE_NAME, select, selectArgs);
                }
            }
        }
        recycleView();
    }

    private void recycleView(){
        RecyclerView recyclerView = findViewById(R.id.rvCandidato);
        CandidatoAdapter candidatoAdapter = new CandidatoAdapter(c, this);
        recyclerView.setAdapter(candidatoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        candidatoAdapter.setOnCandidatoAdapterClickListener(new CandidatoAdapter.OnCandidatoAdapterClickListener() {
            @Override
            public void onCandidatoAdapterClick(View v, int possition) {
                c.moveToPosition(possition);
                int idxId = c.getColumnIndexOrThrow(BD.Candidato._ID);
                int idxNome = c.getColumnIndexOrThrow(BD.Candidato.COLUMN_NAME_NOME);
                int idxDataNascimento = c.getColumnIndexOrThrow(BD.Candidato.COLUMN_NAME_DATA_NASCIMENTO);
                int idxTelefone = c.getColumnIndexOrThrow(BD.Candidato.COLUMN_NAME_TELEFONE);
                int idxPerfil = c.getColumnIndexOrThrow(BD.Candidato.COLUMN_NAME_PERFIL);
                int idxEmail = c.getColumnIndexOrThrow(BD.Candidato.COLUMN_NAME_EMAIL);

                Intent intent = new Intent(MainActivity.this, EditarCandidato.class);
                intent.putExtra("id", c.getString(idxId));
                intent.putExtra("nome", c.getString(idxNome));
                intent.putExtra("data", c.getString(idxDataNascimento));
                intent.putExtra("telefone", c.getString(idxTelefone));
                intent.putExtra("perfil", c.getString(idxPerfil));
                intent.putExtra("email", c.getString(idxEmail));
                startActivityForResult(intent, MainActivity.REQUEST_EDT);

            }
        });
    }
}
