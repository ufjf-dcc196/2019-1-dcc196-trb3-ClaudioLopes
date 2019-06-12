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

import Persistencia.BibliotecaDbHelper;

public class CandidatoAdapter extends RecyclerView.Adapter<CandidatoAdapter.ViewHolder> {
    private Cursor items;
    private Context contexto;
    private OnCandidatoAdapterClickListener listener;

    public CandidatoAdapter(Cursor items, Context context){
        this.items = items;
        this.contexto = context;
    }

    public void setOnCandidatoAdapterClickListener(OnCandidatoAdapterClickListener listener){
        this.listener = listener;
    }

    public CandidatoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inlf = LayoutInflater.from(context);
        View linha = inlf.inflate(R.layout.activity_candidato_adapter, viewGroup, false);
        ViewHolder vh = new ViewHolder(linha);
        return vh;
    }

    public void onBindViewHolder(@NonNull CandidatoAdapter.ViewHolder viewHolder, int i) {
        BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(contexto);
        SQLiteDatabase dbR = bibliotecaHelper.getReadableDatabase();

    }

    public int getItemCount(){
        return items.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textNome;
        public TextView textDataNascimento;
        public TextView textTelefone;
        public TextView textPerfil;
        public TextView textEmail;

        public void onClick(View v) {
            int possition = getAdapterPosition();
            if(possition != RecyclerView.NO_POSITION){
                listener.onCandidatoAdapterClick(v, possition);
            }
        }

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            textNome = itemView.findViewById(R.id.textViewNome);
            textDataNascimento = itemView.findViewById(R.id.textViewDataNascimento);
            textTelefone = itemView.findViewById(R.id.textViewTelefone);
            textPerfil = itemView.findViewById(R.id.textViewPerfil);
            textEmail = itemView.findViewById(R.id.textViewEmail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onCandidatoAdapterClick(v, getAdapterPosition());
                    }
                }
            });

        }
    }

    public interface OnCandidatoAdapterClickListener{
        public void onCandidatoAdapterClick(View v, int possition);
    }
}
