package ufjf.dcc196.headhunter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditarCandidato extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_candidato);

        final Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();

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
    }
}
