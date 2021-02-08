package com.example.doormatt.guard.ui.resident;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doormatt.R;
import com.example.doormatt.admin.ui.resident.NewResidentActivity;
import com.example.doormatt.common.Common;
import com.example.doormatt.guard.ui.logs.GuardLogsRecyclerAdapter;
import com.example.doormatt.model.GuardModel;
import com.example.doormatt.model.LogsModel;
import com.example.doormatt.model.ResidentModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class GuardResidentFragment extends Fragment {
    private final String TAG = GuardResidentFragment.class.getSimpleName();
    private Button newResidentButton;

    GuardResidentRecyclerAdapter adapter;
    RecyclerView recyclerView;
    ResidentModel residentModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guard_resident, container, false);
        recyclerView = view.findViewById(R.id.guard_resident_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);
        residentModel = new ResidentModel();

        view.findViewById(R.id.guard_add_resident_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewResidentActivity.class);
                startActivity(intent);
            }
        });

        queryList();

        return view;
    }


    private void queryList() {
        FirebaseRecyclerOptions<ResidentModel> setOptions =
                new FirebaseRecyclerOptions.Builder<ResidentModel>()
                        .setQuery(
                                FirebaseDatabase.getInstance().getReference(Common.RESIDENT_REF)
                                ,ResidentModel.class)
                        .build();

        adapter = new GuardResidentRecyclerAdapter(setOptions);
        recyclerView.setAdapter(adapter);
    }
}