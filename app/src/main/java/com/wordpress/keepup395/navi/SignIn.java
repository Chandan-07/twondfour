package com.wordpress.keepup395.navi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        final EditText etEmail = (EditText) findViewById(R.id.etEmailSignin);
        final EditText etPassword = (EditText) findViewById(R.id.etPasswordSignIn);
        Button btnSignin = (Button) findViewById(R.id.btnSignin);
        Button btnSignup = (Button) findViewById(R.id.btnSignup);
        TextView forgotp = (TextView) findViewById(R.id.forgot);
        ImageView imageView = (ImageView) findViewById(R.id.account);
        final ProgressBar progressBar = new ProgressBar(this);

        final ProgressDialog progressDialog = new ProgressDialog(this);

        Picasso.with(getApplicationContext()).load(R.drawable.account).into(imageView);

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etEmail.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    progressDialog.setTitle("Signing in...");
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(SignIn.this, Booknow.class);
                                i.putExtra("email", email);
                                i.putExtra("password", password);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                finish();
                                progressDialog.dismiss();
                            } else {
                                try {
                                    throw task.getException();
                                } catch (Exception e) {
                                    Toast.makeText(SignIn.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                                progressDialog.dismiss();
                            }
                        }
                    });
                } else {
                    Toast.makeText(SignIn.this, "Some fields are empty", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignIn.this, SignUp.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        forgotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailt = etEmail.getText().toString().trim();

                if (TextUtils.isEmpty(emailt)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_LONG).show();
                    return;
                }


                mAuth.sendPasswordResetEmail(emailt)

                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignIn.this, "We have sent you instructions to reset your password!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(SignIn.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });

        if (auth != null) {
            Intent k = new Intent(SignIn.this, Booknow.class);
            String email = auth.getEmail().toString();
            k.putExtra("email", email);
            k.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(k);
        }
    }
}