package com.example.shakelab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.sql.DataTruncation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Create extends AppCompatActivity {
    private ArrayList<NoteIngredient> mNoteIngredientList;

    private ActionBarDrawerToggle toggle;
    private TextView text_view_numberOfingredient;

    private RecyclerView mRecyclerView;
    private NoteIngredientAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private NumberPicker numberPicker;

    private FloatingActionButton createBnt;
    private TextView new_shake_name;

    private FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        createNavBar();///NAVIGATION BAR
        createNumberPicker();/// NUMBER PICKER
        btnCreate();

    }

    private void btnCreate() {

        fStore = FirebaseFirestore.getInstance();

        createBnt = (FloatingActionButton)findViewById(R.id.create_new_shake_btn);
        new_shake_name = findViewById(R.id.new_shake_name);



        createBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentReference = fStore.collection("shakes").document(new_shake_name.toString());


                Map<String,Object> newShake = new HashMap<>();



                newShake.put("ShakeName","1");

                documentReference.set(newShake).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Create.this, "Done", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Create.this, "None", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void createNumberPicker() {

        numberPicker = findViewById(R.id.countOfIngredients_picker);
        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(0);
        numberPicker.setValue(0);

        text_view_numberOfingredient = findViewById(R.id.text_view_numberOfingredient);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                buildRecyclerView(newVal);

            }
        });
    }


    public void buildRecyclerView(int newVal){

        mNoteIngredientList = new ArrayList<>();

        for(int i = 0; i < newVal; i++){
            int p = i+1;
            mNoteIngredientList.add(new NoteIngredient("", "" + p));
        }

        mRecyclerView = findViewById(R.id.create_recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new NoteIngredientAdapter(mNoteIngredientList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new NoteIngredientAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeItem(position, "Boom");
            }
        });

    }

    public void changeItem(int position, String text){
        mNoteIngredientList.get(position).changeTex1(text);
        mAdapter.notifyItemChanged(position);
    }

    private void createNavBar() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigarion_drawer_open,R.string.navigarion_drawer_close);

        toggle.syncState();
        drawer.addDrawerListener(toggle);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.bringToFront();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        break;
                    case R.id.nav_profile:

                        break;
                    case R.id.nav_search:
                        startActivity(new Intent(getApplicationContext(), Search.class));
                        break;
                    case R.id.nav_create:
                        Toast.makeText(Create.this, "Create", Toast.LENGTH_SHORT).show();
                        break;
                }

                return false;
            }
        });
    }

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("shakes");
    private DatabaseReference usersRef;

}