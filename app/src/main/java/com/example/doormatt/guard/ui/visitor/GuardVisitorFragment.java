package com.example.doormatt.guard.ui.visitor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doormatt.R;
import com.example.doormatt.common.Common;
import com.example.doormatt.guard.ui.logs.GuardLogsRecyclerAdapter;
import com.example.doormatt.guard.ui.resident.GuardResidentRecyclerAdapter;
import com.example.doormatt.model.ResidentModel;
import com.example.doormatt.model.VisitorModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class GuardVisitorFragment extends Fragment {
    GuardVisitorRecyclerAdapter adapter;
    RecyclerView recyclerView;
    VisitorModel visitorModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guard_visitor, container, false);
        recyclerView = view.findViewById(R.id.guard_visitor_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        visitorModel = new VisitorModel();
        queryList();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void queryList() {
        FirebaseRecyclerOptions<VisitorModel> setOptions =
                new FirebaseRecyclerOptions.Builder<VisitorModel>()
                        .setQuery(
                                FirebaseDatabase.getInstance().getReference(Common.VISITOR_REF)
                                ,VisitorModel.class)
                        .build();

        adapter = new GuardVisitorRecyclerAdapter(setOptions);
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
