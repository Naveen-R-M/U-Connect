package com.example.u_connect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class ThreeActivity extends AppCompatActivity {

    private MaterialEditText met1;
    private Button b1;
    private FirebaseAuth auth;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        met1 = (MaterialEditText)findViewById(R.id.met1);
        b1   = (Button)findViewById(R.id.b1);

        auth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e_mail = met1.getText().toString().trim();
                if (e_mail.equals("")) {
                    Toast.makeText(ThreeActivity.this, "Please enter an e-mail..!", Toast.LENGTH_SHORT).show();
                } else {
                    auth.sendPasswordResetEmail(e_mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                pd.setMessage("Wait until we catch-up");
                                pd.show();
                                Toast.makeText(ThreeActivity.this, "Password reset link has been sent to your e-mail..!", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                                startActivity(new Intent(ThreeActivity.this, TwoActivity.class));
                                finish();
                            }
                            else {
                                Toast.makeText(ThreeActivity.this, "Only registered mail can be resetted.Please try again..!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
