package com.example.doormatt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doormatt.common.Common;
import com.example.doormatt.common.ValidateAccountDetails;
import com.example.doormatt.common.ValidateResidentDetails;
import com.example.doormatt.model.ResidentModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewResidentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int PICK_IMAGE_REQUEST = 2;

    private EditText firstNameEditText, lastNameEditText, roomNumberEditText;
    private TextView dateOfBirthTextView, showDateOfBirthTextView;
    private Button addPictureButton, saveResidenceButton;
    private CircleImageView avatarResidenceCircleImageView;

    private String firstName, lastName, dateOfBirth, roomNumber;
    String downloadImageUrl;
    private Uri imageUri = null;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private StorageReference mStorageRef;

    AlertDialog alertDialog;

    ValidateResidentDetails validateResidentDetails;
    ResidentModel residentModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_residence);

        // Initialize Firebase
        mStorageRef = FirebaseStorage.getInstance().getReference().child(Common.AVATAR_IMAGES);
        mReference = FirebaseDatabase.getInstance().getReference().child(Common.RESIDENT_REF);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        roomNumberEditText = findViewById(R.id.roomNumberEditText);

        dateOfBirthTextView = findViewById(R.id.dateOfBirthTextView);
        showDateOfBirthTextView = findViewById(R.id.showDateOfBirthTextView);

        addPictureButton = findViewById(R.id.editPictureButton);
        saveResidenceButton = findViewById(R.id.saveResidenceButton);

        validateResidentDetails = new ValidateResidentDetails(
                NewResidentActivity.this,
                firstNameEditText,
                lastNameEditText,
                roomNumberEditText
        );

        //TODO Add view ID.

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        saveResidenceButton.setOnClickListener(v -> {
            uploadImageToFirebase();
        });

        addPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogOption();
            }
        });

        showDateOfBirthTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void openDialogOption() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Pick");
        builder.setPositiveButton("Open Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openCamera();
            }
        });

        builder.setNeutralButton("Open Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openGallery();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        builder.create();
        builder.show();
    }


    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dateOfBirth = month + "/" + dayOfMonth + "/" + year;
        dateOfBirthTextView.setText(dateOfBirth);
    }

    private void submitNewResidentToFirebase() {
        boolean firstNameVerified = validateResidentDetails.validateFirstName();
        boolean lastNameVerified = validateResidentDetails.validateLastName();
        boolean roomNumberVerified = validateResidentDetails.validateRoomNumber();

        if(firstNameVerified && lastNameVerified && roomNumberVerified) {
            residentModel = new ResidentModel();

            final String residentId = mReference.push().getKey();
            firstName = firstNameEditText.getText().toString().trim();
            lastName = lastNameEditText.getText().toString().trim();
            roomNumber = roomNumberEditText.getText().toString().trim();

            residentModel.setResidentId(residentId);
            residentModel.setFirstName(firstName);
            residentModel.setLastName(lastName);
    //            residentModel.setDateOfBirth();
            residentModel.setRoomNumber(roomNumber);

            mReference.child(residentId).setValue(residentModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(NewResidentActivity.this, "New resident added!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("submitToFirebase", "E:" + e.getMessage());
                    Toast.makeText(NewResidentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        } catch(NullPointerException e) {
            Log.e("openCamera", "E:" + e.getMessage());
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryIntent, "Select picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Common.PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
        }
    }

    private void uploadImageToFirebase() {
        if(imageUri == null) {
            Toast.makeText(this, "Please upload an image.", Toast.LENGTH_SHORT).show();
        }

        String unique_name = "avatar_" + UUID.randomUUID().toString();

        try {
            final StorageReference filePath = mStorageRef.child(imageUri.getLastPathSegment()+ unique_name);
            final UploadTask uploadTask = filePath.putFile(imageUri);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if(!task.isSuccessful()) {
                                throw task.getException();
                            }

                            downloadImageUrl = filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()) {
                                downloadImageUrl = task.getResult().toString();
                                residentModel.setResidentAvatar(downloadImageUrl);
                                submitNewResidentToFirebase();
                                //TODO Submit values.
                            }
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(NewResidentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch(NullPointerException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}