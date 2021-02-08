package com.example.shakelab;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SetGoogleNickname extends AppCompatDialogFragment {
    private EditText editTextNickname;
    private EditText editTextPassword;
    private ExampleDialodListener listener;
    public static final String TAG = "TAG";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

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

                        RegisterData(userNickname);
                    }
                });

        editTextNickname = view.findViewById(R.id.edit_nickname);
        return builder.create();
    }
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userID;

    private void RegisterData(String userNickname) {


        fAuth.createUserWithEmailAndPassword(fAuth.getCurrentUser().getEmail(),"11111111").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    //Toast.makeText(SetGoogleNickname.this , "User Created", Toast.LENGTH_SHORT).show();
                    userID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("users").document(userID);
                    Map<String,Object> user = new HashMap<>();
                    user.put("nickname",userNickname);
                    //user.put("email",fAuth.getCurrentUser().getEmail());

                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onSuccess: user Profile is created for " + userID);
                        }
                    });
                    //VerifyUser();
                    //if(fAuth.getCurrentUser().isEmailVerified()){
                    //startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    //}
                }else{
                    //Toast.makeText(getApplicationContext(), "User Does not Created " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    public interface ExampleDialodListener{
        void applyTexts(String username, String password);
    }

}
