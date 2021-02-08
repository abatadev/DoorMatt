package com.example.doormatt.admin.ui.resident;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.doormatt.common.Common.QR_IMAGES;

public class AdminResidentDetailedActivity extends AppCompatActivity {
    final String TAG = AdminResidentDetailedActivity.class.getSimpleName();

    private TextView firstNameTextView, lastNameTextView, dateOfBirthTextView, roomNumberTextView;
    private CircleImageView residentAvatarCircleImageView;
    private ImageView qrCode;
    private LinearLayout linearLayout;

    private Button saveTemplateButton;

    private String residentId;
    private DatabaseReference residentRef;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_detailed);
        residentRef = FirebaseDatabase.getInstance().getReference(Common.RESIDENT_REF);

        residentId = getIntent().getStringExtra("residentId");

        firstNameTextView = findViewById(R.id.resident_detailed_first_name_textView);
        lastNameTextView = findViewById(R.id.resident_detailed_last_name_textView);
        dateOfBirthTextView = findViewById(R.id.resident_detailed_date_of_birth_textView);
        roomNumberTextView = findViewById(R.id.resident_detailed_room_number_text_view);
        residentAvatarCircleImageView = findViewById(R.id.resident_detailed_avatar);
        saveTemplateButton = findViewById(R.id.save_template_button);
        qrCode = findViewById(R.id.resident_detailed_qr_code);

        residentRef.child(residentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                try {
                    final String firstName = snapshot.child("firstName").getValue().toString();
                    final String lastName = snapshot.child("lastName").getValue().toString();
                    final String dateOfBirth = snapshot.child("dateOfBirth").getValue().toString();
                    final String roomNumber = snapshot.child("roomNumber").getValue().toString();

                    final String residentAvatar = snapshot.child("residentAvatar").getValue().toString();
//                    final String residentQRCode = snapshot.child(residentId).getValue().toString();

                    firstNameTextView.setText(firstName);
                    lastNameTextView.setText(lastName);
                    dateOfBirthTextView.setText(dateOfBirth);
                    roomNumberTextView.setText(roomNumber);
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

        linearLayout = findViewById(R.id.id_resident_template_layout);

        saveTemplateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTemplateAsImage();
            }
        });
    }

    private  void saveTemplateAsImage()  {
//        File file = save
    }

    private File getdisc() {
        File file= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        return new File(file,"My Image");
    }

    private static Bitmap viewToBitmap(View view, int widh, int hight)
    {
        Bitmap bitmap=Bitmap.createBitmap(widh,hight, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap); view.draw(canvas);
        return bitmap;
    }



}
