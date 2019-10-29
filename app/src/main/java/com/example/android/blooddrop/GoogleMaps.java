package com.example.android.blooddrop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.example.android.blooddrop.Model.DataDonor;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GoogleMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        DatabaseReference databaseDonor;
        databaseDonor = FirebaseDatabase.getInstance().getReference("Donors");
        final DataDonor dataDonor = new DataDonor();
        mMap = googleMap;
        databaseDonor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ArrayList<DataDonor> donors = new ArrayList<>();
                    DataDonor value = dataSnapshot1.getValue(DataDonor.class);
                    String address = value.getDonorAddress();
                    dataDonor.setDonorAddress(address);
                    donors.add(dataDonor);
                    // DataDonor dataDonor = new DataDonor();
                    // Add a marker in Sydney and move the camera
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    LatLng sydney = null;
                    for (int i = 0; i < donors.size(); i++) {
                        if (address.equals(getString(R.string.Cairo))) {
                            sydney = new LatLng(30.044281, 31.340002);
                        } else if (address.equals(getString(R.string.Alex))) {
                            sydney = new LatLng(31.205753, 29.924526);
                        } else if (address.equals(getString(R.string.Giza))) {
                            sydney = new LatLng(30.013056, 31.208853);
                        } else if (address.equals(getString(R.string.portSaid))) {
                            sydney = new LatLng(31.265289, 32.301866);
                        } else if (address.equals(getString(R.string.sues))) {
                            sydney = new LatLng(29.97371, 32.52627);
                        } else if (address.equals(getString(R.string.luxor))) {
                            sydney = new LatLng(25.687243, 32.639637);
                        } else if (address.equals(getString(R.string.Asyut))) {
                            sydney = new LatLng(27.180134, 31.189283);
                        } else if (address.equals(getString(R.string.Aswan))) {
                            sydney = new LatLng(24.09082, 32.89942);
                        } else if (address.equals(getString(R.string.Ismailia))) {
                            sydney = new LatLng(30.60427, 32.27225);
                        } else if (address.equals(getString(R.string.Damietta))) {
                            sydney = new LatLng(31.814444, 31.417540);
                        } else if (address.equals(getString(R.string.Minya))) {
                            sydney = new LatLng(28.10988, 30.7503);
                        } else if (address.equals(getString(R.string.BeniSuef))) {
                            sydney = new LatLng(29.0661, 31.0994);
                        } else if (address.equals(getString(R.string.Qena))) {
                            sydney = new LatLng(26.155061, 32.716012);
                        } else if (address.equals(getString(R.string.Sohag))) {
                            sydney = new LatLng(26.55695, 31.69478);
                        } else if (address.equals(getString(R.string.KafrelSheikh))) {
                            sydney = new LatLng(31.1063, 30.942);
                        } else if (address.equals(getString(R.string.Arish))) {
                            sydney = new LatLng(31.13159, 33.79844);
                        } else if (address.equals(getString(R.string.MarsaMatruh))) {
                            sydney = new LatLng(31.354343, 27.237316);
                        } else if (address.equals(getString(R.string.Hurghada))) {
                            sydney = new LatLng(27.25738, 33.81291);
                        } else if (address.equals(getString(R.string.SharmElSheikh))) {
                            sydney = new LatLng(27.91582, 34.32995);
                        } else if (address.equals(getString(R.string.Monufia))) {
                            sydney = new LatLng(30.5605, 31.0079);
                        } else if (address.equals(getString(R.string.Sharqia))) {
                            sydney = new LatLng(30.7327, 31.7195);
                        } else if (address.equals(getString(R.string.Gharbia))) {
                            sydney = new LatLng(30.786509, 31.000376);
                        }
                        mMap.addMarker(new MarkerOptions().position(sydney).title(value.getDonorBloodType()));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
