package com.example.shakelab;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.stats.GCoreWakefulBroadcastReceiver;
import com.squareup.picasso.Picasso;

public class InfoShakeDialog extends AppCompatDialogFragment {
    private TextView nameShake;
    private TextView recipe;
    private ImageView shakeView;

    private String name;
    private String name2;
    private Note note;

    private ImageView image_view_shake;

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
        image_view_shake = view.findViewById(R.id.image_view_shake);
        recipe = view.findViewById(R.id.text_view_shake_recipe);



        name = note.getShakeName();
        name2 = note.getshakeImage();

        nameShake.setText(name2);
        recipe.setText(name);


        if(note.getshakeImage() != ""){
            Picasso.get().load(name2).into(shakeView);

        }
        else{


            //Picasso.get().load(name2).into(shakeView);
        }


        builder.setView(view).setTitle(note.getShakeName()).setNegativeButton("cansel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

       // btn = view.findViewById(R.id.button1);

        return builder.create();
    }
}
