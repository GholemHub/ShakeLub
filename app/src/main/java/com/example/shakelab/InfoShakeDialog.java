package com.example.shakelab;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.stats.GCoreWakefulBroadcastReceiver;

public class InfoShakeDialog extends AppCompatDialogFragment {
    private TextView nameShake;
    private TextView recipe;

    private String name;
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

        nameShake = view.findViewById(R.id.text_view_shake_name);
        recipe = view.findViewById(R.id.text_view_shake_recipe);

        name = note.getShakeName();


        nameShake.setText(name);
        recipe.setText(name);

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
