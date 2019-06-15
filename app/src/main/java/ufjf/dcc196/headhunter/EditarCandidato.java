package ufjf.dcc196.headhunter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Persistencia.BD;
import Persistencia.BibliotecaDbHelper;

public class EditarCandidato extends AppCompatActivity {

    public static final int REQUEST_MAIN = 1;
    private int idCandidato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_candidato);

        final Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();

        idCandidato = Integer.valueOf(bundle.get("id").toString());

        final EditText etNome = findViewById(R.id.editTextNome);
        final EditText etDataNascimento = findViewById(R.id.editTextDataNascimento);
        final EditText etTelefoen = findViewById(R.id.editTextTelefone);
        final EditText etPerfil = findViewById(R.id.editTextPerfil);
        final EditText etEmail = findViewById(R.id.editTextEmail);

        etNome.setText(bundle.get("nome").toString());
        etDataNascimento.setText(bundle.get("data").toString());
        etTelefoen.setText(bundle.get("telefone").toString());
        etPerfil.setText(bundle.get("perfil").toString());
        etEmail.setText(bundle.get("email").toString());

        Button btnExcluir = findViewById(R.id.btnExcluir);

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.putExtra("id", bundle.get("id").toString());
                setResult(Activity.RESULT_OK, intent1);
                finish();
            }
        });

        Button btnEditar = findViewById(R.id.btnEditar);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.putExtra("id", bundle.get("id").toString());
                intent1.putExtra("nome", etNome.getText());
                intent1.putExtra("data", etDataNascimento.getText());
                intent1.putExtra("telefone", etTelefoen.getText());
                intent1.putExtra("perfil", etPerfil.getText());
                intent1.putExtra("email", etEmail.getText());
                setResult(Activity.RESULT_OK, intent1);
                finish();
            }
        });

        Button btnProducao = findViewById(R.id.btnProducao);

        btnProducao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditarCandidato.this, CadastrarProducao.class);
                startActivityForResult(intent, EditarCandidato.REQUEST_MAIN);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == EditarCandidato.REQUEST_MAIN) {
            if (resultCode == EditarCandidato.RESULT_OK) {
                Bundle bundle1 = data.getExtras();
                BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(this);
                SQLiteDatabase db = bibliotecaHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(BD.Producao.COLUMN_NAME_TITULO, bundle1.get("titulo").toString());
                values.put(BD.Producao.COLUMN_NAME_DESCRICAO, bundle1.get("descricao").toString());
                values.put(BD.Producao.COLUMN_NAME_INICIO, bundle1.get("inicio").toString());
                values.put(BD.Producao.COLUMN_NAME_FIM, bundle1.get("fim").toString());
                values.put(BD.Producao.COLUMN_NAME_ID_CANDIDATO, idCandidato);
                long id = db.insert(BD.Producao.TABLE_NAME, null, values);
            }
        }
    }
}
