package com.example.lisa.customadapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormularActivity extends AppCompatActivity {

    private Button send, select, take;
    private EditText name, age, job, search, location, qm, pricing, others;
    private ImageView picture;

    private String CurrentPhotoPath;
    private static final int REQUEST_IMAGE_CHOSEN = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 0;

    Firebase myFirebase;
    FirebaseStorage storage;
    StorageReference storageRef;

    private Uri imageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_formular);

        initViews();

        Firebase.setAndroidContext(this);
        myFirebase = new Firebase("https://customadapter-61157.firebaseio.com");

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(this, "Your device doesn't have a camera!", Toast.LENGTH_LONG).show();
            send.setVisibility(View.GONE);
        }

        //Fotos aussuchen und schießen teilweise aus Übungsaufgabe ImageCropper übernommen!
        Intent intent = getIntent();
        if (intent != null) {
            if(intent.getType() != null && intent.getType().contains("image/")) {
                if(intent.getData() != null) {
                    Uri data = intent.getData();
                    CurrentPhotoPath = data.toString();
                    picture = findViewById(R.id.picture1);
                    picture.setImageURI(data);
                }
            }
        }

        selectImage();
        takeImage();
        saveFeed();

    }

    private void saveFeed() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFoto();
                String nameString = name.getText().toString();
                int ageString = Integer.parseInt(age.getText().toString());
                String jobString = job.getText().toString();
                String searchString = search.getText().toString();
                String locationString = location.getText().toString();
                int qmString = Integer.parseInt(qm.getText().toString());
                int pricingString = Integer.parseInt(pricing.getText().toString());
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
                //feed.setPicture1(CurrentPhotoPath);

                myFirebase.child("Feed").setValue(feed);
            }
        });
    }

    private void uploadFoto() {
       if(CurrentPhotoPath != null) {
           final ProgressDialog progressDialog = new ProgressDialog(this);
           progressDialog.setTitle("Uploading..,");
           progressDialog.show();

           StorageReference ref = storageRef.child("images");
           ref.putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   progressDialog.dismiss();
                   Toast.makeText(FormularActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   progressDialog.dismiss();
                   Toast.makeText(FormularActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
               }
           }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                   double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                   progressDialog.setMessage("Uploaded "+(int)progress+"%");
               }
           });
       }

    }

    //Foto von Handy aussuchen
    private void selectImage() {
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openImageGallery = new Intent(Intent.ACTION_GET_CONTENT);
                openImageGallery.setType("image/*");
                startActivityForResult(openImageGallery, REQUEST_IMAGE_CHOSEN);
            }
        });
    }

    //Foto schießen
    private void takeImage() {
        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (startCamera.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                        //startActivityForResult(startCamera, REQUEST_IMAGE_CAPTURE);
                    } catch (IOException e) {
                        Log.d("Main","takeImage couldn't create file ");
                        e.printStackTrace();
                    }
                    if(photoFile != null) {

                    } else {
                        Toast.makeText(FormularActivity.this, "File creation failed, only thumnail is shown.", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("FormularActivity", "takeImage: startactivity for result");
                    startActivityForResult(startCamera, REQUEST_IMAGE_CAPTURE);
                } else {
                    Toast.makeText(FormularActivity.this, "No camera app present.", Toast.LENGTH_SHORT).show();

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
            Log.i(FormularActivity.class.getName(),
                    "Directory creation was needed and was successfull: " + storageDir.mkdirs());
        }
        File imageFile = new File(storageDir, imageFileName);
        // Save a file: path for use with ACTION_VIEW intents
        CurrentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    //Klappt nicht?!
    private void galleryAddPic() {
        if (CurrentPhotoPath == null) {
            return;
        }
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(CurrentPhotoPath);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        imageData = data.getData();

        picture = (ImageView) findViewById(R.id.picture1);
        Bitmap image;
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            galleryAddPic();
            if (data == null) {
                image = BitmapFactory.decodeFile(CurrentPhotoPath, null);
                picture.setImageBitmap(image);
            } else {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    image = (Bitmap) extras.get("data");
                    picture.setImageBitmap(image);
                } else {
                    Log.d(FormularActivity.class.getName(), "onActivityResult extras is null");

                }

            }
        }

            else if (requestCode == REQUEST_IMAGE_CHOSEN && resultCode == RESULT_OK) {
                if(data != null) {

                 try {
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    image = BitmapFactory.decodeStream(inputStream);
                    picture.setImageBitmap(image);
                    CurrentPhotoPath = data.getData().toString();
                     Toast.makeText(this, CurrentPhotoPath + " loaded from gallery", Toast.LENGTH_SHORT)
                             .show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                    Toast.makeText(this, "Error requesting image from gallery.", Toast.LENGTH_SHORT).show();
                }
        }
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
    }
}
