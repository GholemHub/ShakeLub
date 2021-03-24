package com.example.shakelab;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteIngredientAdapter extends RecyclerView.Adapter<NoteIngredientAdapter.NoteIngredientsViewHolder> {



    private ArrayList<NoteIngredient> mNoteIngredientsList;

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class NoteIngredientsViewHolder extends RecyclerView.ViewHolder{
        public EditText ingredient_name;
        public TextView ingredient_num;
        public TextView btn;

        public NoteIngredientsViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            ingredient_name = itemView.findViewById(R.id.ingredient_name);
            ingredient_num = itemView.findViewById(R.id.text_view_numberOfingredient);
            btn = itemView.findViewById(R.id.button);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                            Log.d("@@@: ", "POSITION: " + position);
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    btn.setText("" + position);
                                    Log.d("@@@: ", "POSITION: " + position);
                                }
                            });

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
        holder.ingredient_num.setText(currentItem.getCountOfIngredient());
        holder.btn.setText("" + position);
    }

    @Override
    public int getItemCount() {
        return mNoteIngredientsList.size();
    }
}
