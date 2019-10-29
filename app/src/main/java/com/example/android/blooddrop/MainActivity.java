package com.example.android.blooddrop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail, editTextPassword;
    TextView textViewPassword;
    ProgressDialog progressDoalog;
    EditText edEmail;
    EditText edPassword;
    EditText edName;
    RadioButton male;
    RadioButton female;
    Button register;
    private FirebaseAuth Auth;
    //tablet
    private boolean mTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Auth = FirebaseAuth.getInstance();
        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDoalog.setMax(100);
        edEmail = (EditText) findViewById(R.id.editTextEmail);
        edPassword = (EditText) findViewById(R.id.editTextPassword);
        edName = (EditText) findViewById(R.id.editTextName);
        male = (RadioButton) findViewById(R.id.radioButtonMale);
        female = (RadioButton) findViewById(R.id.radioButtonFemale);
        register = (Button) findViewById(R.id.RegisterBtn);
        textViewPassword = (TextView) findViewById(R.id.forget);
        editTextEmail = (EditText) findViewById(R.id.IdEmail);
        editTextPassword = (EditText) findViewById(R.id.IdPassword);
        findViewById(R.id.IdAccount).setOnClickListener(this);
        findViewById(R.id.IDLogin).setOnClickListener(this);


        if (findViewById(R.id.mainActivityId) != null) {
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registerUser();
                }
            });
        }
        textViewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResetDialog();
            }
        });
    }

    private void userlogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError(getString(R.string.invalidEmail));
            editTextEmail.requestFocus();

            return;
        }
        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.EmailRequired));
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError(getString(R.string.PasswordRequired));
            editTextPassword.requestFocus();
            return;
        } else {
            progressDoalog = ProgressDialog.show(MainActivity.this, "", getString(R.string.plzWait), true);
            Auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                if (findViewById(R.id.mainActivityId) != null) {
                                    mTablet = true;
                                    showDialog();
                                } else if (findViewById(R.id.mainActivityPhone) != null) {
                                    Intent intent = new Intent(MainActivity.this, Profile.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    mTablet = false;
                                }
                                progressDoalog.dismiss();
                            } else {
                                //    progressBar.setVisibility(View.GONE);
                                progressDoalog.dismiss();
                                Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IdAccount:
                Intent intent = new Intent(MainActivity.this, SignIn.class);
                startActivity(intent);
                break;
            case R.id.IDLogin:
                //
                userlogin();

                break;
        }


    }

    // method to show the dialog of reset password
    public void showResetDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.reset_passwors_dialog, null);
        dialogBuilder.setView(dialogView);
        final EditText editTextIdReset = (EditText) dialogView.findViewById(R.id.edittextReset);

        final Button ResetBt = (Button) dialogView.findViewById(R.id.Reset);
        dialogBuilder.setTitle(R.string.resetPassword);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        ResetBt.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                final String Email = editTextIdReset.getText().toString().trim();
                if (Email.isEmpty()) {
                    editTextEmail.setError(getString(R.string.ResetEmail));
                    editTextEmail.requestFocus();
                    return;
                }
                Auth.sendPasswordResetEmail(Email)

                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(MainActivity.this, R.string.checkYourEmail, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, R.string.failedToReset, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }

    public void showDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.patient_donor_dialog, null);
        dialogBuilder.setView(dialogView);

        Button donor = (Button) dialogView.findViewById(R.id.donorId);
        Button patient = (Button) dialogView.findViewById(R.id.patientId);
        dialogBuilder.setTitle(R.string.showDialogTitle);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DonationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Patient.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void registerUser() {
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        String name = edName.getText().toString().trim();
        String Male = male.getText().toString().trim();
        String Female = female.getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edEmail.setError(getString(R.string.invalidEmail));
            edEmail.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            edEmail.setError(getString(R.string.EmailRequired));
            edEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            edPassword.setError(getString(R.string.PasswordRequired));
            edPassword.requestFocus();
            return;
        }
        if (name.isEmpty()) {
            edPassword.setError(getString(R.string.nameRequired));
            edPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            edPassword.setError(getString(R.string.passwordCondition));
            edPassword.requestFocus();
            return;

        }

        Auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(MainActivity.this, R.string.userRegSuccessfully, Toast.LENGTH_SHORT).show();

                        } else {
                            if (task.getException() instanceof FirebaseAuthActionCodeException) {
                                Toast.makeText(MainActivity.this, R.string.userAlreadyIn, Toast.LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }


                        }

                    }
                });
    }
}
