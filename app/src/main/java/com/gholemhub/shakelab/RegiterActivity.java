package com.gholemhub.shakelab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gholemhub.shakelab.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegiterActivity extends AppCompatActivity {

    private EditText textEmail;
    private EditText textPassword;
    private EditText textNickname;

    private CheckBox Policy;

    private TextView BackToSignIn;

    ProgressBar progressBar;
    private Button SignUp;


    public static final String TAG = "TAG";

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_window);

        BackToSignIn = findViewById(R.id.RegSignIn);

        textPassword = findViewById(R.id.EditTextPassword);
        textEmail = findViewById(R.id.EditTextEmail);
        textNickname = findViewById(R.id.EditTextNickname);

        Policy = findViewById(R.id.checkBoxPolicy);
        SignUp = findViewById(R.id.BtnSignUp);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        //progressBar = findViewById(R.id.progressBar);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = textEmail.getText().toString().trim();
                String Password = textPassword.getText().toString().trim();
                String Nickname = textNickname.getText().toString().trim();

                //ERRORS
                if(TextUtils.isEmpty(Nickname)){
                    textNickname.setError("Nickname is required");
                    return;
                }

                if(TextUtils.isEmpty(Email)){
                    textEmail.setError("Email is required");
                    return;
                }

                if (Password.length() < 6) {
                    textPassword.setError("Too small password");
                    return;
                }
                if(Policy.isChecked() == false){
                    Policy.setError("Is required ");
                    return;
                }

                createUser(Email,Password, Nickname);

            }
        });

        BackToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));//GO TO ANOTHER ACTIVITY
            }
        });

    }
    private void createUser(String Email, String Password, String Nickname) {
        fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(RegiterActivity.this, "User created", Toast.LENGTH_SHORT).show();
                    //progressBar.setVisibility(View.INVISIBLE);
                    userID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("users").document(Email);
                    Map<String,Object> user = new HashMap<>();//NEW MAP WITH INFORMATION ABOUT USER
                    user.put("nickname",Nickname);
                    user.put("email",Email);
                    user.put("password",Password);


                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {//PUSHING TO THE DB
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onSuccess: user profile is created for " + userID);
                        }
                    });

                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }else{
                    Toast.makeText(getApplicationContext(), "User does not created " +
                            task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}