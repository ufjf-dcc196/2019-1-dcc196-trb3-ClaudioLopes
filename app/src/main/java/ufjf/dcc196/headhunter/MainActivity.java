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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                values.put(BD.Candidato.COLUMN_NAME_NOME, bundle.getString("nome"));
                values.put(BD.Candidato.COLUMN_NAME_DATA_NASCIMENTO, bundle.getString("data"));
                values.put(BD.Candidato.COLUMN_NAME_TELEFONE, bundle.getString("telefone"));
                values.put(BD.Candidato.COLUMN_NAME_PERFIL, bundle.getString("perfil"));
                values.put(BD.Candidato.COLUMN_NAME_EMAIL, bundle.getString("email"));
                long id = db.insert(BD.Candidato.TABLE_NAME, null, values);
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
                int idxNome = c.getColumnIndexOrThrow(BD.Candidato.COLUMN_NAME_NOME);
                int idxDataNascimento = c.getColumnIndexOrThrow(BD.Candidato.COLUMN_NAME_DATA_NASCIMENTO);
                int idxTelefone = c.getColumnIndexOrThrow(BD.Candidato.COLUMN_NAME_TELEFONE);
                int idxPerfil = c.getColumnIndexOrThrow(BD.Candidato.COLUMN_NAME_PERFIL);
                int idxEmail = c.getColumnIndexOrThrow(BD.Candidato.COLUMN_NAME_EMAIL);

            }
        });
    }
}
