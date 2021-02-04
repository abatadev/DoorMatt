package com.example.doormatt.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.doormatt.R;
import com.example.doormatt.admin.ui.guard.NewGuardActivity;
import com.example.doormatt.admin.ui.resident.NewResidentActivity;
import com.example.doormatt.common.Common;
import com.example.doormatt.model.ResidentModel;
import com.example.doormatt.qrcode.QRCodeActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener{

    private DatabaseReference residentRef;
    private RecyclerView recyclerView;

    private Button newResidentButton, newGuardButton, newVisitorButton, readQRButton;
    private EditText searchBar;

    private String residentId;

    FirebaseRecyclerOptions<ResidentModel> options;
    FirebaseRecyclerAdapter<ResidentModel, ResidentsViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        initViews();
        residentRef = FirebaseDatabase.getInstance().getReference(Common.RESIDENT_REF);

        loadData("");

        newResidentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, NewResidentActivity.class);
                startActivity(intent);
            }
        });

        newGuardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, NewGuardActivity.class);
                startActivity(intent);
            }
        });

        readQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, QRCodeActivity.class);
                startActivity(intent);
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString() != null) {
                    loadData(editable.toString());
                } else {
                    loadData("");
                }
            }
        });
    }

    private void initViews() {
        newResidentButton = findViewById(R.id.new_resident_button);
        newGuardButton = findViewById(R.id.new_guard_button);
//        newVisitorButton = findViewById(R.id.new)
        readQRButton = findViewById(R.id.read_qr_button);
        searchBar = findViewById(R.id.admin_resident_search_bar);

        recyclerView = findViewById(R.id.admin_resident_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.new_resident_button:
                startActivity(new Intent(AdminActivity.this, NewResidentActivity.class));
                break;
            case R.id.new_guard_button:
                startActivity(new Intent(AdminActivity.this, NewGuardActivity.class));
                break;
            case R.id.read_qr_button:
                startActivity(new Intent(AdminActivity.this, QRCodeActivity.class));
                break;
        }
    }

    private void loadData(String data) {
        Query query = residentRef.orderByChild("firstName").startAt(data).endAt(data + "\uf8ff");

        options = new FirebaseRecyclerOptions.Builder<ResidentModel>(   )
                .setQuery(query, ResidentModel.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<ResidentModel, ResidentsViewHolder>(options) {
            @NonNull
            @Override
            public ResidentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_admin_residents, parent, false);
                return new ResidentsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final ResidentsViewHolder residentsViewHolder, final int i, @NonNull ResidentModel residentModel) {
                final String myResidentName, myResidentRoomNumber;

                myResidentName = residentModel.getFirstName() + " " + residentModel.getLastName();
                myResidentRoomNumber = residentModel.getRoomNumber();

                residentsViewHolder.residentName.setText("Name: " + myResidentName);
                residentsViewHolder.residentRoomNumber.setText("Room Number: " + myResidentRoomNumber);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }



    @Override
    protected void onStart() {
        adapter.startListening();
        super.onStart();
    }

    @Override
    protected void onStop() {
        adapter.stopListening();
        super.onStop();
    }
}