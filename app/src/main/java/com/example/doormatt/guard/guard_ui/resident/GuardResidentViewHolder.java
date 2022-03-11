package com.example.doormatt.guard.guard_ui.resident;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doormatt.R;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class GuardResidentViewHolder extends RecyclerView.ViewHolder {

    TextView residentNameTextView, residentRoomNumberTextView, residentStatusTextView;
    CircleImageView residentAvatar;
    Button checkInButton, checkOutButton;

    public GuardResidentViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        residentAvatar = itemView.findViewById(R.id.guard_resident_avatar);
        residentNameTextView = itemView.findViewById(R.id.card_view_guard_resident_name_textView);
        residentStatusTextView = itemView.findViewById(R.id.card_view_guard_status_textView);
        residentRoomNumberTextView = itemView.findViewById(R.id.card_view_guard_resident_room_number_textView);

        checkInButton = itemView.findViewById(R.id.card_view_guard_check_in_button);
        checkOutButton = itemView.findViewById(R.id.card_view_guard_check_out_button);
    }
}
