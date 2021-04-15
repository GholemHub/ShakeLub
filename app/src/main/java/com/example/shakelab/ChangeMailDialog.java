package com.example.shakelab;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class ChangeMailDialog extends AppCompatDialogFragment {
    private EditText change_Email_View;
    private Button Apply_Changes ;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.change_mail_dialog, null);

        builder.setView(view).setTitle("Change email").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



            }
        });

        Apply_Changes = view.findViewById(R.id.Apply_Changes);
        change_Email_View = view.findViewById(R.id.change_Email_View);
        change_Email_View.setText("");
        btnSave();

        return builder.create();
    }

    private void btnSave() {
        Apply_Changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase();
            }


        });


    }
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    private void Firebase() {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DocumentReference documentReference = fStore.collection("users").document(user.getEmail());

        Map<String,Object> newMail = new HashMap<>();

        if(change_Email_View.getText().toString() == null)
        {
            return;
        }
        if(change_Email_View.getText().toString() == "")
        {
            return;
        }
        if(!change_Email_View.getText().toString().isEmpty())
        {
            Toast.makeText(getActivity(), "!!!!!!!!!", Toast.LENGTH_SHORT).show();
            newMail.put("nickname", change_Email_View.getText().toString());
            documentReference.set(newMail).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "None", Toast.LENGTH_SHORT).show();
                }
            });
        }



    }
}
