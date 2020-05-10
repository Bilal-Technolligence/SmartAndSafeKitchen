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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;

public class GasSensorActivity extends AppCompatActivity {
    int gas;
    TextView gasDetacted;
    private final String CHANNEL_ID ="personal" ;
    public final int NOTIFICATION_ID = 001;
//    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();



    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_gas_sensor );
        gasDetacted = findViewById(R.id.txtGas);

        dref.child( "SmokeGas" ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // smoke =Float.parseFloat(dataSnapshot.child( "Smoke" ).getValue().toString());
                gas =Integer.parseInt(dataSnapshot.child( "Gas" ).getValue().toString());
                if(gas==1)
                {
                    gasDetacted.setText("Gas Detected stay save don't burn stove without checking the leakage of gas");
                    generateAlert();
                    saveNotificationfirebase();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

    }


    private void generateAlert() {


        createNotificationChannel();

        Intent notificationIintent = new Intent(GasSensorActivity.this, GasSensorActivity.class);
        TaskStackBuilder taskStackBuilder= TaskStackBuilder.create(GasSensorActivity.this);
        taskStackBuilder.addNextIntent(notificationIintent);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(100,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        Uri alarmSound = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);
        builder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        builder.setContentTitle("Stay Alert");
        builder.setContentText("Gas Detected");
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setContentIntent( pendingIntent );

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

    public void saveNotificationfirebase(){
        String key = dref.child("gas").push().getKey();

        dref.child("Notifications").child(key).child("tittle").setValue( "Stay Safe" );
        dref.child("Notifications").child(key).child("description").setValue( "Gas Detected" );
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        dref.child("Notifications").child(key).child("datetime").setValue(currentDateTimeString);
        dref.child("Notifications").child(key).child("name").setValue("Gas Alert");


    }


  //  }
//
//        Intent notificationIintent = new Intent(GasSensorActivity.this, NotificationActivity.class);
//        TaskStackBuilder taskStackBuilder= TaskStackBuilder.create(GasSensorActivity.this);
//        taskStackBuilder.addNextIntent(notificationIintent);
//        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(100,PendingIntent.FLAG_UPDATE_CURRENT);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(GasSensorActivity.this);
//        Uri alarmSound = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION);
//        builder.setSound(alarmSound);
//
//        Notification notification =builder.setContentTitle("Gas")
//                .setAutoCancel(true)
//                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
//                .setTicker("Gas")
//                .setContentTitle("Stay Alert")
//                .setContentText("Gas Detected ")
//                .setAutoCancel(true)
//                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
//                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
//
//                .setContentIntent(pendingIntent).build();
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.notify(uniqueID, notification);
//    }

}
