package com.example.android.blooddrop.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.android.blooddrop.Adapter.DonorsAdapter;
import com.example.android.blooddrop.Model.DataDonor;
import com.example.android.blooddrop.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DonorsFragment extends Fragment implements DonorsAdapter.ListItemClickListener {


    public static DonorsAdapter donorsAdapter;
    public static ArrayList<DataDonor> donors = new ArrayList<>();
    RecyclerView rvDonors;
    EditText editTextSearch;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Donors");
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_donors, container, false);

        rvDonors = (RecyclerView) view.findViewById(R.id.rv_list_donors);

        editTextSearch = (EditText) view.findViewById(R.id.editTextSearch);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    DataDonor value = dataSnapshot1.getValue(DataDonor.class);
                    DataDonor fire = new DataDonor();
                    //updateWidgetScreen(donors, value);
                    String name = value.getDonorName();
                    String address = value.getDonorAddress();
                    String phone = value.getDonorPhone();
                    String blood = value.getDonorBloodType();
                    fire.setDonorName(name);
                    fire.setDonorAddress(address);
                    fire.setDonorPhone(phone);
                    fire.setDonorBloodType(blood);
                    donors.add(fire);

                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
                    rvDonors.setLayoutManager(layoutManager);


                    donorsAdapter = new DonorsAdapter(donors, getContext(), new DonorsAdapter.ListItemClickListener() {
                        @Override
                        public void onListItemClick(DataDonor clicked) {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            String phone = clicked.getDonorPhone();
                            callIntent.setData(Uri.parse("tel:" + phone));

                            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling

                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            startActivity(callIntent);
                        }

                    });
                    rvDonors.setAdapter(donorsAdapter);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // failed value
            }
        });
        // to add recycle view to widget

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString());
            }
        });
        return view;
    }

    private void filter(String text) {

        //new array list that will hold the filtered data
        ArrayList<DataDonor> filterdDonors = new ArrayList<>();

        //looping through existing elements
        for (DataDonor s : donors) {
            //if the existing elements contains the search input
            if (s.getDonorBloodType().contains(text)) {
                //adding the element to filtered list
                filterdDonors.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        // Adapter in not equal null
        if (donorsAdapter != null) {

            donorsAdapter.filterList(filterdDonors);
        }
    }

    @Override
    public void onListItemClick(DataDonor clicked) {

    }

}
