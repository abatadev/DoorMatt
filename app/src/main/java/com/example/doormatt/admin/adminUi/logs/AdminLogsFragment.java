package com.example.doormatt.admin.adminUi.logs;

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
import com.example.doormatt.model.LogsModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class AdminLogsFragment extends Fragment {
    private final String TAG = AdminLogsFragment.class.getSimpleName();

    AdminLogsRecyclerAdapter adapter;
    RecyclerView recyclerView;
    LogsModel logsModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_logs, container, false);
        recyclerView = view.findViewById(R.id.admin_logs_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        logsModel = new LogsModel();


        queryList();


        return view;
    }

    private void queryList() {
        FirebaseRecyclerOptions<LogsModel> setOptions =
            new FirebaseRecyclerOptions.Builder<LogsModel>()
                .setQuery(
                    FirebaseDatabase.getInstance().getReference(Common.LOGS_REF)
                    ,LogsModel.class)
                .build();

        adapter = new AdminLogsRecyclerAdapter(setOptions);
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
