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
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Create extends AppCompatActivity {


    private ActionBarDrawerToggle toggle;
    private NumberPicker numberPicker;
    private Button button;
    private TextView text_view_numberOfingredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

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

        //setUpRecycleView();

        /// NUMBER PICKER
        numberPicker = findViewById(R.id.countOfIngredients_picker);
        numberPicker.setMaxValue(25);
        numberPicker.setMinValue(1);
        numberPicker.setValue(1);


        button = findViewById(R.id.button);
        //text_view_numberOfingredient = findViewById(R.id.text_view_numberOfingredient);


        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                NoteIngredientAdapter adapter = new NoteIngredientAdapter(getApplicationContext(), R.layout.note_create_item);
                ListView listView = findViewById(R.id.create_recycler_view);

                listView.setAdapter(adapter);

                NoteIngredient nti = new NoteIngredient("213");
                //Toast.makeText(Create.this, "Hello", Toast.LENGTH_SHORT).show();
                for(int i = 0; i < newVal; i++){
          //          text_view_numberOfingredient.setText(i+1);
                    adapter.add(nti);

                }
                /*
                adapter.add(nti);
                adapter.add(nti);
*/

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Create.this, "Hello", Toast.LENGTH_SHORT).show();
            }
        });


    }
}