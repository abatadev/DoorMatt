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

public class ResidentViewHolder extends RecyclerView.ViewHolder {

    TextView residentNameTextView, residentRoomNumberTextView, residentStatusTextView;
    CircleImageView residentAvatar;

    public ResidentViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        residentAvatar = itemView.findViewById(R.id.admin_resident_avatar);
        residentNameTextView = itemView.findViewById(R.id.card_view_admin_resident_name_textView);
        residentRoomNumberTextView = itemView.findViewById(R.id.card_view_admin_resident_roomNumber_textView);
    }

}
