package com.example.smartandsafekitchen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();
    //    String[] title={};
//    String[] description={};
//    String[] dateTime={};
//    String[] name={};
    ArrayList<NotificationAttributes> list=new ArrayList<>(  );
    //   NotificationAdapter notificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);



        recyclerView = (RecyclerView) findViewById(R.id.notification_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        dref.child("Notifications").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data: dataSnapshot.getChildren()){
                    NotificationAttributes noti=data.getValue(NotificationAttributes.class);
                    list.add(noti);
                }
                recyclerView.setAdapter(new NotificationAdapter(list));

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                String ingrediendNames=list.get(item.getGroupId()).getName();
                DatabaseReference drefDelete=FirebaseDatabase.getInstance().getReference("Notifications").child( ingrediendNames );
                drefDelete.removeValue();
                // dref.child("Notifications/"+ingrediendNames+"/tittle").setValue( "null" );
                finish();
                startActivity( getIntent() );
                return true;
        }
        return super.onContextItemSelected(item);
    }

}
