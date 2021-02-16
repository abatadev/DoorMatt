//package com.example.doormatt.guard.ui.visitor;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.doormatt.R;
//import com.example.doormatt.model.VisitorModel;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.squareup.picasso.Picasso;
//
//import org.jetbrains.annotations.NotNull;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class GuardVisitorRecyclerAdapter extends FirebaseRecyclerAdapter<VisitorModel, GuardVisitorRecyclerAdapter.ViewHolder> {
//
//    public GuardVisitorRecyclerAdapter(@NonNull @NotNull FirebaseRecyclerOptions<VisitorModel> options) {
//        super(options);
//    }
//
//    @Override
//    protected void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position, @NonNull @NotNull VisitorModel model) {
//        holder.nameTextView.setText(model.getVisitorFirstName());
//        holder.roomNumberTextView.setText(model.getResidentRoomNumber());
//    }
//
//    @NonNull
//    @NotNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_guard_visitor, parent, false);
//        return new ViewHolder(view);
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView firstNameTextView, lastNameTextView, roomNumberTextView;
//        CircleImageView visitorAvatar;
//
//        public ViewHolder(@NonNull @NotNull View itemView) {
//            super(itemView);
//            firstNameTextView = itemView.findViewById(R.id.card_view_guard_visitor_name_textView);
//            lastNameTextView = itemView.findViewById(R.id.card_view);
//            roomNumberTextView = itemView.findViewById(R.id.card_view_guard_visitor_room_number_textView);
//            visitorAvatar = itemView.findViewById(R.id.);
//        }
//    }
//
//
//}
