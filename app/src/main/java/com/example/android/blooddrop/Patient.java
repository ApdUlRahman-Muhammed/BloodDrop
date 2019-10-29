package com.example.android.blooddrop;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.blooddrop.Fragments.DonorsFragment;
import com.example.android.blooddrop.Model.DataDonor;
import com.example.android.blooddrop.widget.WidgetUpdateService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Patient extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    DonorsFragment donorsfragment;
    List<DataDonor> donorsModelsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        donorsModelsArrayList = new ArrayList<>();
        donorsfragment = new DonorsFragment();
        fragmentManager.beginTransaction().replace(R.id.donors_container, donorsfragment).commit();
        donorsModelsArrayList.clear();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Donors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DataDonor usersModels = new DataDonor(
                            snapshot.child("donorId").getValue().toString(),
                            snapshot.child("donorName").getValue().toString(),
                            snapshot.child("donorPhone").getValue().toString(),
                            snapshot.child("donorAddress").getValue().toString(),
                            snapshot.child("donorBloodType").getValue().toString()
                    );
                    donorsModelsArrayList.add(usersModels);
                }
                updateWidgetScreen(donorsModelsArrayList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        Toast.makeText(getApplicationContext(), R.string.AddedTowidget, Toast.LENGTH_LONG).show();
    }


    private void updateWidgetScreen(List<DataDonor> donorsModelsList) {
        String users = getString(R.string.users_list_name);

        for (DataDonor user : donorsModelsList) {

            users = users
                    + user.getDonorName() + ": "
                    + user.getDonorBloodType() + " \n";
        }
        WidgetUpdateService.startWidgetUpdate(this, users);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_patient, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.signout) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        if (itemThatWasClickedId == R.id.googlemap) {
            Intent intent = new Intent(this, GoogleMaps.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
