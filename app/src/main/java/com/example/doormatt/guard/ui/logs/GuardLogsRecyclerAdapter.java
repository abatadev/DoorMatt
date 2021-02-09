package com.example.doormatt.guard.ui.logs;

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

public class GuardLogsRecyclerAdapter extends FirebaseRecyclerAdapter<LogsModel, GuardLogsRecyclerAdapter.ViewHolder> {


    public GuardLogsRecyclerAdapter(@NonNull @NotNull FirebaseRecyclerOptions<LogsModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull GuardLogsRecyclerAdapter.ViewHolder holder, int position, @NonNull @NotNull LogsModel model) {
        holder.residentName.setText(model.getResidentFirstname() + " " + model.getResidentLastName());
        holder.residentRoom.setText(model.getResidentRoomNumber());
        holder.residentStatus.setText("");
        holder.guardName.setText(model.getGuardName());
        holder.guardDateAndTime.setText(model.getDateRecorded());
    }

    @NonNull
    @NotNull
    @Override
    public GuardLogsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_guard_resident_logs, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView residentName, residentRoom, residentStatus, guardName, guardDateAndTime;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            residentName = itemView.findViewById(R.id.guard_resident_name_guard_logs_textView);
            residentRoom = itemView.findViewById(R.id.guard_resident_room_guard_logs_textView);
            residentStatus = itemView.findViewById(R.id.guard_resident_status_guard_logs_textView);
            guardName = itemView.findViewById(R.id.guard_resident_guard_name_logs_textView);
            guardDateAndTime = itemView.findViewById(R.id.guard_resident_guard_date_and_time_textView);
        }

    }
}
