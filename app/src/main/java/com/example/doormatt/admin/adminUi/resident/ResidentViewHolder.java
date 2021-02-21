package com.example.doormatt.admin.adminUi.resident;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doormatt.R;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class ResidentViewHolder extends RecyclerView.ViewHolder {

    TextView residentNameTextView, residentRoomNumberTextView, residentStatusTextView;
    Button deleteResident, editResident;
    CircleImageView residentAvatar;

    public ResidentViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        residentAvatar = itemView.findViewById(R.id.admin_resident_avatar);
        residentNameTextView = itemView.findViewById(R.id.card_view_admin_resident_name_textView);
        residentRoomNumberTextView = itemView.findViewById(R.id.card_view_admin_resident_roomNumber_textView);
        residentStatusTextView = itemView.findViewById(R.id.card_view_admin_resident_status_textView);
        deleteResident = itemView.findViewById(R.id.delete_resident_button);
        editResident = itemView.findViewById(R.id.edit_individual_resident_button);
    }

}
