package ufjf.dcc196.headhunter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CadastrarCandidato extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_candidato);

        final EditText etNome = findViewById(R.id.editTextNome);
        final EditText etDataNascimento = findViewById(R.id.editTextDataNascimento);
        final EditText etTelefoen = findViewById(R.id.editTextTelefone);
        final EditText etPerfil = findViewById(R.id.editTextPerfil);
        final EditText etEmail = findViewById(R.id.editTextEmail);

        Button btnCadastrar = findViewById(R.id.btnConfirmarCandidato);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("nome", etNome.getText());
                intent.putExtra("data", etDataNascimento.getText());
                intent.putExtra("telefone", etTelefoen.getText());
                intent.putExtra("perfil", etPerfil.getText());
                intent.putExtra("email", etEmail.getText());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
