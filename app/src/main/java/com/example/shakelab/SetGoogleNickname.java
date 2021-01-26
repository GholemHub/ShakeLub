package com.example.shakelab;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class SetGoogleNickname extends AppCompatDialogFragment {
    private EditText editTextNickname;
    private EditText editTextPassword;
    private ExampleDialodListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.set_google_nickname_dialog,null);

        builder.setView(view)
                .setTitle("Register")
                .setMessage("Wright your Nickname")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String userNickname = editTextNickname.getText().toString();
                        String userPassword = editTextPassword.getText().toString();
                        listener.applyTexts(userNickname, userPassword);
                    }
                });

        editTextNickname = view.findViewById(R.id.edit_nickname);
        editTextPassword = view.findViewById(R.id.edit_password);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) /**/{
        super.onAttach(context);
            try{
                listener = (ExampleDialodListener) context;
            }catch (ClassCastException e){
                throw new ClassCastException(context.toString() + "Must implement ExampleDialodListener");
            }

    }

    public interface ExampleDialodListener{
        void applyTexts(String username, String password);
    }

}
