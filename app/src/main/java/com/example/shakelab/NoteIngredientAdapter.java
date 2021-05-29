package com.example.shakelab;

import android.app.Activity;
import android.util.Log;
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
import static com.example.shakelab.Create.NewValue;

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
        String names = "NON";

        //NoteIngredient currentItem = mNoteIngredientsList.get(num-1);

        //Log.d("NumIngredient", "Name: " + nivhList.size() + " Nname 2 " +  currentItem.getText().toString());



        /*for (NoteIngredientsViewHolder l: nivhList) {

            if(num == Integer.parseInt(l.ingredient_num.getText().toString())){
                //nivhList.clear();
                Log.d("NumIngredient", "Name: " /*+ nivhList.get(num).getiName() + " : " +
                        l.ingredient_name.getText().toString() + " : "
                        + nivhList.size()

                        );

                break;
            }

        }*/

        names = nivhList.get(num).iName;
        return names;
    }

    public String getIngredientNum(){
        return nivh.ingredient_num.toString();
    }
    public void setNameBtn(String str){
        nivh.ingrediant_percent.setText(str);
    }

    public void saveNames(){

        //nivhList.get()
        for (NoteIngredientsViewHolder l: nivhList) {
            l.setIngredientInfo(l.ingredient_name.getText().toString());
        }
    }
    public void saveNames2(int num){

        for(int i = 0; i < num; i++){
            nivhList.get(i).setIngredientInfo(nivhList.get(i).iName);
        }/*
        //nivhList.get()
        for (NoteIngredientsViewHolder l: nivhList) {
            l.setIngredientInfo(l.ingredient_name.getText().toString());
        }*/
    }

    public void error(){
        nivh.error();
    }

    public static class NoteIngredientsViewHolder extends RecyclerView.ViewHolder implements PercentDialog.PercentDialogListener{
        public EditText ingredient_name;
        public TextView ingredient_num;
        public Button ingrediant_percent;

        private int iPercent;

        private String iName = "123";

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
        if(nivh.iName != null){

        }
        //Log.d("NumIngredient", "onCreateViewHolder1: " + nivhList.size() );
        Log.d("NumIngredient", "ingredient_num: " + NewValue );

                //remove
                if(nivhList.size() >= NewValue){
                    int j = nivhList.size() - NewValue;
                    for(int i = 0; i < j; i++){
                        nivhList.remove(i);
                    }
                }
                nivhList.add(nivh);


        Log.d("NumIngredient", "onCreateViewHolder2: " + nivhList.size() );
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
        holder.ingredient_num.setText(currentItem.getCountOfIngredient());

        holder.ingrediant_percent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PercentDialog percentDialog = new PercentDialog(holder.ingredient_num.getText().toString());
                percentDialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "2" );

                int numer = Integer.parseInt(holder.ingredient_num.getText().toString());
                NoteIngredient note = mNoteIngredientsList.get(numer - 1);
                holder.ingrediant_percent.setText("" + note.btnName);
            }
    });
    }

    @Override
    public int getItemCount() {
        return mNoteIngredientsList.size();
    }
}
