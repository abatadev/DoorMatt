package com.example.doormatt.qrcode;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.example.doormatt.R;
import com.example.doormatt.common.Common;
import com.example.doormatt.model.LogsModel;
import com.example.doormatt.model.ResidentModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class QRScannerFragment extends Fragment {

    private static final String TAG = QRScannerFragment.class.getSimpleName();
    private static final int PERMISSION_REQUEST_CAMERA = 0;

    private PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    private FirebaseDatabase mDatabase;
    private DatabaseReference residentRef, logsRef;
    private Button qrCodeFoundButton;
    private String qrCode;
    private String residentId, firstName, lastName, residentAvatar, roomNumber, scannedBy;
    private int residentStatus;

    private CodeScanner mCodeScanner;

    LogsModel logsModel = new LogsModel();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Activity activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_admin_qr, container, false);
        CodeScannerView scannerView = view.findViewById(R.id.qrScannerView);
        mCodeScanner = new CodeScanner(activity, scannerView);
        mCodeScanner.setCamera(CodeScanner.CAMERA_BACK);
        mCodeScanner.setFormats(CodeScanner.TWO_DIMENSIONAL_FORMATS);
        mCodeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
        mCodeScanner.setScanMode(ScanMode.SINGLE);
        mCodeScanner.setAutoFocusEnabled(true);
        mCodeScanner.setFlashEnabled(false);

        DatabaseReference guardRef, adminRef, roleRef;
        guardRef = FirebaseDatabase.getInstance().getReference(Common.GUARD_REF);
        adminRef = FirebaseDatabase.getInstance().getReference(Common.ADMIN_REF);
        roleRef = FirebaseDatabase.getInstance().getReference(Common.ROLE_REF);


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String myId = mAuth.getCurrentUser().getUid().toString();
        Log.d(TAG, "Role ID: " + myId);
        roleRef.child(myId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.hasChild(myId)) {
                    int roleType = snapshot.child("accountType").getValue(int.class);

                    switch(roleType) {
                        case (Common.ADMIN_ROLE):
                            readAdminDetails(myId, adminRef);
                            break;
                        case (Common.GUARD_ROLE):
                            readGuardDetails(myId, guardRef);
                            break;

                    }
                } else {
                    //TODO
                }
            }


            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull @NotNull Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(activity, result.getText(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "run: " + result.getText());
                        retrieveQRCodeData(result.getText());
                    }

                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeScanner.startPreview();
            }
        });

        previewView = view.findViewById(R.id.fragment_qr_code_previewView);

        cameraProviderFuture = ProcessCameraProvider.getInstance(getContext());
        requestCamera();
        return view;
    }

    private void readGuardDetails(String myId, DatabaseReference guardRef) {
        guardRef.child(myId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    scannedBy = snapshot.child("guardName").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void readAdminDetails(String myId, DatabaseReference adminRef) {
        adminRef.child(myId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                final String adminId = snapshot.child("userId").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void retrieveQRCodeData(String qrCode) {
        mDatabase = FirebaseDatabase.getInstance();
        residentRef = mDatabase.getReference(Common.RESIDENT_REF);

        Log.d(TAG, "retrieveQRCodeData: " + qrCode);
        residentRef.child(qrCode).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                if(snapshot.exists(qrCode)) {
                try {
                    residentId = snapshot.child("residentId").getValue().toString();
                    final String firstName = snapshot.child("firstName").getValue().toString();
                    final String lastName = snapshot.child("lastName").getValue().toString();
                    final String middleName = snapshot.child("middleName").getValue().toString();
                    final String residentAvatar = snapshot.child("residentAvatar").getValue().toString();
                    final String roomNumber = snapshot.child("roomNumber").getValue().toString();
                    final String contactNumber = snapshot.child("contactNumber").getValue().toString();
                    residentStatus = snapshot.child("residentStatus").getValue(int.class);

                    Log.d(TAG, "User ID: " + residentId);
                    Log.d(TAG, "First Name: " + firstName);
                    Log.d(TAG, "Last Name: " + lastName);
                    Log.d(TAG, "Resident Avatar Path: " + residentAvatar);
                    Log.d(TAG, "Room Number: " + roomNumber);

                    showResidentDialog(residentId, firstName, middleName, lastName, contactNumber, residentAvatar, roomNumber, residentStatus);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Code doest not exist in the database.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }

    private void showResidentDialog(String residentId, String firstName, String middleName, String lastName, String contactNumber, String residentAvatar, String roomNumber, int residentStatus) {
        ResidentModel residentModel = new ResidentModel();

        AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        logsRef = mDatabase.getReference(Common.LOGS_REF);
        String logId = logsRef.push().getKey();
        Date dateToTime = new Date();
        String strDateFormat = "HH:mm:ss a";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String time = sdf.format(dateToTime);
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        Map map = new HashMap();
        map.put("timestamp", ServerValue.TIMESTAMP);

        logsModel.setLogId(logId);
        logsModel.setGuardId("admin");
        logsModel.setResidentFirstname(firstName);
        logsModel.setResidentMiddleName(middleName);
        logsModel.setResidentLastName(lastName);
        logsModel.setResidentContactNumber(contactNumber);
        logsModel.setResidentId(residentId);
        logsModel.setResidentRoomNumber(roomNumber);
        logsModel.setGuardName("Admin");
        logsModel.setDateRecorded(date);
        logsModel.setTimeRecorded(time);
        logsModel.setResidentStatus(residentStatus);

        builder.setTitle("Resident").create();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_show_resident_details, null);

        TextView residentIdTextView = view.findViewById(R.id.dialogQrResidentId);
        TextView residentNameTextView = view.findViewById(R.id.dialogQrFirstName);
        TextView residentRoomNumberTextView = view.findViewById(R.id.dialogRoomNumber);
        TextView residentStatusTextView = view.findViewById(R.id.dialogQrStatus);
        CircleImageView residentAvatarCircleImageView = view.findViewById(R.id.dialogResidentAvatar);
        Picasso.get().load(residentAvatar).into(residentAvatarCircleImageView);

        residentIdTextView.setText("Resident ID: " + residentId);
        residentNameTextView.setText("Name: " + firstName + " " + lastName);
        residentRoomNumberTextView.setText("Room Number: "+ roomNumber);

        // Read resident status
        residentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                final int residentStatus = snapshot.child(residentId).child("residentStatus").getValue(int.class);

                try {
                    if (residentStatus == Common.CHECKED_OUT) {
                        residentStatusTextView.setText("Checked Out");
                    } else if (residentStatus == Common.CHECKED_IN) {
                        residentStatusTextView.setText("Checked In");
                    } else {
                        residentStatusTextView.setVisibility(View.GONE);
                    }
                } catch (NullPointerException e) {
                    Log.d(TAG, "onDataChange: "  + e.getMessage());
                    residentStatusTextView.setText(0);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        builder.setPositiveButton("Check In", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResidentToCheckedIn(logId);
            }
        });

        builder.setNeutralButton("Check Out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResidentToCheckedOut(logId);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setView(view).show();
    }

    private void setResidentToCheckedIn(String logId) {
        Log.d(TAG, "onClick: " + logId);
        logsModel.setResidentStatus(Common.CHECKED_IN);
        logsRef.child(logId).setValue(logsModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Map<String, Object> updateData = new HashMap<>();
                logsModel.setResidentStatus(Common.CHECKED_IN);
                updateData.put("residentStatus", logsModel.getResidentStatus());
//                updateData.put("residentStatus", Common.CHECKED_IN);
                updateData.put("scannedBy", scannedBy);
                residentRef.child(residentId).updateChildren(updateData);
                Toast.makeText(getContext(), "Checked In.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }
        });

    }

    private void setResidentToCheckedOut(String logId) {
        Log.d(TAG, "onClick: " + logId);
        logsModel.setResidentStatus(Common.CHECKED_OUT);
        logsRef.child(logId).setValue(logsModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Map<String, Object> updateData = new HashMap<>();
                logsModel.setResidentStatus(Common.CHECKED_OUT);
                updateData.put("residentStatus", logsModel.getResidentStatus());
//                updateData.put("residentStatus", Common.CHECKED_IN);
                residentRef.child(residentId).updateChildren(updateData);
                Toast.makeText(getContext(), "Checked Out.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }
        });

    }


    private void requestCamera() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            }
        }
    }

    @Override
    public void onResume() {
        mCodeScanner.startPreview();
        super.onResume();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    @Override
    public void onStop() {
        mCodeScanner.stopPreview();
        super.onStop();
    }
}