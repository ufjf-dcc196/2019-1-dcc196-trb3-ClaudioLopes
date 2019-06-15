package ufjf.dcc196.headhunter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Persistencia.BD;
import Persistencia.BibliotecaDbHelper;

public class CadastrarAtividade extends AppCompatActivity {
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_atividade);

        final EditText etDescricao = findViewById(R.id.editTextDescricaoAtividade);
        final EditText etData = findViewById(R.id.editTextDataAtividade);
        final EditText etHoras = findViewById(R.id.editTextHorasAtividade);

        Button btnCadastrar = findViewById(R.id.btnConfirmarAtividade);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(context);
                SQLiteDatabase db = bibliotecaHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(BD.Atividade.COLUMN_NAME_DESCRICAO, etDescricao.getText().toString());
                try {
                    values.put(BD.Atividade.COLUMN_NAME_DATA, etData.getText().toString());
                }catch (NullPointerException ex){
                    values.put(BD.Atividade.COLUMN_NAME_DATA, "");
                }

                values.put(BD.Atividade.COLUMN_NAME_HORAS, Integer.valueOf(etHoras.getText().toString()));
                long id = db.insert(BD.Atividade.TABLE_NAME, null, values);

                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }
}
