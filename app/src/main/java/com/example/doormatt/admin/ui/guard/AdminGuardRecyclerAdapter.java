package com.example.doormatt.admin.ui.guard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doormatt.R;
import com.example.doormatt.model.GuardModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

public class AdminGuardRecyclerAdapter extends FirebaseRecyclerAdapter<GuardModel, AdminGuardRecyclerAdapter.ViewHolder> {

    public AdminGuardRecyclerAdapter(@NonNull @NotNull FirebaseRecyclerOptions<GuardModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position, @NonNull @NotNull GuardModel model) {
        holder.guardName.setText(model.getGuardFullName());
        holder.guardEmail.setText(model.getGuardEmail());
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_admin_guard, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView guardName, guardEmail;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            guardName = itemView.findViewById(R.id.admin_guard_name_text_view);
            guardEmail = itemView.findViewById(R.id.admin_guard_email_text_view);
        }
    }
}
