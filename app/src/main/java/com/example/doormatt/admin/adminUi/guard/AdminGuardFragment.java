package com.example.doormatt.admin.adminUi.guard;

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
import com.example.doormatt.common.Common;
import com.example.doormatt.model.GuardModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class AdminGuardFragment extends Fragment {
    private final String TAG = AdminGuardFragment.class.getSimpleName();

    private DatabaseReference guardRef;
    private AdminGuardRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private GuardModel guardModel;
    private Button newGuardButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_admin_guard, container, false);
        recyclerView = view.findViewById(R.id.admin_guard_list);
        newGuardButton = view.findViewById(R.id.new_guard_button);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        guardModel = new GuardModel();
        queryList();

        newGuardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewGuardActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void queryList() {
        FirebaseRecyclerOptions<GuardModel> setOptions =
                new FirebaseRecyclerOptions.Builder<GuardModel>()
                    .setQuery(
                        FirebaseDatabase.getInstance().getReference(Common.GUARD_REF)
                        ,GuardModel.class)
                    .build();

        adapter = new AdminGuardRecyclerAdapter(setOptions);
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