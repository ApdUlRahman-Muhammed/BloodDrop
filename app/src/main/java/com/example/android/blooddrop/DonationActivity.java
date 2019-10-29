package com.example.android.blooddrop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.blooddrop.Model.DataDonor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DonationActivity extends AppCompatActivity {
    public static Spinner bloodType;
    public static Spinner spinnerAddress;
    public static DataDonor dataDonor;
    public static String blood;
    EditText editTextName, editTextPhone, editTextId;
    Button AddBtn, UpdateBtn;
    DatabaseReference databaseDonor;
    EditText editTextIdDelete;
    ImageView imageViewMap;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_donor);

        back = (ImageView) findViewById(R.id.backarrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        spinnerAddress = (Spinner) findViewById(R.id.spinnerAddress);
        bloodType = (Spinner) findViewById(R.id.editTextType);
        editTextId = (EditText) findViewById(R.id.editTextId);
        UpdateBtn = (Button) findViewById(R.id.updateBtn);
        AddBtn = (Button) findViewById(R.id.AddBtn);
        editTextIdDelete = (EditText) findViewById(R.id.editTextIdDelete);
        imageViewMap = (ImageView) findViewById(R.id.googleMapId);
        //  createWidget();
        imageViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonationActivity.this, GoogleMaps.class);
                startActivity(intent);
            }
        });
        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDonor();
            }
        });

        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showUpdateDialog();
            }
        });

    }

    public boolean updateDonor(final String DonorId, String name, String phone, String address, String blood) {

        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        EditText editTextIdDelte = (EditText) dialogView.findViewById(R.id.editTextIdDelete);
        final String idDelete = editTextIdDelte.getText().toString().trim();

        databaseDonor = FirebaseDatabase.getInstance().getReference("Donors").child(DonorId);
        dataDonor = new DataDonor(DonorId, name, phone, address, blood);

        databaseDonor.child(idDelete).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // TODO: handle the case where the data already exists
                    databaseDonor.child(idDelete).setValue(dataDonor);
                    Toast.makeText(DonationActivity.this, R.string.updatedSuccessfully, Toast.LENGTH_SHORT).show();

                } else {
                    // TODO: handle the case where the data does not yet exist
                    Toast.makeText(DonationActivity.this, R.string.idNOTfound, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

        return true;

    }

    public void showUpdateDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);
        final EditText editTextIdDelte = (EditText) dialogView.findViewById(R.id.editTextIdDelete);
        final EditText editTextName = (EditText) dialogView.findViewById(R.id.updateName);
        final EditText editTextPhone = (EditText) dialogView.findViewById(R.id.updatePhone);
        final Spinner spinnerBlood = (Spinner) dialogView.findViewById(R.id.UpdateType);
        final Spinner spinnerAddress = (Spinner) dialogView.findViewById(R.id.spinnerAddress);
        final Button updateBTN = (Button) dialogView.findViewById(R.id.update);
        final Button deletebutton = (Button) dialogView.findViewById(R.id.Delete);

        dialogBuilder.setTitle("update  your data");
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String donorId = editTextIdDelte.getText().toString().trim();

                DeleteDonor(donorId);
                alertDialog.dismiss();
            }
        });
        updateBTN.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String DonorId = editTextIdDelte.getText().toString().trim();
                String name = editTextName.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();
                String blood = spinnerBlood.getSelectedItem().toString().trim();
                String address = spinnerAddress.getSelectedItem().toString().trim();


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

                    updateDonor(DonorId, name, phone, address, blood);

                    alertDialog.dismiss();
                }
            }

        });

    }

    private void AddDonor() {
        String DonorId = editTextId.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String address = spinnerAddress.getSelectedItem().toString().trim();
        blood = bloodType.getSelectedItem().toString().trim();


        if (DonorId.length() < 6) {
            editTextId.setError(getString(R.string.AddCondition));
            editTextId.requestFocus();
            return;
        } else if (TextUtils.isEmpty(name)) {
            editTextName.setError(getString(R.string.EnteryourName));
            editTextName.requestFocus();
            return;
        } else {
            databaseDonor = FirebaseDatabase.getInstance().getReference("Donors");
            dataDonor = new DataDonor(DonorId, name, phone, address, blood);
            databaseDonor.child(DonorId).setValue(dataDonor);

            Toast.makeText(DonationActivity.this, R.string.DonorAdded, Toast.LENGTH_SHORT).show();

        }
    }


    private void DeleteDonor(final String donorId) {
        databaseDonor = FirebaseDatabase.getInstance().getReference("Donors");
        //   Query applesQuery = ref.child("firebase-test").orderByChild("title").equalTo("Apple");

        databaseDonor.child(donorId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // TODO: handle the case where the data already exists

                    databaseDonor.child(donorId).removeValue();
                    Toast.makeText(DonationActivity.this, R.string.DeletedSuccessfully, Toast.LENGTH_SHORT).show();

                } else {
                    // TODO: handle the case where the data does not yet exist
                    Toast.makeText(DonationActivity.this, R.string.idNOTfound, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }


}
