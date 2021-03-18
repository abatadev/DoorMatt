package com.example.doormatt.admin.admin_ui.logs;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doormatt.R;
import com.example.doormatt.common.Common;
import com.example.doormatt.model.LogsModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.jetbrains.annotations.NotNull;

public class AdminLogsFragment extends Fragment {
    private final String TAG = AdminLogsFragment.class.getSimpleName();
    private DatabaseReference logsRef;
    FirebaseRecyclerAdapter<LogsModel, AdminLogsViewHolder> adapter;
    FirebaseRecyclerOptions<LogsModel> options;
    RecyclerView recyclerView;
    LogsModel logsModel;
    private EditText searchEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_logs, container, false);
        logsRef = FirebaseDatabase.getInstance().getReference(Common.LOGS_REF);
        recyclerView = view.findViewById(R.id.admin_logs_list);

        searchEditText = view.findViewById(R.id.admin_logs_search_editText);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        logsModel = new LogsModel();
        loadData("");

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()) {
                    loadData(editable.toString());
                } else {
                    loadData("");
                }
            }
        });
        return view;
    }

    private void loadData(String data) {
        Query query = logsRef.orderByChild("residentFirstname").startAt(data).endAt(data + "\uf8ff");

        options = new FirebaseRecyclerOptions.Builder<LogsModel>()
                .setQuery(query, LogsModel.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<LogsModel, AdminLogsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull AdminLogsViewHolder holder, int position, @NonNull @NotNull LogsModel model) {
                holder.residentNameTextView.setText(model.getResidentFirstname() + " " + model.getResidentMiddleName() + " " + model.getResidentLastName());
                holder.residentRoomTextView.setText(model.getResidentRoomNumber());
                holder.residentStatusTextView.setText(" " + model.getResidentStatus());

                holder.dateAndTimeTextView.setText("Date: " + model.getDateRecorded() + "\nTime:  " + model.getTimeRecorded());
                Log.d(TAG, "onBindViewHolder: Resident Name: " + model.getResidentFirstname());
                Log.d(TAG, "onBindViewHolder: Resident Status: " + model.getResidentStatus());
                try {
                    if(model.getResidentStatus() == Common.CHECKED_OUT) {
                        holder.residentStatusTextView.setText("Checked Out");
                    } else if (model.getResidentStatus() == Common.CHECKED_IN) {
                        holder.residentStatusTextView.setText("Checked In");
                    }
                } catch (NullPointerException e) {
                    Log.d(TAG, "onBindViewHolder: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            @NonNull
            @NotNull
            @Override
            public AdminLogsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_admin_logs, parent, false);
                return new AdminLogsViewHolder(view);
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
