package com.example.doormatt.admin.ui.resident;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doormatt.R;
import com.example.doormatt.model.ResidentModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminResidentRecyclerAdapter extends FirebaseRecyclerAdapter<ResidentModel, AdminResidentRecyclerAdapter.ViewHolder> {

    public AdminResidentRecyclerAdapter(@NonNull @NotNull FirebaseRecyclerOptions<ResidentModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull AdminResidentRecyclerAdapter.ViewHolder holder, int position, @NonNull @NotNull ResidentModel model) {
        Picasso.get().load(model.getResidentAvatar()).into(holder.residentAvatar);
        holder.residentNameTextView.setText("Name: " + model.getFirstName() + " " + model.getLastName());
        holder.residentRoomNumberTextView.setText("Room #: " + model.getRoomNumber());
    }

    @NonNull
    @NotNull
    @Override
    public AdminResidentRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_admin_resident, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView residentNameTextView, residentRoomNumberTextView, residentStatusTextView;
        private CircleImageView residentAvatar;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            residentAvatar = itemView.findViewById(R.id.admin_resident_avatar);
            residentNameTextView = itemView.findViewById(R.id.card_view_admin_resident_name_textView);
            residentRoomNumberTextView = itemView.findViewById(R.id.card_view_admin_resident_roomNumber_textView);
        }
    }
}
