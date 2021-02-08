package com.example.doormatt.admin;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doormatt.R;

import org.jetbrains.annotations.NotNull;

public class ResidentsViewHolder extends RecyclerView.ViewHolder{
    TextView residentName, residentRoomNumber;


    public ResidentsViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

//        residentName = itemView.findViewById(R.id.card_view_resident_full_name);
//        residentRoomNumber = itemView.findViewById(R.id.card_view_resident_room_number);
    }
}
