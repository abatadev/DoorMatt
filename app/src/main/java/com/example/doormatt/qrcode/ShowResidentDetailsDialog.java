package com.example.doormatt.qrcode;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.doormatt.R;

import org.jetbrains.annotations.NotNull;

public class ShowResidentDetailsDialog extends AppCompatDialogFragment {
    private TextView residentIdTextView, residentFirstNameTextView, residentLastNameTextView, residentRoomNumberTextView;
    private ShowResidentDetailsDialogListener listener;

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_show_resident_details, null);

        alertDialogBuilder.setView(view)
                .setTitle("Login")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        String username = editTextUsername.getText().toString();
//                        String password = editTextPassword.getText().toString();
//                        listener.applyTexts(username, password);
                    }
                });
        return alertDialogBuilder.create();
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        try {
            listener = (ShowResidentDetailsDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }

    }

    public interface ShowResidentDetailsDialogListener {
        void applyTexts(String username, String password);
    }
}
