package com.example.doormatt.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.role.RoleManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doormatt.R;
import com.example.doormatt.common.Common;
import com.example.doormatt.model.RolesModel;
import com.example.doormatt.validation.ValidateResidentInput;
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

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.UUID;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewResidentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    final String TAG = NewResidentActivity.class.getSimpleName();

    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int PICK_IMAGE_REQUEST = 1234;

    private QRGEncoder qrgEncoder;

    private EditText firstNameEditText, lastNameEditText, roomNumberEditText;
    private TextView dateOfBirthTextView;
    private Button addPictureButton, saveResidenceButton;
    private ImageView qrImage, showDateOfBirthTextView;
    private CircleImageView avatarResidenceCircleImageView;

    private String firstName, lastName, dateOfBirth, roomNumber;
    private String downloadImageUrl;
    private String downloadQRImageUrl;
    private String qrCodeValue;
    private String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";

    private Bitmap bitmap;

    private Uri imageUri = null;
    private Uri qrImageUri = null;

    private FirebaseDatabase mDatabase;
    private DatabaseReference residentRef, roleRef;
    private StorageReference avatarStorageRef;
    private StorageReference qrStorageRef;

    AlertDialog alertDialog;

    ValidateResidentInput validateResidentDetails;
    ResidentModel residentModel;
    RolesModel rolesModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_residence);

        // Initialize Firebase
        avatarStorageRef = FirebaseStorage.getInstance().getReference().child(Common.AVATAR_IMAGES);
        qrStorageRef = FirebaseStorage.getInstance().getReference().child(Common.QR_IMAGES);
        residentRef = FirebaseDatabase.getInstance().getReference().child(Common.RESIDENT_REF);
        roleRef = FirebaseDatabase.getInstance().getReference().child(Common.ROLE_REF);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        roomNumberEditText = findViewById(R.id.roomNumberEditText);

        dateOfBirthTextView = findViewById(R.id.dateOfBirthTextView);
        showDateOfBirthTextView = findViewById(R.id.showDateofBirthImageView);

        addPictureButton = findViewById(R.id.editPictureButton);
        saveResidenceButton = findViewById(R.id.saveResidenceButton);

        qrImage = findViewById(R.id.qrCodeImageView);

        validateResidentDetails = new ValidateResidentInput(
                NewResidentActivity.this,
                firstNameEditText,
                lastNameEditText,
                roomNumberEditText
        );

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        saveResidenceButton.setOnClickListener(v -> {
            uploadProfileImageToFirebase();
        });

        addPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                openDialogOption();
                openGallery();
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

    private void generateQRCode() {
        qrCodeValue = residentModel.getResidentId();
        Log.d(TAG, "QR Code: " + qrCodeValue);

        if(qrCodeValue.length() > 0) {
            WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;

            qrgEncoder = new QRGEncoder(
                    qrCodeValue, null,
                    QRGContents.Type.TEXT,
                    smallerDimension);
            qrgEncoder.setColorBlack(Color.BLACK);
            qrgEncoder.setColorWhite(Color.WHITE);

            try {
                bitmap = qrgEncoder.getBitmap();
                qrImage.setImageBitmap(bitmap);

                saveQrCode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
//            edtValue.setError(getResources().getString(R.string.value_required));
        }

        Bitmap onSetQRBitmap = ((BitmapDrawable) qrImage.getDrawable()).getBitmap();
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        onSetQRBitmap.compress(Bitmap.CompressFormat.JPEG, 100, boas);
        byte[] data = boas.toByteArray();

//        final StorageReference filePath = avatarStorageRef.child(imageUri.getLastPathSegment()+ unique_name);
        UploadTask uploadTask = qrStorageRef.child(qrCodeValue).putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.e(TAG, "Error: " + e.getMessage());
            }
        })
        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "SUCCESS");
            }
        });
    }

    private void saveQrCode() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            try {
                boolean save = new QRGSaver().save(savePath, qrCodeValue, bitmap, QRGContents.ImageType.IMAGE_JPEG);
                String result = save ? "Image Saved" : "Image Not Saved";
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }


    private void submitNewResidentToFirebase() {
        boolean firstNameVerified = validateResidentDetails.validateFirstName();
        boolean lastNameVerified = validateResidentDetails.validateLastName();
        boolean roomNumberVerified = validateResidentDetails.validateRoomNumber();

        if(firstNameVerified && lastNameVerified && roomNumberVerified) {
            residentModel = new ResidentModel();

            final String residentId = residentRef.push().getKey();
            firstName = firstNameEditText.getText().toString().trim();
            lastName = lastNameEditText.getText().toString().trim();
            roomNumber = roomNumberEditText.getText().toString().trim();
            dateOfBirth = dateOfBirthTextView.getText().toString().trim();

            residentModel.setResidentId(residentId);
            residentModel.setFirstName(firstName);
            residentModel.setLastName(lastName);
            residentModel.setDateOfBirth(dateOfBirth);
            residentModel.setRoomNumber(roomNumber);
            residentModel.setResidentAvatar(downloadImageUrl);
            residentModel.setQrCode(qrCodeValue);

            residentRef.child(residentId).setValue(residentModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(NewResidentActivity.this, "New resident added!", Toast.LENGTH_SHORT).show();
                    saveResidenceButton.setText("Submitted");
                    saveResidenceButton.setEnabled(false);

                    rolesModel = new RolesModel(residentId, 3);
                    roleRef.child(residentId).setValue(rolesModel).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Log.d(TAG, "Error Role: " + e.getMessage());
                        }
                    });

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
        startActivityForResult(Intent.createChooser(galleryIntent, "Select picture"), Common.PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Common.PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
        }
    }

    private void uploadProfileImageToFirebase() {
        if(imageUri == null) {
            Toast.makeText(this, "Please upload an image.", Toast.LENGTH_SHORT).show();
        }

        String unique_name = "avatar_" + UUID.randomUUID().toString();

        try {
            final StorageReference filePath = avatarStorageRef.child(imageUri.getLastPathSegment()+ unique_name);
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
                                submitNewResidentToFirebase();
                                generateQRCode();
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