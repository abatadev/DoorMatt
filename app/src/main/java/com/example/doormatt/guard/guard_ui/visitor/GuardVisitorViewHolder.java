package com.example.doormatt.guard.guard_ui.visitor;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doormatt.R;

import org.jetbrains.annotations.NotNull;

public class GuardVisitorViewHolder extends RecyclerView.ViewHolder {
    TextView residentFullName, residentContactNumberTextView, residentRoomNumberTextView;
    TextView visitorFullNameTextView, visitorContactNumberTextView;

    public GuardVisitorViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        residentFullName = itemView.findViewById(R.id.residentFullNameTextViewVisitors);
        residentRoomNumberTextView = itemView.findViewById(R.id.residentRoomNumberTextViewVisitors);
        residentContactNumberTextView = itemView.findViewById(R.id.residentContactNumberVisitors);

        visitorFullNameTextView = itemView.findViewById(R.id.visitorsFullNameTextViewVisitors);
        visitorContactNumberTextView = itemView.findViewById(R.id.visitorContactNumberTextViewVisitors);

    }
}
