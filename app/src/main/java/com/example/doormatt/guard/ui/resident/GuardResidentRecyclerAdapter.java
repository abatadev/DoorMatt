package com.example.doormatt.guard.ui.resident;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doormatt.R;
import com.example.doormatt.guard.ui.logs.GuardLogsRecyclerAdapter;
import com.example.doormatt.model.ResidentModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class GuardResidentRecyclerAdapter extends FirebaseRecyclerAdapter<ResidentModel, GuardResidentRecyclerAdapter.ViewHolder> {

    public GuardResidentRecyclerAdapter(@NonNull @NotNull FirebaseRecyclerOptions<ResidentModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position, @NonNull @NotNull ResidentModel model) {
        holder.residentName.setText(model.getFirstName() +" " + model.getLastName());
        holder.roomNumber.setText(model.getRoomNumber());
        holder.status.setText("Status");
        Picasso.get().load(model.getResidentAvatar()).into(holder.residentAvatar);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_guard_resident, parent, false);
        return new GuardResidentRecyclerAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView residentName, roomNumber, status;
        CircleImageView residentAvatar;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            residentName = itemView.findViewById(R.id.card_view_guard_resident_name_textView);
            roomNumber = itemView.findViewById(R.id.card_view_guard_resident_room_number_textView);
            status = itemView.findViewById(R.id.card_view_guard_status_textView);
            residentAvatar = itemView.findViewById(R.id.guard_resident_avatar);
        }
    }
}
