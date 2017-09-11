package com.wordpress.keepup395.navi;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.util.Calendar;

public class finalobook extends AppCompatActivity {

    String email = "";
    String password = "";
    String phone = "";
    String username = "";
    String startDateTime = "";
    String endDateTime = "";
    String ustart = "";
    String uend = "";
    String title = "";
    String desc = "";
    String bikeno = "";
    String image = "";
    String gender = "";
    String cost = "";
    String id = "";
    String bikeId = "";
    TextView phonetext;
    TextView usernametext;
    String[] bikeid;
    String checkEmail = "";
    String text = "Make sure, you agree the terms & conditions of 2 &four ";
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalobook);

        TextView tvTitle = (TextView) findViewById(R.id.bikeTitle);
        TextView tvDesc = (TextView) findViewById(R.id.bikeDesc);
        ImageView ivBike = (ImageView) findViewById(R.id.bikeimage);
        usernametext = (TextView) findViewById(R.id.username);
        TextView emailtext = (TextView) findViewById(R.id.email);
        phonetext = (TextView) findViewById(R.id.phone);
        TextView startdatetext = (TextView) findViewById(R.id.startdatetime);
        TextView enddatetext = (TextView) findViewById(R.id.enddatetime);
        TextView costtext = (TextView) findViewById(R.id.tvcost);
        TextView terms = (TextView) findViewById(R.id.terms);

        Bundle b = getIntent().getExtras();
        title = b.getString("title");
        desc = b.getString("desc");
        bikeno = b.getString("bikeno");
        image = b.getString("image");
        startDateTime = b.getString("btnstart");
        endDateTime = b.getString("btnend");
        ustart = b.getString("ustart");
        uend = b.getString("uend");
        password = b.getString("password");
        email = b.getString("email");
        cost = b.getString("cost");
        id = b.getString("id");

        //Bitmap bitmap = StringToBitMap(image);
        //ivBike.setImageBitmap(Bitmap.createScaledBitmap(bitmap,300,300,false));
        Picasso.with(finalobook.this).load(image).into(ivBike);
        tvTitle.setText(title);
        terms.setText(text);
        tvDesc.setText(desc + " cc");
        emailtext.setText("Email : " + email);
        startdatetext.setText("Start Date and Time : " + startDateTime);
        enddatetext.setText("End Date and Time : " + endDateTime);
        costtext.setText("Rs : " + cost + "/-");

        Button btnConfirm = (Button) findViewById(R.id.btnconfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minutes = cal.get(Calendar.MINUTE);
                if (hour >= 9 && hour <= 22) {
                    if (hour == 22) {
                        AlertDialog.Builder ab = new AlertDialog.Builder(finalobook.this);
                        ab.setTitle("Attention!!!");
                        ab.setMessage("Booking hour is from 9:00 am to 10:00 pm");
                        ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        ab.show();
                    } else {
                        AlertDialog.Builder ab = new AlertDialog.Builder(finalobook.this);
                        ab.setTitle("Attention");
                        ab.setMessage("Please check your Start and End dates before proceeding further\n\nAre you sure you want to proceed?");
                        ab.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                check();
                            }
                        });
                        ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        ab.show();
                    }
                } else {
                    AlertDialog.Builder ab = new AlertDialog.Builder(finalobook.this);
                    ab.setTitle("Attention!!!");
                    ab.setMessage("Booking hour is from 9:00 am to 10:00 pm");
                    ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    ab.show();
                }

            }
        });
        String link = "http://www.twondfour.com/fetch/getUserDetails.php?email=" + email;
        new FetchData().execute(link);
    }

    private void check() {
        if (checkEmail.equals(email)) {
            AlertDialog.Builder ab = new AlertDialog.Builder(finalobook.this);
            ab.setTitle("Ooops!");
            ab.setMessage("You have already booked one ride\nYou can't book another one");
            ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            ab.show();
        } else {
            String link2 = "http://www.twondfour.com/fetch/finalbookcheck.php?startdate=" + ustart + "&enddate=" + uend;
            new GetBikeCheck().execute(link2);
        }
    }

    class FetchData extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(finalobook.this, "Loading...", "Please wait...", true, true);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            if (Looper.myLooper() == null) {
                Looper.prepare();
            }
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(params[0]));
                HttpResponse response = client.execute(request);
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    JSONObject object = jsonArray.getJSONObject(0);
                    username = object.getString("username");
                    phone = object.getString("phone");
                    //gender = object.getString("gender");
                }
                return false;
            } catch (Exception e) {
                Toast.makeText(finalobook.this, "Error", Toast.LENGTH_LONG).show();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            usernametext.setText("User Name : " + username);
            phonetext.setText("Phone no. : " + phone);
            new MultipleBikeCheck().execute();
        }
    }

    class GetBikeCheck extends AsyncTask<String, Void, Boolean> {

        private ProgressDialog loading2;
        private int flag = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading2 = ProgressDialog.show(finalobook.this, "Loading...", "Please wait...", true, true);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (flag == 0) {
                AlertDialog.Builder ab = new AlertDialog.Builder(finalobook.this);
                ab.setTitle("Ooops!");
                ab.setMessage("Bike is no more available\nPlease try booking again");
                ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent in2 = new Intent(finalobook.this, Booknow.class);
                        in2.putExtra("email", email);
                        in2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(in2);
                        finish();
                    }
                });
                ab.show();
                loading2.dismiss();
            }
            loading2.dismiss();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            if (Looper.myLooper() == null) {
                Looper.prepare();
            }
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(params[0]));
                HttpResponse response = client.execute(request);
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    bikeid = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        bikeid[i] = object.getString("id");
                        if (bikeid[i].equals(id)) {
                            flag = 1;
                            bikeId = bikeid[i];
                            Intent in = new Intent(finalobook.this, OtpActivity.class);
                            in.putExtra("user", username);
                            in.putExtra("email", email);
                            in.putExtra("password", password);
                            in.putExtra("number", phone);
                            in.putExtra("id", bikeId);
                            in.putExtra("cost", cost);
                            in.putExtra("image", image);
                            in.putExtra("title", title);
                            in.putExtra("decodestart", ustart);
                            in.putExtra("decodeend", uend);
                            in.putExtra("starttime", startDateTime);
                            in.putExtra("endtime", endDateTime);
                            startActivity(in);
                            loading2.dismiss();
                        }
                    }
                }
                return false;
            } catch (Exception e) {
                Toast.makeText(finalobook.this, "Error", Toast.LENGTH_LONG).show();
            }
            return false;
        }
    }

    class MultipleBikeCheck extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            loading.dismiss();
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
                Toast.makeText(finalobook.this, "Error", Toast.LENGTH_LONG).show();
            }
            return null;
        }
    }
}