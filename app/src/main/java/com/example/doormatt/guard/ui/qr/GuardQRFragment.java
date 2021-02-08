package com.example.doormatt.guard.ui.qr;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doormatt.R;
import com.example.doormatt.common.Common;
import com.example.doormatt.model.LogsModel;
import com.example.doormatt.model.ResidentModel;
import com.example.doormatt.qrcode.QRCodeActivity;
import com.example.doormatt.qrcode.QRCodeFoundListener;
import com.example.doormatt.qrcode.QRCodeImageAnalyzer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class GuardQRFragment extends Fragment {

    private static final String TAG = QRCodeActivity.class.getSimpleName();
    private static final int PERMISSION_REQUEST_CAMERA = 0;

    private PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    private FirebaseDatabase mDatabase;
    private DatabaseReference residentRef, logsRef;
    private Button qrCodeFoundButton;
    private String qrCode;
    private String residentId, firstName, lastName, residentAvatar, roomNumber;
    LogsModel logsModel = new LogsModel();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_qr, container, false);

        previewView = view.findViewById(R.id.fragment_qr_code_previewView);

        qrCodeFoundButton = view.findViewById(R.id.fragment_qr_code_admin_button);
        qrCodeFoundButton.setVisibility(View.INVISIBLE);
        qrCodeFoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), qrCode, Toast.LENGTH_SHORT).show();
                Log.i(QRCodeActivity.class.getSimpleName(), "QR Code Found: " + qrCode);
                retrieveQRCodeData(qrCode);
            }
        });

        cameraProviderFuture = ProcessCameraProvider.getInstance(getContext());
        requestCamera();
        return view;
    }

    private void retrieveQRCodeData(String qrCode) {
        mDatabase = FirebaseDatabase.getInstance();
        residentRef = mDatabase.getReference(Common.RESIDENT_REF);

        ResidentModel residentModel = new ResidentModel();

        Log.d(TAG, "retrieveQRCodeData: " + qrCode);
        residentRef.child(qrCode).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    final String residentId = snapshot.child("residentId").getValue().toString();
                    final String firstName = snapshot.child("firstName").getValue().toString();
                    final String lastName = snapshot.child("lastName").getValue().toString();
                    final String residentAvatar = snapshot.child("residentAvatar").getValue().toString();
                    final String roomNumber = snapshot.child("roomNumber").getValue().toString();

                    Log.d(TAG, "User ID: " + residentId);
                    Log.d(TAG, "First Name: " + firstName);
                    Log.d(TAG, "Last Name: " + lastName);
                    Log.d(TAG, "Resident Avatar Path: " + residentAvatar);
                    Log.d(TAG, "Room Number: " + roomNumber);

                    showResidentDialog(residentId, firstName, lastName, residentAvatar, roomNumber);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }

    private void showResidentDialog(String residentId, String firstName, String lastName, String residentAvatar, String roomNumber) {
        AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        logsRef = mDatabase.getReference(Common.LOGS_REF);
        String logId = logsRef.push().getKey();
        String time = Calendar.getInstance().getTime().toString();
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        Map map = new HashMap();
        map.put("timestamp", ServerValue.TIMESTAMP);

        logsModel.setLogId(logId);
        logsModel.setGuardId("admin");
        logsModel.setResidentFirstname(firstName);
        logsModel.setResidentLastName(lastName);
        logsModel.setResidentId(residentId);
        logsModel.setResidentRoomNumber(roomNumber);
        logsModel.setGuardName("Admin");
        logsModel.setDateRecorded(date);
        logsModel.setTimeRecorded(time);
        logsModel.setResidentStatus(1);


        builder.setTitle("Resident").create();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_show_resident_details, null);

        TextView residentIdTextView = view.findViewById(R.id.dialog_qr_resident_id);
        TextView residentNameTextView = view.findViewById(R.id.dialog_qr_first_name);
        TextView residentRoomNumberTextView = view.findViewById(R.id.dialog_room_number);

        residentIdTextView.setText("Resident ID: " + residentId);
        residentNameTextView.setText("Name: " + firstName + " " + lastName);
        residentRoomNumberTextView.setText("Room Number: "+ roomNumber);

        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logsRef.child(logId).setValue(logsModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: Registered to database.");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());
                    }
                });
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


    private void requestCamera() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            }
        }

    }

    private void startCamera() {
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindCameraPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(getContext(), "Error starting camera " + e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "startCamera: " + e.getMessage());
            }
        }, ContextCompat.getMainExecutor(getContext()));
    }

    private void bindCameraPreview(@NonNull ProcessCameraProvider cameraProvider) {
        previewView.setPreferredImplementationMode(PreviewView.ImplementationMode.SURFACE_VIEW);

        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(previewView.createSurfaceProvider());

        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(1280, 720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(getContext()), new QRCodeImageAnalyzer(new QRCodeFoundListener() {
            @Override
            public void onQRCodeFound(String _qrCode) {
                qrCode = _qrCode;
                qrCodeFoundButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void qrCodeNotFound() {
                qrCodeFoundButton.setVisibility(View.INVISIBLE);
            }
        }));

        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, imageAnalysis, preview);
    }


}