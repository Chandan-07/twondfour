package com.wordpress.keepup395.navi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class SignUp extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText etEmail;
    EditText etPassword;
    String email = "";
    String ssword = "";
    String phone = "";
    String gender = "";
    String birthday = "";
    String username = "";
    ProgressDialog progressDialog;
    //private String SERVER_URL="http://www.twondfour.com/demo.php";
    private String SERVER_URL = "http://www.twondfour.com/fetch/userdetail.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        mAuth = FirebaseAuth.getInstance();

        etEmail = (EditText) findViewById(R.id.etEmailSignin);
        etPassword = (EditText) findViewById(R.id.etpassword);
        final EditText user = (EditText) findViewById(R.id.Username);
        final EditText mobile = (EditText) findViewById(R.id.mobile);
        //final EditText gendere = (EditText)findViewById(R.id.gender);
        //final EditText birthdays = (EditText)findViewById(R.id.birthday);

        //Button btnRegister = (Button) findViewById(R.id.btnRegister);
        progressDialog = new ProgressDialog(SignUp.this);
        //final FirebaseAuth mAuth = FirebaseAuth.getInstance();


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString().trim();
                phone = mobile.getText().toString().trim();
                //gender=gendere.getText().toString().trim();
                //birthday=birthdays.getText().toString().trim();
                username = user.getText().toString().trim();

                ssword = etPassword.getText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(ssword) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(username)) {
                    if (phone.length() == 10) {
                        saveToDatabase();
                    } else {
                        Toast.makeText(SignUp.this, "Enter a valid phone number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUp.this, "Some fields are empty", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void saveToDatabase() {
        progressDialog.setMessage("Registering...");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, ssword).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    mAuth.signOut();
                                    sendP sendp = new sendP();
                                    sendp.execute(username, email, phone, gender, birthday);
                                }
                            }
                        });
                    }
                } else {
                    try {
                        throw task.getException();
                    } catch (Exception e) {
                        Toast.makeText(SignUp.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                }
            }
        });
    }

    public class sendP extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String paramName = strings[0];
            String paramEmail = strings[1];
            String parammobile = strings[2];
            String paramgender = strings[3];
            String paramBirtday = strings[4];
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("username", paramName));
            nameValuePairs.add(new BasicNameValuePair("email", paramEmail));
            nameValuePairs.add(new BasicNameValuePair("phone", parammobile));
            nameValuePairs.add(new BasicNameValuePair("gender", paramgender));
            nameValuePairs.add(new BasicNameValuePair("birthday", paramBirtday));
            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(SERVER_URL);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = client.execute(httpPost);
                HttpEntity entity = response.getEntity();

                Log.e("pass 1", "connection success");
            } catch (Exception e) {
                Toast.makeText(SignUp.this, "Error" + e, Toast.LENGTH_SHORT).show();
            }
            return "success";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(SignUp.this, "Check your Email and verify it", Toast.LENGTH_LONG).show();
            Intent i = new Intent(SignUp.this, SignIn.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            progressDialog.dismiss();
            finish();
            Log.d("SERVER_URL", SERVER_URL);
        }
    }
}