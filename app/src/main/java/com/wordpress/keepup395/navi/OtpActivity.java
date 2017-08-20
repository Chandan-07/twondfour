package com.wordpress.keepup395.navi;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OtpActivity extends AppCompatActivity {

    public int seconds = 60;
    public int minutes = 19;
    TextView tvMessage;
    EditText etOTP;
    Button btnBook;
    TextView timer;
    String message = "Here you will receive a call from us for the confirmation of your booking.\n\nPlease don't close this page.";
    String user;
    String email;
    String phone;
    String startdate;
    String enddate;
    String cost;
    String image2;
    String title;
    String decodestart;
    String decodeend;
    String bikeid;
    String checkOTP;
    String sendOTP;
    String SERVER_URL = "http://www.twondfour.com/fetch/prebook.php";
    String checkEmail;
    int flag = 0;
    String key;
    String tosendOTP;
    DatabaseReference mref;
    int otpnumber;

    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        tvMessage = (TextView) findViewById(R.id.tvMeassage);
        etOTP = (EditText) findViewById(R.id.etOTP);
        btnBook = (Button) findViewById(R.id.btnBook);
        timer = (TextView) findViewById(R.id.timer);

        new CountDownTimer(1200 * 1000 + 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                timer.setText(String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }

            public void onFinish() {
                timer.setText("Completed");
                AlertDialog.Builder ab = new AlertDialog.Builder(OtpActivity.this);
                ab.setTitle("Ooops!!!");
                ab.setMessage("You can't book this ride anymore.\nSorry");
                ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(OtpActivity.this, Booknow.class);
                        i.putExtra("email", email);
                        startActivity(i);
                    }
                });
                ab.setCancelable(false);
                ab.show();
            }
        }.start();

        Random r = new Random();
        otpnumber = r.nextInt(99999 - 11111) + 11111;
        sendOTP = "" + otpnumber;

        /*UUID uuid = UUID.randomUUID();
        tosendOTP = uuid.toString().replaceAll("-", "");
        sendOTP = tosendOTP.substring(0,5);*/

        mref = FirebaseDatabase.getInstance().getReference("data");

        Bundle b = getIntent().getExtras();
        user = b.getString("user");
        email = b.getString("email");
        // final String password=b.getString("password");
        phone = b.getString("number");
        startdate = b.getString("starttime");
        enddate = b.getString("endtime");
        cost = b.getString("cost");
        image2 = b.getString("image");
        title = b.getString("title");

        decodestart = b.getString("decodestart");
        decodeend = b.getString("decodeend");
        bikeid = b.getString("id");

        new PreBook().execute();

        tvMessage.setText(message);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oTP = etOTP.getText().toString().trim();
                if (!TextUtils.isEmpty(oTP)) {
                    //new FetchOTP().execute();
                    if (sendOTP.equals(oTP)) {
                        mref.child(key).removeValue();
                        Intent in = new Intent(OtpActivity.this, Confirm.class);
                        in.putExtra("user", user);
                        in.putExtra("email", email);
                        //in.putExtra("password",password);
                        in.putExtra("number", phone);
                        in.putExtra("id", bikeid);
                        in.putExtra("cost", cost);
                        in.putExtra("image", image2);
                        in.putExtra("title", title);
                        in.putExtra("decodestart", decodestart);
                        in.putExtra("decodeend", decodeend);
                        in.putExtra("starttime", startdate);
                        in.putExtra("endtime", enddate);
                        startActivity(in);
                        finish();
                    } else {
                        AlertDialog.Builder ab = new AlertDialog.Builder(OtpActivity.this);
                        ab.setTitle("Oops! Something went wrong");
                        ab.setMessage("You have entered wrong OTP");
                        ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        ab.show();
                    }
                } else {
                    Toast.makeText(OtpActivity.this, "Some Fields are Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        if (flag == 0) {
            new MultipleBikeCheck().execute();
        }
        if (flag == 1) {
            super.onBackPressed();
        }
    }

    class MultipleBikeCheck extends AsyncTask<String, Void, Boolean> {
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            loading = ProgressDialog.show(OtpActivity.this, "Loading...", "Please wait...", true, true);
            super.onPreExecute();
            checkEmail = "";
        }

        @Override
        protected Boolean doInBackground(String... params) {
            if (Looper.myLooper() == null) {
                Looper.prepare();
            }
            try {
                String url = "http://www.twondfour.com/fetch/multiplebikecheck.php?email=" + email;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(url));
                HttpResponse response = client.execute(request);
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    JSONObject object = jsonArray.getJSONObject(0);
                    //url = new URL(object.getString("image"));
                    //bitmaps[i]= BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    checkEmail = object.getString("email_id");
                }
            } catch (Exception e) {
                Toast.makeText(OtpActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            loading.dismiss();
            //Toast.makeText(OtpActivity.this,""+checkEmail,Toast.LENGTH_LONG).show();
            //etOTP.setText(""+checkEmail);
            if (email.equals(checkEmail)) {
                //Toast.makeText(OtpActivity.this,"Wait for our call\nYour booking is still pending",Toast.LENGTH_LONG).show();
                AlertDialog.Builder ab = new AlertDialog.Builder(OtpActivity.this);
                ab.setTitle("Ooops!");
                ab.setMessage("Wait for our call\nYour booking is still pending");
                ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                ab.show();
            } else {
                flag = 1;
                onBackPressed();
            }
        }
    }

    class PreBook extends AsyncTask<String, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            new RemoveRideCompleteUser().execute();

            //etOTP.setText(""+idChild);
            //key = idChild;
            //Toast.makeText(OtpActivity.this,""+send,Toast.LENGTH_LONG).show();
        }

        @Override
        protected Boolean doInBackground(String... strings) {

            if (Looper.myLooper() == null) {
                Looper.prepare();
            }
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("cost", cost));
            nameValuePairs.add(new BasicNameValuePair("decode_start_date", decodestart));
            nameValuePairs.add(new BasicNameValuePair("decode_end_date", decodeend));
            nameValuePairs.add(new BasicNameValuePair("bike_id", bikeid));
            nameValuePairs.add(new BasicNameValuePair("otp", sendOTP));
            nameValuePairs.add(new BasicNameValuePair("phone", phone));
            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(SERVER_URL);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = client.execute(httpPost);
                HttpEntity entity = response.getEntity();
                Log.e("pass 1", "connection success");
            } catch (Exception e) {
                Toast.makeText(OtpActivity.this, "Error" + e, Toast.LENGTH_SHORT).show();
            }
            return null;
        }
    }

    class RemoveRideCompleteUser extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            String idChild = mref.push().getKey();
            Dataentry data = new Dataentry(idChild, title, user, phone, startdate, enddate, cost, sendOTP, id);
            mref.child(idChild).setValue(data).isSuccessful();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            if (Looper.myLooper() == null) {
                Looper.prepare();
            }
            try {
                String url = "http://www.twondfour.com/fetch/multiplebikecheck.php?email=" + email;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(url));
                HttpResponse response = client.execute(request);
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    JSONObject object = jsonArray.getJSONObject(0);
                    //url = new URL(object.getString("image"));
                    //bitmaps[i]= BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    //checkEmail=object.getString("email_id");
                    id = object.getString("id");
                    //checkenddate = Long.parseLong(object.getString("enddate"));
                }
            } catch (Exception e) {
                Toast.makeText(OtpActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
            return null;
        }
    }
}