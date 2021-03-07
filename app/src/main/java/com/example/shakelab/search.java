package com.example.shakelab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class search extends AppCompatActivity {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("shakes");

    private SearchView search_view;
    private FloatingActionButton go_to_create_Layout;
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setUpRecyclerView();

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.bringToFront();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_home:
                        Toast.makeText(search.this, "Search", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.nav_profile:

                        break;
                    case R.id.nav_search:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        break;
                    case R.id.nav_create:
                        startActivity(new Intent(getApplicationContext(), Create.class));
                        break;
                }

                return false;
            }
        });


        search_view = findViewById(R.id.search_view);
        go_to_create_Layout = findViewById(R.id.go_to_create_Layout);

        go_to_create_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Create.class));
            }
        });
       search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Query query;

                if(newText.isEmpty()){
                    query = notebookRef
                            .orderBy("countOfLayers", Query.Direction.DESCENDING);

                    FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                            .setQuery(query, Note.class)
                            .build();
                    adapter.updateOptions(options);
                }else{

                    query = notebookRef

                            .whereEqualTo("shakeName", newText)
                            .orderBy("countOfLayers", Query.Direction.DESCENDING);

                    FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                            .setQuery(query, Note.class)
                            .build();

                    adapter.updateOptions(options);

                }

                return false;
            }
        });

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigarion_drawer_open,R.string.navigarion_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    private DatabaseReference usersRef;

    public List<Note> ls = new ArrayList<>();



    private void setUpRecyclerView() {

        Query query = notebookRef.orderBy("countOfLayers", Query.Direction.DESCENDING);

        usersRef = FirebaseDatabase.getInstance().getReference().child("Notebook");

        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query, Note.class)
                .build();

        adapter = new NoteAdapter(options, ls);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                Note note = documentSnapshot.toObject(Note.class);
                String id = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();

                Toast.makeText(search.this, "Position " + note.getCountOfLayers(), Toast.LENGTH_SHORT).show();
                openDialog(note);
            }
        });
    }

    private void openDialog(Note note) {

        InfoShakeDialog infoShakeDialog = new InfoShakeDialog(note);
        infoShakeDialog.show(getSupportFragmentManager(), "exemple dialog");
    }

    @Override
    protected void onStart(){
        super.onStart();

        adapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();

        adapter.stopListening();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}