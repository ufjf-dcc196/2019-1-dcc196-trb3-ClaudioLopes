package ufjf.dcc196.headhunter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Persistencia.BD;
import Persistencia.BibliotecaDbHelper;

public class AtividadeAdapter extends RecyclerView.Adapter<AtividadeAdapter.ViewHolder>  {

    private Cursor items;
    private Context contexto;
    private OnAtividadeAdapterClickListener listener;

    public AtividadeAdapter(Cursor items, Context context){
        this.items = items;
        this.contexto = context;
    }

    public void setOnAtividadeAdapterClickListener(OnAtividadeAdapterClickListener listener){
        this.listener = listener;
    }

    public AtividadeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inlf = LayoutInflater.from(context);
        View linha = inlf.inflate(R.layout.activity_atividade_adapter, viewGroup, false);
        AtividadeAdapter.ViewHolder vh = new AtividadeAdapter.ViewHolder(linha);
        return vh;
    }

    public void onBindViewHolder(@NonNull AtividadeAdapter.ViewHolder viewHolder, int i) {
        BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(contexto);
        SQLiteDatabase db = bibliotecaHelper.getReadableDatabase();
        String[] visao = {
                BD.Atividade._ID,
                BD.Atividade.COLUMN_NAME_DESCRICAO,
                BD.Atividade.COLUMN_NAME_DATA,
                BD.Atividade.COLUMN_NAME_HORAS,
        };
        String selecao = BD.Atividade._ID + " >= ?";
        String[] args = {"0"};
        String sort = BD.Atividade._ID + " DESC";
        items = db.query(BD.Atividade.TABLE_NAME, visao, selecao, args, null, null  , sort);

        int idxDescricao = items.getColumnIndexOrThrow(BD.Atividade.COLUMN_NAME_DESCRICAO);
        int idxData = items.getColumnIndexOrThrow(BD.Atividade.COLUMN_NAME_DATA);
        int idxHora = items.getColumnIndexOrThrow(BD.Atividade.COLUMN_NAME_HORAS);

        items.moveToPosition(i);
        viewHolder.textDescricao.setText(items.getString(idxDescricao));
        try {
            viewHolder.textData.setText(items.getString(idxData));
        }catch (NullPointerException ex){
            viewHolder.textData.setText("erro");
        }
        viewHolder.textHora.setText(items.getString(idxHora));
    }

    public int getItemCount(){
        if (items == null){
            return 0;
        }
        return items.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textDescricao;
        public TextView textData;
        public TextView textHora;

        public void onClick(View v) {
            int possition = getAdapterPosition();
            if(possition != RecyclerView.NO_POSITION){
                listener.onAtividadeAdapterClick(v, possition);
            }
        }

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            textDescricao = itemView.findViewById(R.id.textViewDescricaoAtividade);
            textData = itemView.findViewById(R.id.textViewDataAtividade);
            textHora = itemView.findViewById(R.id.textViewHorasAtividade);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onAtividadeAdapterClick(v, getAdapterPosition());
                    }
                }
            });

        }
    }

    public interface OnAtividadeAdapterClickListener{
        public void onAtividadeAdapterClick(View v, int possition);
    }
}
