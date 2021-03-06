package com.example.doormatt.admin.admin_ui.resident;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.doormatt.R;
import com.example.doormatt.common.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminResidentDetailedActivity extends AppCompatActivity {
    final String TAG = AdminResidentDetailedActivity.class.getSimpleName();

    private TextView residentIdTextView, residentFullNameTextView, dateOfBirthTextView, roomNumberTextView, contactNumberTextView;
    private CircleImageView residentAvatarCircleImageView;
    private ImageView qrCode;
    private ConstraintLayout idTemplateLayout;

    private Button saveTemplateButton;

    private String residentId;
    private DatabaseReference residentRef;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_detailed);
        residentRef = FirebaseDatabase.getInstance().getReference(Common.RESIDENT_REF);

        residentId = getIntent().getStringExtra("residentId");

        residentFullNameTextView = findViewById(R.id.detailedFullNameTextView);
        residentIdTextView = findViewById(R.id.detailedResidentIdTextView);
        roomNumberTextView = findViewById(R.id.detailedRoomNumberTextView);
        dateOfBirthTextView = findViewById(R.id.detailedDateOfBirthTextView);
        contactNumberTextView = findViewById(R.id.detailedPhoneNumberTextView);

        residentAvatarCircleImageView = findViewById(R.id.detailedResidentPictureCircleImageView);
        saveTemplateButton = findViewById(R.id.createIdTemplateButton);
        qrCode = findViewById(R.id.detailedQrCodeImageView);

        residentRef.child(residentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                try {
                    final String firstName = snapshot.child("firstName").getValue().toString();
                    final String middleName = snapshot.child("middleName").getValue().toString();
                    final String lastName = snapshot.child("lastName").getValue().toString();
                    final String dateOfBirth = snapshot.child("dateOfBirth").getValue().toString();
                    final String roomNumber = snapshot.child("roomNumber").getValue().toString();
                    final String residentAvatar = snapshot.child("residentAvatar").getValue().toString();
                    final String contactNumber = snapshot.child("contactNumber").getValue().toString();
                    final String myResidentId = snapshot.child("residentId").getValue().toString();

                    residentFullNameTextView.setText(String.format("%s %s %s", firstName, middleName, lastName));
                    dateOfBirthTextView.setText(dateOfBirth);
                    roomNumberTextView.setText(roomNumber);
                    contactNumberTextView.setText(contactNumber);
                    residentIdTextView.setText(myResidentId);
                    Picasso.get().load(residentAvatar).into(residentAvatarCircleImageView);

                    StorageReference mImageStorage = FirebaseStorage.getInstance().getReference();
                    StorageReference ref = mImageStorage.child(Common.QR_IMAGES)
                            .child(residentId);

                    ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downUri = task.getResult();
                                String imageUrl = downUri.toString();
                                Picasso.get().load(imageUrl).into(qrCode);
                            } else {
                                finish();
                            }
                        }
                    });

                } catch (NullPointerException e) {
                    Log.d(TAG, "onDataChange: " + e.getMessage());
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        idTemplateLayout = findViewById(R.id.residentTemplateLayout);

        saveTemplateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBitMap(AdminResidentDetailedActivity.this, idTemplateLayout);
            }
        });
    }

    private  void saveTemplateAsImage()  {
//        File file = save
    }

    private File saveBitMap(Context context, View drawView){
        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"DoorMat");
        if (!pictureFileDir.exists()) {
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if(!isDirectoryCreated)
                Log.d(TAG, "Can't create directory to save the image");
            return null;
        }

        String filename = pictureFileDir.getPath() +File.separator+ System.currentTimeMillis()+".jpg";
        File pictureFile = new File(filename);
        Bitmap bitmap = getBitmapFromView(drawView);
        try {
            pictureFile.createNewFile();
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, oStream);
            Toast.makeText(context, "Image saved under DoorMat directory.", Toast.LENGTH_SHORT).show();
            oStream.flush();
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "There was an issue saving the image.");
        }
        scanGallery( context,pictureFile.getAbsolutePath());
        return pictureFile;
    }

    //create bitmap from view and returns it
    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    // used for scanning gallery
    private void scanGallery(Context cntx, String path) {
        try {
            MediaScannerConnection.scanFile(cntx, new String[] { path },null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
