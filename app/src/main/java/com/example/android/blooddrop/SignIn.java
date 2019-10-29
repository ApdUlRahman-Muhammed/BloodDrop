package com.example.android.blooddrop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;

public class SignIn extends AppCompatActivity {
    EditText edEmail, edPassword, edName;
    RadioButton male, female;
    ProgressDialog progressDoalog;
    Button submit;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        progressDoalog = new ProgressDialog(SignIn.this);
        mAuth = FirebaseAuth.getInstance();
        edEmail = (EditText) findViewById(R.id.editTextEmail);
        edPassword = (EditText) findViewById(R.id.editTextPassword);
        edName = (EditText) findViewById(R.id.editTextName);
        male = (RadioButton) findViewById(R.id.radioButtonMale);
        female = (RadioButton) findViewById(R.id.radioButtonFemale);
        submit = (Button) findViewById(R.id.RegisterBtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
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
            edName.setError(getString(R.string.nameRequired));
            edName.requestFocus();
            return;
        }
        if (password.length() < 6) {
            edPassword.setError(getString(R.string.passwordCondition));
            edPassword.requestFocus();
            return;

        } else {
            progressDoalog = ProgressDialog.show(SignIn.this, "", getString(R.string.plzWait), true);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(SignIn.this, R.string.userRegSuccessfully, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignIn.this, MainActivity.class);
                                startActivity(intent);
                                progressDoalog.dismiss();

                            } else {
                                if (task.getException() instanceof FirebaseAuthActionCodeException) {
                                    Toast.makeText(SignIn.this, R.string.userAlreadyIn, Toast.LENGTH_SHORT).show();

                                } else {

                                    Toast.makeText(SignIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }
                                progressDoalog.dismiss();

                            }


                        }
                    });
        }
    }
}
