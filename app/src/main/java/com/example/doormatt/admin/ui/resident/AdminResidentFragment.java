package com.example.doormatt.admin.ui.resident;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doormatt.R;
import com.example.doormatt.common.Common;
import com.example.doormatt.model.ResidentModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class AdminResidentFragment extends Fragment {
    private final String TAG = AdminResidentFragment.class.getSimpleName();

    private DatabaseReference residentRef;
    FirebaseRecyclerAdapter<ResidentModel, ResidentViewHolder> adapter;
    FirebaseRecyclerOptions<ResidentModel> options;
    private RecyclerView recyclerView;
    private ResidentModel residentModel;
    private Button newResidentButton;
    private EditText searchBarEditText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_resident, container, false);

        residentRef = FirebaseDatabase.getInstance().getReference(Common.RESIDENT_REF);

        recyclerView = view.findViewById(R.id.admin_resident_recyclerView);
        newResidentButton = view.findViewById(R.id.new_resident_button);
        searchBarEditText = view.findViewById(R.id.resident_search_bar_editText);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        residentModel = new ResidentModel();
        loadData("");

        newResidentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewResidentActivity.class);
                startActivity(intent);
            }
        });

        searchBarEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString() != null) {
                    loadData(editable.toString());
                } else {
                    loadData("");
                }
            }
        });

        return view;
    }

    private void loadData(String data) {
        Query query = residentRef.orderByChild("firstName").startAt(data).endAt(data + "\uf8ff");;

        options = new FirebaseRecyclerOptions.Builder<ResidentModel>()
                .setQuery(query, ResidentModel.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<ResidentModel, ResidentViewHolder>(options) {
            @NonNull
            @NotNull
            @Override
            public ResidentViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_admin_resident, parent, false);
                return new ResidentViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull ResidentViewHolder holder, int position, @NonNull @NotNull ResidentModel model) {
                final String residentId = model.getResidentId().toString();

                holder.residentNameTextView.setText(model.getFirstName() + " " + model.getLastName());
                holder.residentRoomNumberTextView.setText(model.getRoomNumber());
//                holder.residentStatusTextView.setText("1");
                Picasso.get().load(model.getResidentAvatar()).into(holder.residentAvatar);
//                holder.residentStatusTextView.setText(model.);

                Intent intent = new Intent(getContext(), AdminResidentDetailedActivity.class);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent.putExtra("residentId", model.getResidentId());
                        startActivity(intent);
                    }
                });

            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}