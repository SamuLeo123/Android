package com.example.a3aetim.teste;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class NotaAdapter extends RecyclerView.Adapter<NotaAdapter.ApplicationViewHolder> {
    private ArrayList<NotaClass> mNoteList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    public void setOnitemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class ApplicationViewHolder extends RecyclerView.ViewHolder{
        public TextView mTitle,mDesc;

        public ApplicationViewHolder(final View itemView, final OnItemClickListener listener){
            super(itemView);
            mTitle = itemView.findViewById(R.id.txtNotaUsu);
            mDesc = itemView.findViewById(R.id.txtBreveDescNota);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int pos =getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            listener.onItemClick(itemView,pos);
                        }
                    }
                }
            });
        }
    }
    public NotaAdapter(ArrayList<NotaClass> noteList){
        mNoteList = noteList;
    }
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.nota,viewGroup,false);
        ApplicationViewHolder avh = new ApplicationViewHolder(view,mListener);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder applicationViewHolder, int i) {
        CardView card = applicationViewHolder.itemView.findViewById(R.id.cardViewNota);
        NotaClass currentNote = mNoteList.get(i);
        applicationViewHolder.mTitle.setText(currentNote.getTitulo());
        applicationViewHolder.mDesc.setText(currentNote.getTexto().substring(0,3)+"....");
        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
        card.setCardBackgroundColor(currentColor);
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }
}
