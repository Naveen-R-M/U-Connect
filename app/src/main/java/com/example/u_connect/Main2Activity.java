package com.example.u_connect;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Main2Activity extends AppCompatActivity {
    private FirebaseAuth auth;
    private ProgressDialog pd;

    private Toolbar tb1;
    private TabLayout tl1;
    private TabItem ti1,ti2;
    private ViewPager vp1;
    private PagerAdapter pg1;
    private TextView t1;

    private FloatingActionButton floatingActionButton;

    private BottomSheetBehavior behavior;
    LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        auth = FirebaseAuth.getInstance();

        tb1 = findViewById(R.id.tb1);
        tb1.setTitle("U - Connect");
        setSupportActionBar(tb1);

        tl1 = findViewById(R.id.tl1);
        ti1 = findViewById(R.id.ti1);
        ti2 = findViewById(R.id.ti2);
        vp1 = findViewById(R.id.vp1);

        layout = findViewById(R.id.layout);

        floatingActionButton = (FloatingActionButton)findViewById(R.id.float1);

        pg1 = new PageAdapter(getSupportFragmentManager(),tl1.getTabCount());
        vp1.setAdapter(pg1);
        pd = new ProgressDialog(this);

        View bottomSheet = findViewById(R.id.bottom);
        t1 = (TextView)findViewById(R.id.t1);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this,Main3Activity.class);
                startActivity(intent);
            }
        });

        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i){
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.i("BottomSheetCallback","BottomSheetBehavior.STATE_DRAGGING");
                        t1.setText("Exploring..");

                        break;

                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.i("BottomSheetCallback","BottomSheetBehavior.STATE_SETTLING");
                        break;

                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.i("BottomSheetCallback","BottomSheetBehavior.STATE_EXPANDED");
                        t1.setText("Here you go..!");
                        break;

                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Log.i("BottomSheetCallback","BottomSheetBehavior.STATE_COLLAPSED");
                        break;

                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

                Log.i("BottomSheetCallback","slideOffset: " + v);

            }
        });

        tl1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp1.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==1){
                    tb1.setBackgroundColor(ContextCompat.getColor(Main2Activity.this,android.R.color.holo_red_light));
                    tl1.setBackgroundColor(ContextCompat.getColor(Main2Activity.this,android.R.color.holo_red_light));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        getWindow().setStatusBarColor(ContextCompat.getColor(Main2Activity.this,android.R.color.holo_red_light));
                    }

                }else if(tab.getPosition()==2){

                    tb1.setBackgroundColor(ContextCompat.getColor(Main2Activity.this,android.R.color.holo_green_light));
                    tl1.setBackgroundColor(ContextCompat.getColor(Main2Activity.this,android.R.color.holo_green_light));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        getWindow().setStatusBarColor(ContextCompat.getColor(Main2Activity.this,android.R.color.holo_green_light));
                    }
                }else {
                    tb1.setBackgroundColor(ContextCompat.getColor(Main2Activity.this,android.R.color.holo_orange_light));
                    tl1.setBackgroundColor(ContextCompat.getColor(Main2Activity.this,android.R.color.holo_orange_light));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        getWindow().setStatusBarColor(ContextCompat.getColor(Main2Activity.this,android.R.color.holo_orange_light));
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        vp1.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tl1));

    }

    private void Logout(){
        auth.signOut();
        finish();
        Intent intent = new Intent(Main2Activity.this,MainActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.m1){
            pd.setMessage("Logging out..!");
            pd.show();
            Logout();
            pd.dismiss();
            finish();
        }
        else if(item.getItemId()==R.id.m2){
            startActivity(new Intent(Main2Activity.this,FourActivity.class));
            finish();
        }

//        switch (item.getItemId()){
//
//            case R.id.m1:{
//                Logout();
//                finish();
//            }
//
//            case R.id.m2:{
//                startActivity(new Intent(Main2Activity.this,FourActivity.class));
//                finish();
//            }
//
//
//
//        }
        return super.onOptionsItemSelected(item);
    }
}
