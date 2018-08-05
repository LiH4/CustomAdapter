package com.example.lisa.customadapter;

import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AdapterActivity extends AppCompatActivity {

    String name;

    private ListView listView;
    private FeedAdapter feedAdapter;

    //List<Feed> feedList;

    private Firebase firebase;

    int age, qm, pricing;


    //DatabaseReference database = FirebaseDatabase.getInstance().getReference("Feed");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);

        firebase.setAndroidContext(this);
        firebase = new Firebase("https://customadapter-61157.firebaseio.com/");

        listView = (ListView)findViewById(R.id.feed_list);

        fillAdapter();
        //feedList = new ArrayList<>();

        /*
        firebase.child("Feed").addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.d("AdapterActiviey", dataSnapshot.toString());
                name = dataSnapshot.child("name").getValue(String.class);
                fillAdapter();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/

        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {

                if(dataSnapshot.child("age").exists()) {
                    age = dataSnapshot.child("age").getValue(Integer.class);
                } else {
                    age = 0;
                }

                if(dataSnapshot.child("qm").exists()) {
                    qm = dataSnapshot.child("qm").getValue(Integer.class);
                } else {
                    qm = 0;
                }

                if(dataSnapshot.child("pricing").exists()) {
                    pricing = dataSnapshot.child("pricing").getValue(Integer.class);
                } else {
                    pricing = 0;
                }

                feedAdapter.add(new Feed(dataSnapshot.child("name").getValue(String.class), age, dataSnapshot.child("job").getValue(String.class), dataSnapshot.child("search").getValue(String.class), dataSnapshot.child("location").getValue(String.class), qm, pricing,dataSnapshot.child("others").getValue(String.class), dataSnapshot.child("picture1").getValue(String.class)));
            }

            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


/*    @Override
    protected void onStart() {
        super.onStart();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                //feedList.clear();

                for(DataSnapshot feedSnapshot : dataSnapshot.getChildren()) {
                        name = feedSnapshot.child("name").getValue(String.class);
              *//*      Feed feed = feedSnapshot.getValue(Feed.class);
                    feedList.add(feed);*//*

                }
//                feedAdapter = new FeedAdapter(AdapterActivity.this, (ArrayList<Feed>) feedList);
//                listView.setAdapter(feedAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }*/

    private void fillAdapter() {
        listView = (ListView)findViewById(R.id.feed_list);

        ArrayList<Feed> feedList = new ArrayList<>();


        //feedList.add(new Feed(name, "24", "Studium der Medieninformatik", "WG", "Regensburg", "45qm²", "350€ kalt", "Stellplatz"));
//        feedList.add(new Feed("Anna", "26", "Lehrerin", "WG", R.drawable.lisa, "Regensburg", "45qm²", "350€ kalt", "Stellplatz benötigt", R.drawable.pretzel, R.drawable.herz, R.drawable.images ));
//        feedList.add(new Feed("Lisa", "24", "Studium der Medieninformatik", "WG", R.drawable.lisa, "Regensburg", "45qm²", "350€ kalt", "Stellplatz benötigt", R.drawable.pretzel, R.drawable.herz, R.drawable.images ));
//        feedList.add(new Feed("Lisa", "24", "Studium der Medieninformatik", "WG", R.drawable.lisa, "Regensburg", "45qm²", "350€ kalt", "Stellplatz benötigt", R.drawable.pretzel, R.drawable.herz, R.drawable.images ));


        feedAdapter = new FeedAdapter(this, feedList);
        listView.setAdapter(feedAdapter);
    }
}
