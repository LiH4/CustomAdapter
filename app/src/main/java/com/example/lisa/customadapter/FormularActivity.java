package com.example.lisa.customadapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.firebase.client.Firebase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormularActivity extends AppCompatActivity {

    private Button send, select, take;
    private EditText name, age, job, search, location, qm, pricing, others;
    private ImageView picture;

    private static final int REQUEST_IMAGE_CHOSEN = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 0;

    private String mCurrentPhotoPath;


    Firebase myFirebase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_formular);

        Firebase.setAndroidContext(this);

        myFirebase = new Firebase("https://customadapter-61157.firebaseio.com");

        initViews();
        selectImage();
        takeImage();
        saveFeed();

        Intent intent = getIntent();
        if (intent != null) {
            // TODO Check intent type, set variables and load image to mCropImageView

            if(intent.getType() != null && intent.getType().contains("image/")) {
                if(intent.getData() != null) {
                    Uri data = intent.getData();
                    mCurrentPhotoPath = data.toString();
                    picture = findViewById(R.id.picture1);
                    picture.setImageURI(data);
                }
            }

        }


    }

    private void saveFeed() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameString = name.getText().toString();
                String ageString = age.getText().toString();
                String jobString = job.getText().toString();
                String searchString = search.getText().toString();
                String locationString = location.getText().toString();
                String qmString = qm.getText().toString();
                String pricingString = pricing.getText().toString();
                String othersString = others.getText().toString();

                DatabaseFeed feed = new DatabaseFeed();

                feed.setName(nameString);
                feed.setAge(ageString);
                feed.setJob(jobString);
                feed.setSearch(searchString);
                feed.setLocation(locationString);
                feed.setQm(qmString);
                feed.setPricing(pricingString);
                feed.setOthers(othersString);
                feed.setPicture1(mCurrentPhotoPath);

                myFirebase.child("Feed").setValue(feed);

            }
        });
    }

    //Bild von Handy aussuchen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CHOSEN && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                picture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }
    }

    private void selectImage() {
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_CHOSEN);

            }
        });
    }

    public void takeImage() {
        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startCamera = new Intent();
                startCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                if (startCamera.resolveActivity(getPackageManager()) != null) {
                    try {
                        File photo = createImageFile();
                        startActivityForResult(startCamera, REQUEST_IMAGE_CAPTURE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists()) {
            Log.i(MainActivity.class.getName(),
                    "Directory creation was needed and was successfull: " + storageDir.mkdirs());
        }
        File imageFile = new File(storageDir, imageFileName);
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    private void initViews() {
        send = (Button) findViewById(R.id.sendData);
        select = (Button) findViewById(R.id.selectImage);
        take = (Button) findViewById(R.id.takePicture);

        name = (EditText) findViewById(R.id.nameInput);
        age = (EditText) findViewById(R.id.ageInput);
        job = (EditText) findViewById(R.id.jobInput);
        search = (EditText) findViewById(R.id.searchInput);
        location = (EditText) findViewById(R.id.locationInput);
        qm = (EditText) findViewById(R.id.qmInput);
        pricing = (EditText) findViewById(R.id.pricingInput);
        others = (EditText) findViewById(R.id.othersInput);

        picture = (ImageView) findViewById(R.id.picture1);


    }
}
