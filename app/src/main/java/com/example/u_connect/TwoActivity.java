package com.example.u_connect;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class TwoActivity extends AppCompatActivity {

    private Toolbar tb1;
    private MaterialEditText met1;
    private MaterialEditText met2;
    private Button b1;
    private FirebaseAuth auth;
    private ProgressDialog pd;
    private TextView t1;
    private ParticleTextView pet1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        tb1 = (Toolbar)findViewById(R.id.tb1);
        met1 = (MaterialEditText)findViewById(R.id.met1);
        met2 = (MaterialEditText)findViewById(R.id.met2);
        b1   = (Button)findViewById(R.id.b1);
        t1   = (TextView)findViewById(R.id.t1);

        t1.animate().scaleX(1f).scaleY(1f).setDuration(1000).start();

        setSupportActionBar(tb1);
        getSupportActionBar().setTitle("U-Connect");

        pet1 = (ParticleTextView)findViewById(R.id.pet1);
        ParticleTextViewConfig con = new ParticleTextViewConfig.Builder()
                .setTargetText("LOGIN")
                .setReleasing(0.4)
                .setParticleRadius(3)
                .setMiniDistance(1)
                .setTextSize(150)
                .setRowStep(9)
                .setColumnStep(9)
                .instance();
        pet1.setConfig(con);
        pet1.startAnimation();

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        Context context;
        pd = new ProgressDialog(this);


        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e_mail = met1.getText().toString();
                String password = met2.getText().toString();
                if(e_mail.isEmpty()||password.isEmpty()){
                    Toast.makeText(TwoActivity.this, "All fields are mandatory..!", Toast.LENGTH_SHORT).show();
                }
                else {
                    pd.setMessage("Wait until we catch-up..!");
                    pd.show();
                    auth.signInWithEmailAndPassword(e_mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                pd.dismiss();
                                check();

                            /*Toast.makeText(TwoActivity.this, "Login Successfull..!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TwoActivity.this , Main2Activity.class);
                            startActivity(intent);*/

                            }
                        }
                    });

                }


            }
        });

    }

    private void check(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Boolean flag = firebaseUser.isEmailVerified();

        if(flag){
            finish();
            Intent intent = new Intent(TwoActivity.this,Main2Activity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Verify your e-mail before loggin in ..!", Toast.LENGTH_SHORT).show();
            verify();
            auth.signOut();
        }
    }

    private void verify(){
        FirebaseUser user = auth.getCurrentUser();
        if(user!=null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(TwoActivity.this, "Verification mail has been sent to your mail..!", Toast.LENGTH_SHORT).show();
                        auth.signOut();
                        finish();
                        startActivity(new Intent(TwoActivity.this,TwoActivity.class));
                    }
                }
            });
        }
    }

}