package com.example.shakelab;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.example.shakelab.Create.IngredientsCount;

//ADAPTER FOR NOTEINGREDIENT
public class NoteIngredientAdapter extends RecyclerView.Adapter<NoteIngredientAdapter.NoteIngredientsViewHolder> {

    public NoteIngredientsViewHolder nivh;
    public static List<NoteIngredientsViewHolder> nivhList = new ArrayList<NoteIngredientsViewHolder>();

    public static ArrayList<NoteIngredient> mNoteIngredientsList;

    private OnItemClickListener mListener;

    private static Activity context;

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public void PercentError() {
        nivh.PercentError();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public String getIngredientInfo3(int num){
        String names = "";
        int i = -1;
        for (NoteIngredientsViewHolder l: nivhList) {

            if(num == i){
                names = l.getiName();
                break;
            }
            i++;
        }
        return names;
    }

    public String getIngredientNum(){
        return nivh.ingredient_num.toString();
    }
    public void setNameBtn(String str){
        nivh.ingrediant_percent.setText(str);
    }

    public void saveNames(){
        for (NoteIngredientsViewHolder l: nivhList) {
            l.setIngredientInfo(l.ingredient_name.getText().toString());
        }
    }

    public void error(){
        nivh.error();
    }

    public static class NoteIngredientsViewHolder extends RecyclerView.ViewHolder implements PercentDialog.PercentDialogListener{
        public EditText ingredient_name;
        public TextView ingredient_num;
        public Button ingrediant_percent;

        private int iPercent;

        private String iName;

        public int getiPercent() {
            return iPercent;
        }

        public void setiPercent(int iPercent) {
            this.iPercent = iPercent;
        }



        public String getiName() {
            return iName;
        }

        public void error(){
            ingredient_name.setError("Required");
        }

        public void setIngredientInfo(String name){
            iName = name;
        }

        public void setIngredientInfo2(String name, int percent){
            iName = name;
            iPercent = percent;
        }

        @Override
        public void applyTexts(int iPercent, String num) {//INTERFACE WHICH HELP TO CONNECT NOTEADAPTER WITH CREATE CONTEXT
            Toast.makeText(context, "Create2: " + iPercent, Toast.LENGTH_SHORT).show();
        }


        public NoteIngredientsViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            ingredient_name = itemView.findViewById(R.id.ingredient_name);
            ingredient_num = itemView.findViewById(R.id.text_view_numberOfingredient);
            ingrediant_percent = itemView.findViewById(R.id.percent_ntb);

            ingrediant_percent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PercentDialog percentDialog = new PercentDialog(ingredient_num.getText().toString());
                    percentDialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "2" );

                    int numer = Integer.parseInt(ingredient_num.getText().toString());
                    NoteIngredient note = mNoteIngredientsList.get(numer - 1);
                    ingrediant_percent.setText("" + note.btnName);

                }
            });
        }

        public void PercentError() {
            ingrediant_percent.setError("Required");
        }
    }




    @NonNull
    @Override
    public NoteIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_create_item, parent, false);

        nivh = new NoteIngredientsViewHolder(v, mListener);
        nivhList.add(new NoteIngredientsViewHolder(v, mListener));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return nivh;//OUR NOTEINGREDIENT ITEM
    }


    public NoteIngredientAdapter(ArrayList<NoteIngredient> noteList, Activity context){
        mNoteIngredientsList = new ArrayList<NoteIngredient>();

        this.context = context;

        mNoteIngredientsList = noteList;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteIngredientsViewHolder holder, int position) {
        NoteIngredient currentItem = mNoteIngredientsList.get(position);

        holder.ingredient_name.setText(currentItem.getNameOfIngredient());
        holder.ingredient_name.getText();
        holder.ingredient_num.setText(currentItem.getCountOfIngredient());
    }

    @Override
    public int getItemCount() {
        return mNoteIngredientsList.size();
    }
}
