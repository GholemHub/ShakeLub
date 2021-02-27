package com.example.shakelab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteHolder> {

    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Note model) {
        holder.textViewShakeName.setText(model.getShakeName());
        holder.textViewIngredients.setText(model.getIngredients());
        holder.textViewLayers.setText(String.valueOf(model.getLayers()));
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);

        return new NoteHolder(v);
    }

    class NoteHolder extends RecyclerView.ViewHolder{

        TextView textViewShakeName;
        TextView textViewIngredients;
        TextView textViewLayers;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            textViewShakeName = itemView.findViewById(R.id.text_view_shake_name);
            textViewIngredients = itemView.findViewById(R.id.text_view_shake_ingredients);
            textViewLayers =  itemView.findViewById(R.id.text_view_layers);

        }
    }

}
