package com.example.doormatt.guard.guard_ui.logs;

import static java.text.DateFormat.getDateTimeInstance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doormatt.R;
import com.example.doormatt.common.Common;
import com.example.doormatt.guard.guard_ui.visitor.GuardNewVisitorActivity;
import com.example.doormatt.model.LogsModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.Date;

public class GuardLogsFragment extends Fragment {
    private final String TAG = GuardLogsFragment.class.getSimpleName();
//
    FirebaseRecyclerAdapter<LogsModel, GuardLogsViewHolder> adapter;
    EditText searchView;
    RecyclerView recyclerView;
    LogsModel logsModel;
    FirebaseRecyclerOptions<LogsModel> options;
    private DatabaseReference logsRef;
    private DatabaseReference residentRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guard_logs, container, false);

        logsRef = FirebaseDatabase.getInstance().getReference(Common.LOGS_REF);
        residentRef = FirebaseDatabase.getInstance().getReference(Common.RESIDENT_REF);

        searchView = view.findViewById(R.id.searchViewGuardLogsEditText);

        recyclerView = view.findViewById(R.id.guard_logs_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);
        logsModel = new LogsModel();

        loadData("");

        searchView.addTextChangedListener(new TextWatcher() {
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
//        adapter.startListening();
        return view;
    }

    private void loadData(String data) {
        Query query = logsRef.orderByChild("logId").startAt(data).endAt(data + "\uf8ff");

        options = new FirebaseRecyclerOptions.Builder<LogsModel>()
                .setQuery(query, LogsModel.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<LogsModel, GuardLogsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull GuardLogsViewHolder holder, int position, @NonNull @NotNull LogsModel model) {
                holder.residentName.setText(String.format("%s %s %s", model.getResidentFirstname(), model.getResidentMiddleName(), model.getResidentLastName()));
                holder.residentRoom.setText(model.getResidentRoomNumber());
                holder.residentStatus.setText(String.format(" %s", model.getResidentStatus()));
                holder.residentContactNumber.setText(String.format("%s", model.getResidentContactNumber()));
                holder.residentTime.setText(String.format("%s", model.getTimeRecorded()));
                holder.residentDate.setText(String.format("%s", model.getDateRecorded()));

                Log.d(TAG, "onBindViewHolder: Resident Name: " + model.getResidentFirstname());
                Log.d(TAG, "onBindViewHolder: Resident Status: " + model.getResidentStatus());

                try {
                    if(model.getResidentStatus() == Common.CHECKED_OUT) {
                        holder.residentStatus.setText(String.format("%s", "Checked out"));
                    } else if (model.getResidentStatus() == Common.CHECKED_IN) {
                        holder.residentStatus.setText(String.format("%s", "Checked in"));
                    }
                } catch (NullPointerException e) {
                    Log.d(TAG, "onBindViewHolder: " + e.getMessage());
                    e.printStackTrace();
                }

/*                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Set status")
                                .setPositiveButton("Check In", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        logsRef.child(getRef(position).getKey()).child("residentStatus").setValue(Common.CHECKED_IN);
                                        Toast.makeText(getContext(), "Checked in.", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                })
                                .setNeutralButton("Check Out", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        logsRef.child(getRef(position).getKey()).child("residentStatus").setValue(Common.CHECKED_OUT);
                                        Toast.makeText(getContext(), "Checked Out.", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });*/
            }

            @NonNull
            @NotNull
            @Override
            public GuardLogsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_guard_logs, parent, false);
                return new GuardLogsViewHolder(view);
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
