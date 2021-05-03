package com.example.shakelab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

//ADAPTER FOR NOTEADAPTER
public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteHolder> implements Filterable {
private OnItemClickListener listener;

    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options, List<Note> noteList) {
        super(options);
        this.noteList = noteList;
        noteListFull = new ArrayList<>(noteList);
    }
    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options) {
        super(options);

    }

    @Override
    public void updateOptions(@NonNull FirestoreRecyclerOptions<Note> options) {
        super.updateOptions(options);

    }

    public  List<Note> noteList;
    private List<Note> noteListFull;

    @Override
    public Filter getFilter() {
        return noteFilter;
    }

    private Filter noteFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Note> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(noteListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Note note: noteListFull) {
                    if(note.getShakeName().toLowerCase().contains(filterPattern)){
                        filteredList.add(note);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            noteList.clear();
            noteList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    //BRIDGE BETWEEN XML & NOTE.JAVA
    class NoteHolder extends RecyclerView.ViewHolder{

        TextView textViewShakeName;
        TextView text_view_shake_ingredients;
        ImageView imageViewShake;


        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            textViewShakeName = itemView.findViewById(R.id.text_view_shake_name);
            imageViewShake = itemView.findViewById(R.id.image_view_shake);
            text_view_shake_ingredients = itemView.findViewById(R.id.text_view_shake_ingredients);

            itemView.setOnClickListener(new View.OnClickListener() {//ITEM = TOUCHED(CHOSE) XML FILE
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }


    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Note model) {
        //LIKE ONCREATE METHOD BUT FOR VIEWHOLDER
        holder.textViewShakeName.setText(model.getShakeName());
        holder.text_view_shake_ingredients.setText(String.valueOf(model.getShakeIngredientsString()));

        if(model.getShakeImage() != null && model.getShakeImage() != ""){
            Picasso.get().load(model.getShakeImage()).into(holder.imageViewShake);
        }
        else{

        }
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);

        return new NoteHolder(v);
    }
}
