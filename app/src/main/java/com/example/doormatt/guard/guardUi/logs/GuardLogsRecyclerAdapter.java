package com.example.doormatt.guard.guardUi.logs;

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
        holder.residentName.setText(String.format("%s %s", model.getResidentFirstname(), model.getResidentLastName()));
        holder.residentRoom.setText(model.getResidentRoomNumber());
        holder.residentStatus.setText("" + model.getResidentStatus());
        holder.residentContactNumber.setText(model.getResidentContactNumber());
        holder.residentTime.setText(model.getTimeRecorded());
        holder.residentDate.setText(model.getDateRecorded());
    }

    @NonNull
    @NotNull
    @Override
    public GuardLogsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_guard_logs, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView residentName, residentRoom, residentStatus, residentContactNumber,
                    residentTime, residentDate;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            residentName = itemView.findViewById(R.id.residentNameTextViewLogs);
            residentRoom = itemView.findViewById(R.id.visitorRoomNumberTextViewLogs);
            residentStatus = itemView.findViewById(R.id.residentStatusTextViewLogs);
            residentContactNumber = itemView.findViewById(R.id.visitorPhoneNumberTextViewLogs);
            residentTime = itemView.findViewById(R.id.residentTimeInTextViewLogs);
            residentDate = itemView.findViewById(R.id.residentDateTextViewLogs);
        }

    }
}
