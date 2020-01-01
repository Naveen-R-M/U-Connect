package com.example.u_connect;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.yasic.library.particletextview.Object.ParticleTextViewConfig;
import com.yasic.library.particletextview.View.ParticleTextView;

public class MainActivity extends AppCompatActivity {

    private Toolbar tb1;
    private MaterialEditText met1;
    private MaterialEditText met2;
    private MaterialEditText met3;
    private MaterialEditText met4;
    private Button b1;
    private TextView t1;
    private FirebaseAuth firebaseAuth;
    private connection cn;
    private ParticleTextView pet1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tb1 = (Toolbar)findViewById(R.id.tb1);
        met1 = (MaterialEditText) findViewById(R.id.met1);
        met2 = (MaterialEditText) findViewById(R.id.met2);
        met3 = (MaterialEditText) findViewById(R.id.met3);
        met4 = (MaterialEditText)findViewById(R.id.met4);
        b1 = (Button) findViewById(R.id.b1);
        t1 = (TextView)findViewById(R.id.t1);

        t1.animate().scaleX(1f).scaleY(1f).setDuration(1000).start();

        cn = new connection(this);
        setSupportActionBar(tb1);
        getSupportActionBar().setTitle("U-Connect");
        pet1 = (ParticleTextView)findViewById(R.id.pet1);

        pet1 = (ParticleTextView)findViewById(R.id.pet1);
        ParticleTextViewConfig con = new ParticleTextViewConfig.Builder()
                .setTargetText("REGISTRATION")
                .setReleasing(0.4)
                .setParticleRadius(3)
                .setMiniDistance(1)
                .setTextSize(128)
                .setRowStep(9)
                .setColumnStep(9)
                .instance();
        pet1.setConfig(con);
        pet1.startAnimation();


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u_name = met1.getText().toString().trim();
                String pass = met2.getText().toString().trim();
                String e_mail = met3.getText().toString().trim();
                String ph_no = met4.getText().toString().trim();

                if ((u_name.isEmpty() || (pass.isEmpty() || (e_mail.isEmpty() || (ph_no.isEmpty()))))) {
                    Toast.makeText(MainActivity.this, "All the fields are mandatory", Toast.LENGTH_SHORT).show();
                }
                /*else if (firebaseUser != null) {
                    finish();
                    String action;
                    Toast.makeText(MainActivity.this, "User already registered..! Try logging in..!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, TwoActivity.class);
                    startActivity(intent);

                } */
                else {

                    firebaseAuth.createUserWithEmailAndPassword(e_mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            } else {
                                if (!(cn.isConnected())) {
                                    Toast.makeText(MainActivity.this, "Registration failed..!Check your internet connection", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }

                    });
                }
            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TwoActivity.class));
            }
        });


    }
}
