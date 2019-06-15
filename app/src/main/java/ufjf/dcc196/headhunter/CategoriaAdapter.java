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

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder>  {

    private Cursor items;
    private Context contexto;
    private OnCategoriaAdapterClickListener listener;

    public CategoriaAdapter(Cursor items, Context context){
        this.items = items;
        this.contexto = context;
    }

    public void setOnCategoriaAdapterClickListener(OnCategoriaAdapterClickListener listener){
        this.listener = listener;
    }

    public CategoriaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inlf = LayoutInflater.from(context);
        View linha = inlf.inflate(R.layout.activity_categoria_adapter, viewGroup, false);
        CategoriaAdapter.ViewHolder vh = new CategoriaAdapter.ViewHolder(linha);
        return vh;
    }

    public void onBindViewHolder(@NonNull CategoriaAdapter.ViewHolder viewHolder, int i) {
        BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(contexto);
        SQLiteDatabase db = bibliotecaHelper.getReadableDatabase();
        String[] visao = {
                BD.Categoria._ID,
                BD.Categoria.COLUMN_NAME_TITULO,
        };
        String selecao = BD.Categoria._ID + " >= ?";
        String[] args = {"0"};
        String sort = BD.Categoria._ID + " DESC";
        items = db.query(BD.Categoria.TABLE_NAME, visao, selecao, args, null, null  , sort);

        int idxTitulo = items.getColumnIndexOrThrow(BD.Categoria.COLUMN_NAME_TITULO);

        items.moveToPosition(i);

        try {
            viewHolder.textTitulo.setText(items.getString(idxTitulo));
        }catch (NullPointerException ex){
            viewHolder.textTitulo.setText("erro");
        }
    }

    public int getItemCount(){
        if (items == null){
            return 0;
        }
        return items.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textTitulo;

        public void onClick(View v) {
            int possition = getAdapterPosition();
            if(possition != RecyclerView.NO_POSITION){
                listener.onCategoriaAdapterClick(v, possition);
            }
        }

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            textTitulo = itemView.findViewById(R.id.textViewTituloCategoria);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onCategoriaAdapterClick(v, getAdapterPosition());
                    }
                }
            });

        }
    }

    public interface OnCategoriaAdapterClickListener{
        public void onCategoriaAdapterClick(View v, int possition);
    }
}
