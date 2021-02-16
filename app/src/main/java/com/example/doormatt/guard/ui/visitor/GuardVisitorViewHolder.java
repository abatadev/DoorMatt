package com.example.doormatt.guard.ui.visitor;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doormatt.R;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class GuardVisitorViewHolder extends RecyclerView.ViewHolder {
    TextView residentFirstName, residentLastName, residentContactNumberTextView, residentRoomNumberTextView;
    TextView visitorFirstName, visitorLastName, visitorContactNumber;

    public GuardVisitorViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        residentFirstName = itemView.findViewById(R.id.residentFirstNameVisitorGuard);
        residentLastName = itemView.findViewById(R.id.residentLastNameVisitorGuard);
        residentContactNumberTextView = itemView.findViewById(R.id.residentContactNumberVisitorGuard);
        residentRoomNumberTextView = itemView.findViewById(R.id.residentRoomNumberVisitorGuard);


        visitorFirstName = itemView.findViewById(R.id.visitorGuardFirstName);
        visitorLastName = itemView.findViewById(R.id.visitorGuardLastName);
        visitorContactNumber = itemView.findViewById(R.id.visitorGuardContactNumber);
    }
}
