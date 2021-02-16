package com.example.doormatt.guard.ui.visitor;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doormatt.R;
import com.example.doormatt.admin.ui.resident.NewResidentActivity;
import com.example.doormatt.common.Common;
import com.example.doormatt.guard.ui.logs.GuardLogsRecyclerAdapter;
import com.example.doormatt.guard.ui.resident.GuardResidentFragment;
import com.example.doormatt.guard.ui.resident.GuardResidentRecyclerAdapter;
import com.example.doormatt.guard.ui.resident.GuardResidentViewHolder;
import com.example.doormatt.model.ResidentModel;
import com.example.doormatt.model.VisitorModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class GuardVisitorFragment extends Fragment {
    private final String TAG = GuardVisitorFragment.class.getSimpleName();
    private EditText searchEditText;

    FirebaseRecyclerAdapter<VisitorModel, GuardVisitorViewHolder> adapter;
    FirebaseRecyclerOptions<VisitorModel> options;
    RecyclerView recyclerView;
    VisitorModel visitorModel;
    private DatabaseReference residentRef, visitorRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guard_visitor, container, false);
        recyclerView = view.findViewById(R.id.guard_visitor_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);
        searchEditText = view.findViewById(R.id.guard_visitor_search_edit_text);

        residentRef = FirebaseDatabase.getInstance().getReference(Common.RESIDENT_REF);
        visitorRef = FirebaseDatabase.getInstance().getReference(Common.VISITOR_REF);

        visitorModel = new VisitorModel();
        searchEditText = view.findViewById(R.id.guard_visitor_search_edit_text);
//        queryList();


        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        visitorModel = new VisitorModel();
        loadData("");

//        view.findViewById(R.id.guard_add_resident_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), NewResidentActivity.class);
//                startActivity(intent);
//            }
//        });

        searchEditText.addTextChangedListener(new TextWatcher() {
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
        Query query = visitorRef.orderByChild("visitorFirstName").startAt(data).endAt(data + "\uf8ff");;

        options = new FirebaseRecyclerOptions.Builder<VisitorModel>()
                .setQuery(query, VisitorModel.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<VisitorModel, GuardVisitorViewHolder>(options) {
            @NonNull
            @NotNull
            @Override
            public GuardVisitorViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_guard_visitor, parent, false);
                return new GuardVisitorViewHolder(view);
            }


            @Override
            protected void onBindViewHolder(@NonNull @NotNull GuardVisitorViewHolder holder, int position, @NonNull @NotNull VisitorModel model) {
                holder.residentFirstName.setText(model.getResidentFirstName());
                holder.residentLastName.setText(model.getResidentLastName());
                holder.residentRoomNumberTextView.setText(model.getResidentRoomNumber());
                holder.residentContactNumberTextView.setText(model.getResidentContactNumber());

                holder.visitorFirstName.setText(model.getVisitorFirstName());
                holder.visitorLastName.setText(model.getVisitorLastName());
                holder.visitorContactNumber.setText(model.getVisitorContactNumber());

                Log.d(TAG, "onBindViewHolder: " + model.getResidentFirstName().toString());
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

