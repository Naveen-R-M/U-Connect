package com.example.u_connect;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.IOException;

public class FourActivity extends AppCompatActivity {

    private ImageView img1;
    private MaterialEditText met1;
    private MaterialEditText met2;
    private MaterialEditText met3;
    private MaterialEditText met4;
    private Button b1;
    private Toolbar tb1;

    private FirebaseAuth auth;

    private FirebaseStorage storage;

    private StorageReference storageReference;

    String First_name,Last_name,age,mobile_no;
    private static int PICK_IMAGE = 123;
    Uri path;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){

            path = data.getData();
            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                img1.setImageBitmap(bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);

        img1 = findViewById(R.id.img1);
        met1 = findViewById(R.id.met1);
        met2 = findViewById(R.id.met2);
        met3 = findViewById(R.id.met3);
        met4 = findViewById(R.id.met4);
        b1   = findViewById(R.id.b1);

        tb1 = (Toolbar)findViewById(R.id.tb1);

        setSupportActionBar(tb1);
        getSupportActionBar().setTitle("Update Profile");

        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        storageReference = storage.getReference();

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Choose an image..!"),PICK_IMAGE);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_data();
            }
        });

    }

    public void add_data(){

        First_name = met1.getText().toString();
        Last_name  = met2.getText().toString();
        age        = met3.getText().toString();
        mobile_no  = met4.getText().toString();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference("users");


//        StorageReference reference = storageReference.child(auth.getUid()).child("Images").child("User Profile pics");
//        UploadTask uploadTask = reference.putFile(path);
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(FourActivity.this, "Upload Failed..!", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(FourActivity.this, "Upload Successful..!", Toast.LENGTH_SHORT).show();
//            }
//        });

        Profile p = new Profile(First_name,Last_name,age,mobile_no);
        ref.child("User_info").push().setValue(p);
        Toast.makeText(FourActivity.this, "Data saved Successfully..!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(FourActivity.this,Main2Activity.class));

    }
}
