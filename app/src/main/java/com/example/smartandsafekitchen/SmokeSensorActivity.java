package com.example.smartandsafekitchen;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;

public class SmokeSensorActivity extends AppCompatActivity {
    Switch switchButton;
    int smoke;
    int gas;

//    Switch switchButton;
//    int smoke;
//    int gas;
private final String CHANNEL_ID ="personal" ;
    public final int NOTIFICATION_ID = 001;//
//    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();



    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_smoke_sensor );

        switchButton=(Switch) findViewById(R.id.txtonoff);
        final ImageView animationTarget = (ImageView) this.findViewById(R.id.fanimage);

        dref.child( "SmokeGas" ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // smoke =Float.parseFloat(dataSnapshot.child( "Smoke" ).getValue().toString());
                smoke =Integer.parseInt(dataSnapshot.child( "Smoke" ).getValue().toString());

                if(smoke==1)
                {

                    generateAlert();
                    saveNotificationfirebase();
                    switchButton.setChecked(true);
                    Animation animation = AnimationUtils.loadAnimation(SmokeSensorActivity.this, R.anim.fananim);
                    animationTarget.startAnimation(animation);

                }
                else if(smoke==0)
                {
                    // dref.child( "SmokeGas" ).child( "fan" ).setValue( 0 );
                    switchButton.setChecked(false);
                    Animation animation = AnimationUtils.loadAnimation(SmokeSensorActivity.this, R.anim.stopfananim);
                    animationTarget.startAnimation(animation);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );



        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchButton.isChecked()){
                    dref.child( "SmokeGas" ).child( "fan" ).setValue( 0 );
//                    dref.child( "SmokeGas" ).child( "Smoke" ).setValue( 0 );
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
        });







    }

    private void generateAlert() {


        createNotificationChannel();

        Intent notificationIintent = new Intent(SmokeSensorActivity.this, SmokeSensorActivity.class);
        TaskStackBuilder taskStackBuilder= TaskStackBuilder.create(SmokeSensorActivity.this);
        taskStackBuilder.addNextIntent(notificationIintent);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(100,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);

        builder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        builder.setContentTitle("Fan On");
        builder.setContentText("Smoke Detected");
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setContentIntent( pendingIntent );
        Uri alarmSound = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);

        NotificationManager notificationManager = (NotificationManager)getSystemService( Context.NOTIFICATION_SERVICE );
        notificationManager.notify(NOTIFICATION_ID,builder.build() );

    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            CharSequence name = "personal";
            String Description = "Stay Alert";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel( CHANNEL_ID,name,importance );
            notificationChannel.setDescription( Description );

            NotificationManager notificationManager = (NotificationManager)getSystemService( NOTIFICATION_SERVICE );
            notificationManager.createNotificationChannel( notificationChannel );
        }
    }

    //Save notification on firebase

    public void saveNotificationfirebase(){

        //get the push key value
        String key = dref.child("smoke").push().getKey();

        dref.child("Notifications").child(key).child("tittle").setValue( "Fan On" );
        dref.child("Notifications").child(key).child("description").setValue( "Smoke Detected" );
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        dref.child("Notifications").child(key).child("datetime").setValue(currentDateTimeString);
        dref.child("Notifications").child(key).child("name").setValue("Smoke Alert");


    }


    public void onClick(View v) {
        ImageView animationTarget = (ImageView) this.findViewById(R.id.fanimage);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fananim);
        animationTarget.startAnimation(animation);

    }

}
