package com.example.u_connect;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.jar.Attributes;

public class ChatActivity extends AppCompatActivity {

    private TextView t1;
    private String Chat;
//    private ScrollView scr1;
    private MaterialEditText met1;
    private ImageButton img1;
    private Toolbar tb1;
    private String user_id,user_name , date , time;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference ref;
    MessageAdapter adapter;
    List<Displaymessage> msg;
    RecyclerView recycler_view;
    private ScrollView scrollView;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        t1 = findViewById(R.id.t1);
        met1 = (MaterialEditText)findViewById(R.id.met1);
        img1 = (ImageButton)findViewById(R.id.img1);
        scrollView = (ScrollView)findViewById(R.id.scrollView);

//        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
//        Context context;
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        layoutManager.setStackFromEnd(true);
//        recycler_view.setLayoutManager(layoutManager);


        Chat = getIntent().getExtras().get("Info").toString();
       // Toast.makeText(this,Chat, Toast.LENGTH_SHORT).show();

        auth = FirebaseAuth.getInstance();

        tb1 = (Toolbar)findViewById(R.id.tb1);
        setSupportActionBar(tb1);
        getSupportActionBar().setTitle(Chat);

        user = FirebaseAuth.getInstance().getCurrentUser();

        ref = FirebaseDatabase.getInstance().getReference("Users").child(Chat);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = met1.getText().toString();
                if(!TextUtils.isEmpty(message)){
                    message(user.getUid(),Chat,message);
                }else{
                    Toast.makeText(ChatActivity.this, "Type in something", Toast.LENGTH_SHORT).show();
                }
                met1.setText("");
                t1.append("\n" + message + "\n");
            }
        });



    }

    @Override
    protected void onStart() {

        super.onStart();

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                display(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                display(dataSnapshot);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void message(String sender , String reciever , String message){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        HashMap<String , Object> hashmap = new HashMap<>();

        hashmap.put("sender",sender);
        hashmap.put("receiver",reciever);
        hashmap.put("message",message);

        ref.child("chats").push().setValue(hashmap);


    }
    private void display(DataSnapshot dataSnapshot) {

        Iterator iterator = dataSnapshot.getChildren().iterator();

        while (iterator.hasNext()){
            String msg = (String)((DataSnapshot)iterator.next()).getValue();
            String receiver = (String)((DataSnapshot)iterator.next()).getValue();
            String sender = (String)((DataSnapshot)iterator.next()).getValue();

            t1.append(msg + "\n");
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);

        }

        //read(user.getUid(),user_id);

    }

//    private void read(final String my_id , final String user_id){
//        msg = new ArrayList<>();
//
//        ref = FirebaseDatabase.getInstance().getReference("Chats");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                msg.clear();
//                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
//                    Displaymessage dm = dataSnapshot1.getValue(Displaymessage.class);
//                    if(dm.getReceiver().equals(my_id) && dm.getSender().equals(user_id)){
//                        msg.add(dm);
//                    }
//                    adapter = new MessageAdapter(ChatActivity.this,msg);
//                    recycler_view.setAdapter(adapter);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }

}
