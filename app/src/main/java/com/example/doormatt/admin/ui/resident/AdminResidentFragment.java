package com.example.doormatt.admin.ui.resident;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.doormatt.R;
import com.example.doormatt.admin.ui.guard.AdminGuardRecyclerAdapter;
import com.example.doormatt.common.Common;
import com.example.doormatt.model.AdminModel;
import com.example.doormatt.model.GuardModel;
import com.example.doormatt.model.ResidentModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminResidentFragment extends Fragment {
    private final String TAG = AdminResidentFragment.class.getSimpleName();

    private DatabaseReference residentRef;
    private AdminResidentRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private ResidentModel residentModel;
    private Button newResidentButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_resident, container, false);
        recyclerView = view.findViewById(R.id.admin_resident_recyclerView);
        newResidentButton = view.findViewById(R.id.new_resident_button);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        residentModel = new ResidentModel();
        queryList();

        newResidentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewResidentActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void queryList() {
        FirebaseRecyclerOptions<ResidentModel> setOptions =
            new FirebaseRecyclerOptions.Builder<ResidentModel>()
                .setQuery(
                    FirebaseDatabase.getInstance().getReference(Common.RESIDENT_REF)
                    ,ResidentModel.class)
                .build();

        adapter = new AdminResidentRecyclerAdapter(setOptions);
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