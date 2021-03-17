package com.example.shakelab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteIngredientAdapter extends RecyclerView.Adapter<NoteIngredientAdapter.NoteIngredientsViewHolder> {



    private ArrayList<NoteIngredient> mNoteIngredientsList;

    private OnItemClicklListener mListener;

    public void setOnItemClickListener(OnItemClicklListener listener){
        mListener = listener;
    }

    public interface OnItemClicklListener {
        void onItemClick(int position);
    }

    public static class NoteIngredientsViewHolder extends RecyclerView.ViewHolder{
        public EditText ingredient_name;

        public NoteIngredientsViewHolder(@NonNull View itemView, OnItemClicklListener listener) {
            super(itemView);

            ingredient_name = itemView.findViewById(R.id.ingredient_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }



    @NonNull
    @Override
    public NoteIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_create_item, parent, false);
        NoteIngredientsViewHolder nivh = new NoteIngredientsViewHolder(v, mListener);

        return nivh;
    }

    public NoteIngredientAdapter(ArrayList<NoteIngredient> noteList){
        mNoteIngredientsList = new ArrayList<NoteIngredient>();
        mNoteIngredientsList = noteList;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteIngredientsViewHolder holder, int position) {
        NoteIngredient currentItem = mNoteIngredientsList.get(position);
        holder.ingredient_name.setText(currentItem.getNameOfIngredient());
    }

    @Override
    public int getItemCount() {
        return mNoteIngredientsList.size();
    }
}
