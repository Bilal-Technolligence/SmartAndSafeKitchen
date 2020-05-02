package com.example.smartandsafekitchen;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SmokeSensorActivity extends AppCompatActivity {
    Switch switchButton;
    int smoke;
    int gas;
    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_smoke_sensor );

        switchButton=(Switch) findViewById(R.id.txtonoff);
        final ImageView animationTarget = (ImageView) this.findViewById(R.id.fanimage);
        switchButton.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(switchButton.isChecked()){
                    dref.child( "SmokeGas" ).child( "fan" ).setValue( 0 );
                    dref.child( "SmokeGas" ).child( "Smoke" ).setValue( 0 );
                    Animation animation = AnimationUtils.loadAnimation(SmokeSensorActivity.this, R.anim.fananim);
                    animationTarget.startAnimation(animation);



                }
                else
                {
                    dref.child( "SmokeGas" ).child( "fan" ).setValue( 0 );
                    Animation animation = AnimationUtils.loadAnimation(SmokeSensorActivity.this, R.anim.stopfananim);
                    animationTarget.startAnimation(animation);
                }
            }
        } );

   }
}
