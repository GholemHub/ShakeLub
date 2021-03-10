package com.example.shakelab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NoteIngredientAdapter extends ArrayAdapter<NoteIngredient> {
    List list = new ArrayList();

    public NoteIngredientAdapter(Context context, int resourse, List<NoteIngredient> noteIngredientsList){
        super(context, resourse, noteIngredientsList);
    }

    public NoteIngredientAdapter(Context applicationContext, int note_create_item) {
        super(applicationContext,note_create_item);
    }

    @Override
    public void add(@Nullable NoteIngredient object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    static class NoteIngredientsHandler{
        EditText editText;

        public NoteIngredientsHandler(EditText editText) {
            this.editText = editText;
        }

        public NoteIngredientsHandler() {

        }

        public EditText getEditText() {
            return editText;
        }
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NoteIngredient noteIngredient = getItem(position);

        View line;
        line = convertView;
        NoteIngredientsHandler noteIngredientsHandler;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            line = inflater.inflate(R.layout.note_create_item, parent, false);
            noteIngredientsHandler = new NoteIngredientsHandler();
            noteIngredientsHandler.editText = line.findViewById(R.id.ingredient_name);
            line.setTag(noteIngredientsHandler);
        }else{
            noteIngredientsHandler = (NoteIngredientsHandler) line.getTag();
        }

        NoteIngredient noteIngredient1 = new NoteIngredient("132");
        noteIngredientsHandler.editText.setText(noteIngredient.getNameOfIngredient());
        return line;
    }
}
