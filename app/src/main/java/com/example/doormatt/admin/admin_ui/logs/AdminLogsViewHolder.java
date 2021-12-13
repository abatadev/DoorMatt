package com.example.doormatt.admin.admin_ui.logs;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doormatt.R;

import org.jetbrains.annotations.NotNull;

public class AdminLogsViewHolder extends RecyclerView.ViewHolder {

    TextView residentNameTextView, residentRoomTextView, residentStatusTextView;
    TextView guardNameTextView;
    TextView dateAndTimeTextView;

    public AdminLogsViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        residentNameTextView = itemView.findViewById(R.id.card_view_logs_resident_name_textView);
        residentRoomTextView = itemView.findViewById(R.id.card_view_logs_resident_room_textView);
        residentStatusTextView = itemView.findViewById(R.id.card_view_logs_resident_status_textView);
        guardNameTextView = itemView.findViewById(R.id.card_view_logs_guard_name_textView);
        dateAndTimeTextView = itemView.findViewById(R.id.card_view_logs_resident_date_time_textView);
    }
}
