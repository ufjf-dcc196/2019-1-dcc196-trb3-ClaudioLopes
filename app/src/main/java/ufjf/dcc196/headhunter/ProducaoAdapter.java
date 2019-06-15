package ufjf.dcc196.headhunter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Persistencia.BD;
import Persistencia.BibliotecaDbHelper;

public class ProducaoAdapter extends RecyclerView.Adapter<ProducaoAdapter.ViewHolder> {
    private Cursor items;
    private Context contexto;
    private OnProducaoAdapterClickListener listener;

    public ProducaoAdapter(Cursor items, Context context){
        this.items = items;
        this.contexto = context;
    }

    public void setOnProducaoAdapterClickListener(OnProducaoAdapterClickListener listener){
        this.listener = listener;
    }

    public ProducaoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inlf = LayoutInflater.from(context);
        View linha = inlf.inflate(R.layout.activity_producao_adaapter, viewGroup, false);
        ProducaoAdapter.ViewHolder vh = new ProducaoAdapter.ViewHolder(linha);
        return vh;
    }

    public void onBindViewHolder(@NonNull ProducaoAdapter.ViewHolder viewHolder, int i) {
        BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(contexto);
        SQLiteDatabase db = bibliotecaHelper.getReadableDatabase();
        String[] visao = {
                BD.Producao._ID,
                BD.Producao.COLUMN_NAME_TITULO,
                BD.Producao.COLUMN_NAME_DESCRICAO,
                BD.Producao.COLUMN_NAME_INICIO,
                BD.Producao.COLUMN_NAME_FIM,
                BD.Producao.COLUMN_NAME_ID_CANDIDATO,
        };
        String selecao = BD.Producao._ID + " >= ?";
        String[] args = {"0"};
        String sort = BD.Producao._ID + " DESC";
        items = db.query(BD.Producao.TABLE_NAME, visao, selecao, args, null, null  , sort);

        int idxTitulo = items.getColumnIndexOrThrow(BD.Producao.COLUMN_NAME_TITULO);
        int idxDescricao = items.getColumnIndexOrThrow(BD.Producao.COLUMN_NAME_DESCRICAO);
        int idxInicio = items.getColumnIndexOrThrow(BD.Producao.COLUMN_NAME_INICIO);
        int idxFim = items.getColumnIndexOrThrow(BD.Producao.COLUMN_NAME_FIM);

        items.moveToPosition(i);
        viewHolder.textTitulo.setText(items.getString(idxTitulo));
        viewHolder.textDescricao.setText(items.getString(idxDescricao));
        viewHolder.textInicio.setText(items.getString(idxInicio));
        viewHolder.textFim.setText(items.getString(idxFim));
    }

    public int getItemCount(){
        if (items == null){
            return 0;
        }
        return items.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textTitulo;
        public TextView textDescricao;
        public TextView textInicio;
        public TextView textFim;

        public void onClick(View v) {
            int possition = getAdapterPosition();
            if(possition != RecyclerView.NO_POSITION){
                listener.onProducaoAdapterClick(v, possition);
            }
        }

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            textTitulo = itemView.findViewById(R.id.textViewTitulo);
            textDescricao = itemView.findViewById(R.id.textViewDescricao);
            textInicio = itemView.findViewById(R.id.textViewInicio);
            textFim = itemView.findViewById(R.id.textViewFin);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onProducaoAdapterClick(v, getAdapterPosition());
                    }
                }
            });

        }
    }

    public interface OnProducaoAdapterClickListener{
        public void onProducaoAdapterClick(View v, int possition);
    }
}
