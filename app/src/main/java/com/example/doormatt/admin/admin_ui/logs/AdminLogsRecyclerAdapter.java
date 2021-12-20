package com.example.doormatt.admin.admin_ui.logs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doormatt.R;
import com.example.doormatt.model.LogsModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

public class AdminLogsRecyclerAdapter extends FirebaseRecyclerAdapter<LogsModel, AdminLogsRecyclerAdapter.ViewHolder> {

    public AdminLogsRecyclerAdapter(@NonNull @NotNull FirebaseRecyclerOptions<LogsModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position, @NonNull @NotNull LogsModel model) {
        holder.residentNameTextView.setText("Name: " + model.getResidentFirstname() + " " + model.getResidentLastName());
        holder.residentRoomTextView.setText("Room: " + model.getResidentRoomNumber());
        holder.residentStatusTextView.setText("Status: " + model.getResidentStatus());
        holder.guardNameTextView.setText("Guard: " + model.getGuardName());
        holder.dateAndTimeTextView.setText("Date: + " + model.getDateRecorded());
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_admin_logs, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView residentNameTextView, residentRoomTextView, residentStatusTextView;
        private TextView guardNameTextView;
        private TextView dateAndTimeTextView;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            residentNameTextView = itemView.findViewById(R.id.card_view_logs_resident_name_textView);
            residentRoomTextView = itemView.findViewById(R.id.card_view_logs_resident_room_textView);
            residentStatusTextView = itemView.findViewById(R.id.card_view_logs_resident_status_textView);
//            guardNameTextView = itemView.findViewById(R.id.card_view_logs_guard_name_textView);
            dateAndTimeTextView = itemView.findViewById(R.id.card_view_logs_resident_date_time_textView);
        }
    }

}
