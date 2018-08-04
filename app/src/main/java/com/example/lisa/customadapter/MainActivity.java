package com.example.lisa.customadapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button seeFeeds, fillFormular;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seeFeeds = findViewById(R.id.buttonSeeFeed);
        fillFormular = (Button)findViewById(R.id.buttonFillFormular);

        seeFeeds.setOnClickListener(this);
        fillFormular.setOnClickListener(this);





    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSeeFeed:
                Intent intent = new Intent(this, AdapterActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonFillFormular:
                Intent formularIntent = new Intent(this, FormularActivity.class);
                startActivity(formularIntent);
                //open formular
                break;
        }
    }


}
