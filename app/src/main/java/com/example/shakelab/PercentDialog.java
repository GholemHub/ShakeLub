package com.example.shakelab;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

//import static com.example.shakelab.NoteIngredientAdapter.mNoteIngredientsList;
import static com.example.shakelab.NoteIngredientAdapter.mNoteIngredientsList;
import static com.example.shakelab.NoteIngredientAdapter.nivhList;

public class PercentDialog extends AppCompatDialogFragment {
    private NumberPicker numberPicker;
    private PercentDialogListener listener;
    public static int text = 0;
    public static String num;
    public static Button btn;

    public PercentDialog(String num){
        this.num = num;
    }
    public PercentDialog(String num, Button btn){
        this.num = num;
        this.btn = btn;
    }


    @Override
    public void onAttach(@NonNull Context context) {//HELP US TO REMEMBER THAT WE HAVE TO IMPLEMENT THE INTERFACE IN CREATE CONTEXT
        super.onAttach(context);

        try {
            listener = (PercentDialogListener) context;
        }catch (ClassCastException e){
            throw  new ClassCastException(context.toString() + "must implement PercentDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.percent_dialog, null);


//BUILDER OF OUR DIALOG
        builder.setView(view).setTitle("").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (NoteIngredientAdapter.NoteIngredientsViewHolder list: nivhList) {
                    int ingredientNum = Integer.parseInt((String) list.ingredient_num.getText());
                    if(ingredientNum == Integer.parseInt(num)){

                        list.ingrediant_percent.setText("%");

                    }
                }
            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                listener.applyTexts(numberPicker.getValue(), num);

                // CHOOSE THE CORRECT BNT FOR CHANGE BNT->TEXT
                for (NoteIngredientAdapter.NoteIngredientsViewHolder list: nivhList) {
                    int ingredientNum = Integer.parseInt((String) list.ingredient_num.getText());
                    if(ingredientNum == Integer.parseInt(num)){

                        list.ingrediant_percent.setText("" + numberPicker.getValue());
                        NoteIngredient note =  mNoteIngredientsList.get(ingredientNum-1);
                        note.setPercentOfIngredient("" + numberPicker.getValue());
                    }
                }
            }
        });

        getNumerPickerValue(view);

        return builder.create();
    }


    public interface PercentDialogListener{//INTERFACE
        void applyTexts(int iPercent, String num);

    }

    private void getNumerPickerValue(View view) {
        numberPicker = view.findViewById(R.id.percent_numerPicker);
        numberPicker.setMaxValue(20);
        numberPicker.setMinValue(1);
    }
}
