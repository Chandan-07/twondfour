package com.wordpress.keepup395.navi;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;

public class MyAccount extends AppCompatActivity {

    ListView lvBookedBikes;
    String email = "";
    String username = "";
    String phone = "";
    String gender = "";
    String birthday = "";
    TextView tvname;
    TextView tvemail;
    TextView tvphone;
    TextView tvgender;
    TextView tvbirthday;

    String[] startdate;
    String[] enddate;
    String[] cost;
    String[] bikename;
    String[] imageString;
    String[] decodestartdate;
    Bitmap[] image;

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        lvBookedBikes = (ListView) findViewById(R.id.lvBookedBikes);
        tvname = (TextView) findViewById(R.id.tvname);
        tvemail = (TextView) findViewById(R.id.tvemail);
        tvphone = (TextView) findViewById(R.id.tvphone);
        //tvgender = (TextView)findViewById(R.id.tvgender);
        //tvbirthday = (TextView)findViewById(R.id.tvbirthday);

        Bundle b = getIntent().getExtras();
        email = b.getString("email");

        String link = "http://www.twondfour.com/fetch/getUserDetails.php?email=" + email;
        new GetUrls().execute(link);
    }

    class GetBookedUserDetails extends AsyncTask<String, Void, Boolean> {

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
                    startdate = new String[jsonArray.length()];
                    enddate = new String[jsonArray.length()];
                    cost = new String[jsonArray.length()];
                    bikename = new String[jsonArray.length()];
                    //image = new Bitmap[jsonArray.length()];
                    decodestartdate = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        //url = new URL(object.getString("image"));
                        //bitmaps[i]= BitmapFactory.decodeStream(url.openConnection().getInputStream());

                        bikename[i] = object.getString("bike_name");
                        cost[i] = object.getString("cost");
                        startdate[i] = object.getString("start_date_time");
                        enddate[i] = object.getString("end_date_time");
                        decodestartdate[i] = object.getString("decode_start_date");
                    }

                }
                return false;
            } catch (Exception e) {
                Toast.makeText(MyAccount.this, "Error", Toast.LENGTH_LONG).show();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            YourRide yourRide = new YourRide(MyAccount.this, bikename, cost, startdate, enddate, decodestartdate);
            lvBookedBikes.setAdapter(yourRide);
            loading.dismiss();
        }
    }

    class GetUrls extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(MyAccount.this, "Loading...", "Please wait...", true, true);
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
                    //birthday = object.getString("birthday");

                }
                return false;
            } catch (Exception e) {
                Toast.makeText(MyAccount.this, "Error", Toast.LENGTH_LONG).show();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aboolean) {
            super.onPostExecute(aboolean);
            tvname.setText("User : " + username);
            tvemail.setText("Email : " + email);
            tvphone.setText("Phone : " + phone);
            //tvgender.setText("Gender : "+gender);
            //tvbirthday.setText("Birthday : "+birthday);
            String link = "http://www.twondfour.com/fetch/getBookedUserDetails.php?email=" + email;
            new GetBookedUserDetails().execute(link);
        }
    }
}
