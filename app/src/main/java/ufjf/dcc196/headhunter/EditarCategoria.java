package ufjf.dcc196.headhunter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditarCategoria extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_categoria);

        final Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();

        final EditText etTitulo = findViewById(R.id.editTextTituloCategoria);
        etTitulo.setText(bundle.get("titulo").toString());

        Button btnEditar = findViewById(R.id.btnEditarCategoria);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.putExtra("id", bundle.get("id").toString());
                intent1.putExtra("titulo", etTitulo.getText());
                setResult(Activity.RESULT_OK, intent1);
                finish();
            }
        });

        Button btnExcluir = findViewById(R.id.btnExcluirCategoria);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.putExtra("id", bundle.get("id").toString());
                setResult(Activity.RESULT_OK, intent1);
                finish();
            }
        });
    }
}
