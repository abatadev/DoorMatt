package com.example.doormatt.guard.guardUi.logs;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doormatt.R;

import org.jetbrains.annotations.NotNull;

public class GuardLogsViewHolder extends RecyclerView.ViewHolder {
    TextView residentName;
    TextView residentRoom;
    TextView residentStatus;
    TextView residentContactNumber;
    TextView residentTime;
    TextView residentDate;


    public GuardLogsViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        residentName = itemView.findViewById(R.id.residentNameTextViewLogs);
        residentRoom = itemView.findViewById(R.id.visitorRoomNumberTextViewLogs);
        residentStatus = itemView.findViewById(R.id.residentStatusTextViewLogs);
        residentContactNumber = itemView.findViewById(R.id.visitorPhoneNumberTextViewLogs);
        residentTime = itemView.findViewById(R.id.residentTimeInTextViewLogs);
        residentDate = itemView.findViewById(R.id.residentDateTextViewLogs);

    }

}