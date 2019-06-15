package ufjf.dcc196.headhunter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.nio.DoubleBuffer;

public class CadastrarProducao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_producao);

        final EditText etTitulo = findViewById(R.id.editTextTituloProducao);
        final EditText etDescricao = findViewById(R.id.editTextDescricaoProducao);
        final EditText etInicio = findViewById(R.id.editTextDataInicioProducao);
        final EditText etFim = findViewById(R.id.editTextDataFimProducao);

        Button btnCadastar = findViewById(R.id.btnCadastarProducao);

        btnCadastar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.putExtra("titulo", etTitulo.getText());
                intent1.putExtra("descricao", etDescricao.getText());
                intent1.putExtra("inicio", etInicio.getText());

                try{
                    intent1.putExtra("fim", etFim.getText());
                }catch (NullPointerException ex){
                    intent1.putExtra("fim", "");
                }

                setResult(Activity.RESULT_OK, intent1);
                finish();
            }
        });
    }
}
