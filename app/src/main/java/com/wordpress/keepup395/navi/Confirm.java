package com.wordpress.keepup395.navi;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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

public class Confirm extends AppCompatActivity {


    private String SERVER_URL = "http://www.twondfour.com/fetch/bookuser.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        Bundle b = getIntent().getExtras();
        final String user = b.getString("user");
        final String email = b.getString("email");
        // final String password=b.getString("password");
        final String phone = b.getString("number");
        final String startdate = b.getString("starttime");
        final String enddate = b.getString("endtime");
        final String cost = b.getString("cost");
        final String image2 = b.getString("image");
        final String title = b.getString("title");

        final String decodestart = b.getString("decodestart");
        final String decodeend = b.getString("decodeend");
        final String bikeid = b.getString("id");

        final TextView tvtitle = (TextView) findViewById(R.id.title);
        final ImageView imageView = (ImageView) findViewById(R.id.image);
        final TextView username = (TextView) findViewById(R.id.username);
        final TextView subject = (TextView) findViewById(R.id.textView2);
        final TextView tvemail = (TextView) findViewById(R.id.email);
        final TextView tvphone = (TextView) findViewById(R.id.phone);
        final TextView tvstart = (TextView) findViewById(R.id.startdate);
        final TextView tvend = (TextView) findViewById(R.id.enddate);
        final TextView tvcost = (TextView) findViewById(R.id.cost);

        TextView tvcall = (TextView) findViewById(R.id.clickhere);

        //Bitmap bitmap = StringToBitMap(image2);
        //imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 300, 300, false));
        Picasso.with(Confirm.this).load(image2).into(imageView);
        tvtitle.setText(title);
        username.setText("Name : " + user);
        tvemail.setText("Email : " + email);
        tvphone.setText("Phone no. : " + phone);
        tvstart.setText("Start Date : " + startdate);
        tvend.setText("End Date : " + enddate);
        tvcost.setText("Cost : Rs " + cost + "/-");

        tvcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*try {
                    Intent call = new Intent(Intent.ACTION_CALL);
                    call.setData(Uri.parse("tel:+919777533892"));
                    call.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(call);
                }catch (ActivityNotFoundException e)
                {
                    Log.e("Calling a phone number", "call failed", e);
                }*/
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9777533892"));
                startActivity(intent);
            }
        });

        new GetUrl().execute(image2, title, user, email,/*password*/phone, startdate, enddate, cost, decodestart, decodeend, bikeid);
    }

    class GetUrl extends AsyncTask<String, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            Bundle b = getIntent().getExtras();
            final String user = b.getString("user");

            // final String password=b.getString("password");
            final String phone = b.getString("number");
            final String startdate = b.getString("starttime");
            final String enddate = b.getString("endtime");
            final String cost = b.getString("cost");

            final String title = b.getString("title");
            final String bikeid = b.getString("id");
        }

        @Override
        protected Boolean doInBackground(String... strings) {

            if (Looper.myLooper() == null) {
                Looper.prepare();
            }
            String image = strings[0];
            String title = strings[1];
            String user = strings[2];
            String email = strings[3];
            String phone = strings[4];
            String startdate = strings[5];
            String enddate = strings[6];
            String cost = strings[7];
            String decodestart = strings[8];
            String decodeend = strings[9];
            String bikeid = strings[10];
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("user_name", user));
            nameValuePairs.add(new BasicNameValuePair("email_id", email));
            nameValuePairs.add(new BasicNameValuePair("phone_no", phone));
            nameValuePairs.add(new BasicNameValuePair("start_date_time", startdate));
            nameValuePairs.add(new BasicNameValuePair("end_date_time", enddate));
            nameValuePairs.add(new BasicNameValuePair("cost", cost));
            nameValuePairs.add(new BasicNameValuePair("bike_name", title));
            nameValuePairs.add(new BasicNameValuePair("bike_image", image));
            nameValuePairs.add(new BasicNameValuePair("decode_start_date", decodestart));
            nameValuePairs.add(new BasicNameValuePair("decode_end_date", decodeend));
            nameValuePairs.add(new BasicNameValuePair("bike_id", bikeid));
            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(SERVER_URL);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = client.execute(httpPost);
                HttpEntity entity = response.getEntity();

                Log.e("pass 1", "connection success");
            } catch (Exception e) {
                Toast.makeText(Confirm.this, "Error" + e, Toast.LENGTH_SHORT).show();
            }
            return null;
        }
    }
}