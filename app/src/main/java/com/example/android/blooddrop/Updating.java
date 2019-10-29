package com.example.android.blooddrop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.blooddrop.Model.DataDonor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Updating extends AppCompatActivity {
    EditText editTextIdDelte, editTextName, editTextPhone, editTextAddress;
    Spinner spinnerBlood;
    Button updateBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_and_delete);
        editTextIdDelte = (EditText) findViewById(R.id.editTextIdDelete);
        editTextName = (EditText) findViewById(R.id.updateName);
        editTextPhone = (EditText) findViewById(R.id.updatePhone);
        spinnerBlood = (Spinner) findViewById(R.id.UpdateType);
        editTextAddress = (EditText) findViewById(R.id.updateAddress);
        updateBTN = (Button) findViewById(R.id.update);
        updateBTN.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String id = editTextIdDelte.getText().toString().trim();
                String name = editTextName.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();
                String blood = spinnerBlood.getSelectedItem().toString().trim();
                String address = editTextAddress.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    editTextName.setError(getString(R.string.nameRequired));
                    return;
                } else if (TextUtils.isEmpty(phone)) {
                    editTextPhone.setError(getString(R.string.phoneIsRequared));
                    return;
                } else if (TextUtils.isEmpty(blood)) {
                    editTextName.setError(getString(R.string.bloodIsRewquared));
                    return;

                } else {

                    updateDonor(id, name, phone, address, blood);

                    // alertDialog.dismiss();
                }
            }

        });
    }

    private boolean updateDonor(final String DonorId, String name, String phone, String address, String blood) {

        final String idDelete = editTextIdDelte.getText().toString().trim();
        final DatabaseReference databaseDonor;
        databaseDonor = FirebaseDatabase.getInstance().getReference("Donors").child(DonorId);
        final DataDonor dataDonor = new DataDonor(DonorId, name, phone, address, blood);
        // DataDonor dataDonor = new DataDonor(DonorId, name, phone, address, blood);

        databaseDonor.child(idDelete).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // TODO: handle the case where the data already exists
                    databaseDonor.child(idDelete).setValue(dataDonor);
                    Toast.makeText(Updating.this, R.string.DonorUpdatedSuccessfully, Toast.LENGTH_SHORT).show();

                } else {
                    // TODO: handle the case where the data does not yet exist
                    Toast.makeText(Updating.this, R.string.idNOTfound, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

        return true;

    }
}
