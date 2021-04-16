package com.example.shakelab;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.squareup.picasso.Picasso;

public class InfoShakeDialog extends AppCompatDialogFragment {
    private TextView nameShake;
    private ImageView shakeView;
    private TextView layers;
    private TextView ingredients;

    private String name;
    private String Image;
    private Note note;


    InfoShakeDialog(Note note){
        this.note = note;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_info_shake, null);

        shakeView = view.findViewById(R.id.shake_view);
        nameShake = view.findViewById(R.id.text_view_shake_name);

        layers = view.findViewById(R.id.text_view_ingredients_count);
        ingredients = view.findViewById(R.id.IngredientsInfo);

        setLayers();
        setIngredients();
        nameShake.setText(note.getShakeName());

        Image = note.getShakeImage();

        if(note.getShakeImage() != null && note.getShakeImage() != ""){
            Picasso.get().load(Image).into(shakeView);
        }
        else{

            //Picasso.get().load(Image).into(shakeView);
        }


        builder.setView(view).setTitle("").setNegativeButton("cansel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    private void setIngredients() {

        ingredients.setText(note.getShakeIngredientsString2());

    }

    private void setLayers() {
        layers.setText( layers.getText() +" " + note.getCountOfLayers());
    }
}
