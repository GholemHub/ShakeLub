    package com.example.shakelab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.shakelab.NoteIngredientAdapter.mNoteIngredientsList;
import static com.example.shakelab.NoteIngredientAdapter.nivhList;

public class Create extends AppCompatActivity implements PercentDialog.PercentDialogListener {
    private static final int PICK_IMAGE_REQUEST = 1;
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
    private Uri mImageUri;
    private ImageView new_shake_image;

    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    private String URL;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        createNavBar();///NAVIGATION BAR
        createNumberPicker();/// NUMBER PICKER

        uploadImage();
        btnCreate();
    }


    private void uploadImage() {
        new_shake_image = findViewById(R.id.new_shake_image);
        new_shake_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
    }

    //FUNCTION TO AUTO INDICATE THE FILE EXTENTION
    private String getFileExtention(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadFile(){

        //WAY TO CORRECT COLLECTION IN DATABASE
        mStorageRef = FirebaseStorage.getInstance().getReference("shakeImage");


        if(new_shake_image != null){//MAKING FILE NAME
            StorageReference fileRefrence = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtention(mImageUri));

            mUploadTask = fileRefrence.putFile(mImageUri)//PUTTING FILE TO DB
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            fileRefrence.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    URL = uri.toString();
                                }
                            });
                            //MAKING LINK TO THE FIRESTORE
                            String downloadUri= taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                            URL = downloadUri;
                        }
                    });

        }
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
        && data != null && data.getData() != null){
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(new_shake_image);//SHOWING THE CHOSE IMAGE
            uploadFile();
        }
    }

    private void btnCreate() {
            fStore = FirebaseFirestore.getInstance();
            createBnt = (FloatingActionButton)findViewById(R.id.create_new_shake_btn);
            new_shake_name = findViewById(R.id.new_shake_name);

            createBnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(numberPicker.getValue() == 0){
                        //Toast.makeText(Create.this, "No engredients", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(new_shake_name.getText().toString().isEmpty()){
                        new_shake_name.setError("Name is required");
                        //Toast.makeText(Create.this, "No name", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    DocumentReference documentReference = fStore.collection("shakes")
                            .document(new_shake_name.getText().toString());

                    Map<String,Object> newShake = new HashMap<>();

                    newShake.put("shakeName", new_shake_name.getText().toString());
                    newShake.put("shakeImage", URL);
                    newShake.put("countOfLayers", numberPicker.getValue());

                    if(mNoteIngredientList != null && mNoteIngredientList.isEmpty()){

                        mAdapter.error();
                        return;
                    }

                    mAdapter.saveNames();

                    String ingredientsStr = "";
                    String ingredientsStr2 = "";
                    String ingredientsStr3 = "";
                    String listPercentOfIngredients = "";

                    int i = 0;
                    for (NoteIngredient list : mNoteIngredientList) {


                        /// PUTTING A NEW INGREDIENT WITH CURRENT NUMBER
                        int NumIngredient = Integer.parseInt(list.getCountOfIngredient());
                        //mAdapter.saveNames2(NumIngredient);
                        //Log.d("NumIngredient","NumIngredient " + NumIngredient );
                        newShake.put("ingredient" + list.getCountOfIngredient(),
                                mAdapter.getIngredientInfo3(NumIngredient));

                        ///CHECKING IF PERCENT IS EXIST
                        if(list.getPercentOfIngredient() != null && !list.getPercentOfIngredient().isEmpty()){
                            newShake.put("percent_of_ingredient" + list.getCountOfIngredient(), list.getPercentOfIngredient());
                            listPercentOfIngredients += list.getPercentOfIngredient() + "/";
                            //Toast.makeText(Create.this, "Required", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            //Toast.makeText(Create.this, "Required2", Toast.LENGTH_SHORT).show();
                            mAdapter.PercentError();
                            return;
                        }


                        /// CREATING A VARIABLE WITH ALL INGREDIENTS
                        ingredientsStr += mAdapter.getIngredientInfo3(NumIngredient) + " ";
                        ingredientsStr2 += (i+1) + ") "+ mAdapter.getIngredientInfo3(NumIngredient )+ "\n";
                        ingredientsStr3 += mAdapter.getIngredientInfo3(NumIngredient ) + "/";



                        i++;
                    }

                    //CATCHING ERRORS
                    if(ingredientsStr != null && !ingredientsStr.isEmpty()){
                        newShake.put("shakeIngredientsString", ingredientsStr);
                    }else{
                        newShake.put("shakeIngredientsString", "");
                    }

                    if(ingredientsStr2 != null && !ingredientsStr2.isEmpty()){
                        newShake.put("shakeIngredientsString2", ingredientsStr2);
                    }else{
                        newShake.put("shakeIngredientsString2", "");
                    }

                    if(ingredientsStr3 != null && !ingredientsStr3.isEmpty()){
                        newShake.put("shakeIngredientsString3", ingredientsStr3);
                    }else{
                        newShake.put("shakeIngredientsString3", "");
                    }


                    if(listPercentOfIngredients != null && !listPercentOfIngredients.isEmpty())
                        newShake.put("listPercentOfIngredients",listPercentOfIngredients);



                    nivhList.clear();
                    nivhList = new ArrayList<NoteIngredientAdapter.NoteIngredientsViewHolder>();

                    //POOSHING TO DB OUR MAP WITH INFORMATION
                    documentReference.set(newShake).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                           Log.d("PUSh","Done");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Toast.makeText(Create.this, "None", Toast.LENGTH_SHORT).show();
                        }
                    });
                    startActivity(new Intent(getApplicationContext(), Search.class));
                }
            });

    }

    private void createNumberPicker() {

        numberPicker = findViewById(R.id.countOfIngredients_picker);
        numberPicker.setMaxValue(5);
        numberPicker.setMinValue(0);
        numberPicker.setValue(0);

        text_view_numberOfingredient = findViewById(R.id.text_view_numberOfingredient);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                IngredientsCount = newVal;
                buildRecyclerView(newVal);

            }
        });
    }
public static int IngredientsCount;
public static int NewValue = 0;
    public void buildRecyclerView(int newVal){

        mNoteIngredientList = new ArrayList<>();
        NewValue = 0;
        NewValue = newVal;
        for(int i = 0; i < newVal; i++){
            int p = i+1;
            mNoteIngredientList.add(new NoteIngredient("", "" + p));
        }

        mRecyclerView = findViewById(R.id.create_recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new NoteIngredientAdapter(mNoteIngredientList, this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
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
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        break;
                    case R.id.nav_search:
                        startActivity(new Intent(getApplicationContext(), Search.class));
                        break;
                    case R.id.nav_create:
                        startActivity(new Intent(getApplicationContext(), Create.class));
                        //Toast.makeText(Create.this, "Create", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void applyTexts(int iPercent, String num) {


            //Toast.makeText(Create.this, "Quantity: " + iPercent, Toast.LENGTH_SHORT).show();

        }
    }
