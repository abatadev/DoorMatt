package com.example.doormatt.guard.guard_ui.resident;

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
import com.example.doormatt.model.ResidentModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GuardResidentFragment extends Fragment {
    private final String TAG = GuardResidentFragment.class.getSimpleName();
    private Button newResidentButton;
    private EditText searchEditText;

    FirebaseRecyclerAdapter<ResidentModel, GuardResidentViewHolder> adapter;
    FirebaseRecyclerOptions<ResidentModel> options;
    RecyclerView recyclerView;
    ResidentModel residentModel;
    private DatabaseReference residentRef, logsRef, visitorRef;
    private LogsModel logsModel;

    String logId;
    String firstName;
    String middleName;
    String lastName;
    String contactNumber;
    String residentId;
    String roomNumber;
    String date;
    String time;

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guard_resident, container, false);
        residentRef = FirebaseDatabase.getInstance().getReference(Common.RESIDENT_REF);

        recyclerView = view.findViewById(R.id.guard_resident_list);
        searchEditText = view.findViewById(R.id.guard_resident_search_edit_text);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        logsModel = new LogsModel();

        residentModel = new ResidentModel();
        loadData("");

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()) {
                    loadData(editable.toString());
                } else {
                    loadData("");
                }
            }
        });
        adapter.startListening();
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void loadData(String data) {
        Query query = residentRef.orderByChild("firstName").startAt(data).endAt(data + "\uf8ff");

        options = new FirebaseRecyclerOptions.Builder<ResidentModel>()
                .setQuery(query, ResidentModel.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<ResidentModel, GuardResidentViewHolder>(options) {
            @NonNull
            @NotNull
            @Override
            public GuardResidentViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_guard_resident, parent, false);
                return new GuardResidentViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull GuardResidentViewHolder holder, int position, @NonNull @NotNull ResidentModel model) {

                holder.residentNameTextView.setText(model.getFirstName() + " " + model.getLastName());
                holder.residentRoomNumberTextView.setText(model.getRoomNumber());
                Log.d(TAG, "onBindViewHolder: " + model.getResidentStatus());
                Picasso.get().load(model.getResidentAvatar()).into(holder.residentAvatar);

                try {
                    if(model.getResidentStatus() == Common.CHECKED_OUT) {
                        holder.residentStatusTextView.setText("Checked Out");

                        // Create new visitor
                        holder.itemView.setOnClickListener(v -> {
                            Intent intent = new Intent(getContext(), GuardNewVisitorActivity.class);
                            Log.d(TAG, "onBindViewHolder: residentId: " + getRef(position).getKey());
                            intent.putExtra("residentId", getRef(position).getKey());
                            startActivity(intent);
                        });

                    } else if (model.getResidentStatus() == Common.CHECKED_IN) {
                        holder.residentStatusTextView.setText("Checked In");

                        // Create new visitor
                        holder.itemView.setOnClickListener(v -> {
                            Intent intent = new Intent(getContext(), GuardNewVisitorActivity.class);
                            Log.d(TAG, "onBindViewHolder: residentId: " + getRef(position).getKey());
                            intent.putExtra("residentId", getRef(position).getKey());
                            startActivity(intent);
                        });
                    }
                } catch (NullPointerException e) {
                    Log.d(TAG, "onBindViewHolder: " + e.getMessage());
                    e.printStackTrace();
                }

                holder.checkInButton.setOnClickListener(v -> {
                    residentRef.child(getRef(position).getKey()).child("residentStatus").setValue(Common.CHECKED_IN);
                    Toast.makeText(getContext(), "Checked in.", Toast.LENGTH_SHORT).show();
                    holder.residentStatusTextView.setText("Checked In");

                    Date dateToTime = new Date();
                    String strDateFormat = "HH:mm:ss a";
                    SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
                    time = sdf.format(dateToTime);
                    date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

                    residentRef.child(getRef(position).getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()) {
                                firstName = snapshot.child("firstName").getValue(String.class);
                                middleName = snapshot.child("middleName").getValue(String.class);
                                lastName = snapshot.child("lastName").getValue(String.class);
                                contactNumber = snapshot.child("contactNumber").getValue(String.class);
                                residentId = snapshot.child("residentId").getValue(String.class);
                                roomNumber = snapshot.child("roomNumber").getValue(String.class);

                                CheckOutLogs(firstName, middleName, lastName, contactNumber, residentId, roomNumber, date, time, Common.CHECKED_IN);
                            } else {
                                Log.d(TAG, "onDataChange: Does not exist");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    // TODO Logs Model


                });

                holder.checkOutButton.setOnClickListener(v -> {
                    residentRef.child(getRef(position).getKey()).child("residentStatus").setValue(Common.CHECKED_OUT);

                    Toast.makeText(getContext(), "Checked out.", Toast.LENGTH_SHORT).show();
                    holder.residentStatusTextView.setText("Checked Out");

                    Date dateToTime = new Date();
                    String strDateFormat = "HH:mm:ss a";
                    SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
                    time = sdf.format(dateToTime);
                    date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

                    residentRef.child(getRef(position).getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()) {
                                firstName = snapshot.child("firstName").getValue(String.class);
                                middleName = snapshot.child("middleName").getValue(String.class);
                                lastName = snapshot.child("lastName").getValue(String.class);
                                contactNumber = snapshot.child("contactNumber").getValue(String.class);
                                residentId = snapshot.child("residentId").getValue(String.class);
                                roomNumber = snapshot.child("roomNumber").getValue(String.class);

                                CheckOutLogs(firstName, middleName, lastName, contactNumber, residentId, roomNumber, date, time, Common.CHECKED_OUT);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    // TODO Logs Model



                });
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    private void CheckOutLogs(String firstName, String middleName, String lastName,
                              String contactNumber, String residentId, String roomNumber,
                              String date, String time, int residentStatus) {



        logsRef = FirebaseDatabase.getInstance().getReference(Common.LOGS_REF);
        String logId = logsRef.push().getKey();
        Log.d(TAG, "CheckOutLogs: " + logId);

        logsModel.setLogId(logId);
        logsModel.setGuardId("guard");
        logsModel.setResidentFirstname(firstName);
        logsModel.setResidentMiddleName(middleName);
        logsModel.setResidentLastName(lastName);
        logsModel.setResidentContactNumber(contactNumber);
        logsModel.setResidentId(residentId);
        logsModel.setResidentRoomNumber(roomNumber);
        logsModel.setDateRecorded(date);
        logsModel.setTimeRecorded(time);
        logsModel.setResidentStatus(residentStatus);
        logsRef.child(logId).setValue(logsModel).addOnFailureListener(e ->
                Log.e(TAG, "onFailure: " + e.getMessage()));
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

    @Override
    public void onPause() {
        super.onPause();
    }
}