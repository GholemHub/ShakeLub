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
import java.util.jar.Attributes;

public class NoteIngredientAdapter extends RecyclerView.Adapter<NoteIngredientAdapter.NoteIngredientsViewHolder> {



    private ArrayList<NoteIngredient> mNoteIngredientsList;

    private OnItemClickListener mListener;
    private static String Name;

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public String getIngredientInfo2(){
        return nivh.getiName();
    }

    public void error(){
        nivh.error();
    }

    public static class NoteIngredientsViewHolder extends RecyclerView.ViewHolder{
        public EditText ingredient_name;
        public TextView ingredient_num;
        public TextView btn;

        private String iName;

        public String getiName() {
            return iName;
        }

        public void error(){
            ingredient_name.setError("Required");
        }

        public void setIngredientInfo(String name){
            iName = name;
            //return name;
        }


        public NoteIngredientsViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            ingredient_name = itemView.findViewById(R.id.ingredient_name);
            ingredient_num = itemView.findViewById(R.id.text_view_numberOfingredient);
            btn = itemView.findViewById(R.id.button);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setIngredientInfo(ingredient_name.getText().toString());
                    btn.setText("SAVED");
                    btn.setBackgroundColor(itemView.getResources().getColor(R.color.coral));
                }
            });
        }
    }

    public NoteIngredientsViewHolder nivh;
    @NonNull
    @Override
    public NoteIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_create_item, parent, false);
        nivh = new NoteIngredientsViewHolder(v, mListener);

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
        holder.ingredient_name.getText();
        holder.ingredient_num.setText(currentItem.getCountOfIngredient());
        holder.btn.setText("SAVE");
    }

    @Override
    public int getItemCount() {
        return mNoteIngredientsList.size();
    }
}
