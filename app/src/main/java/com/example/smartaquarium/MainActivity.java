package com.example.smartaquarium;

import android.app.Notification;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import static com.example.smartaquarium.App.PEMBERITAHUAN;


public class MainActivity extends AppCompatActivity {
    public static TextView status, action;
    FirebaseDatabase database;
    DatabaseReference myRef, myReff, myRefff;
    private NotificationManagerCompat notificationManagerCompat;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManagerCompat = NotificationManagerCompat.from(this);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("tag", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        Log.d("tokennya ni", token);
                    }
                });


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("isi");
        status = findViewById(R.id.status);
        action = findViewById(R.id.action);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myReff.setValue(1);
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                status.setText(dataSnapshot.getValue().toString());
                onPemberitahuan();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        myReff = database.getReference("makan");
        myReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                action.setText(dataSnapshot.getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void onPemberitahuan(){
        Notification notification = new NotificationCompat.Builder(this, PEMBERITAHUAN)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Smart Aquarium")
                .setContentText("Jarak: "+ status.getText().toString()+ " cm. Silahkan isi tempat makanannya!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        if (Integer.parseInt(status.getText().toString()) <= 7 ){
            notificationManagerCompat.cancelAll();
        }else {
            notificationManagerCompat.notify(1, notification);
        }
    }
}
