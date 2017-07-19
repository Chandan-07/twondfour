package com.wordpress.keepup395.navi;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;
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
import java.net.URL;

public class Booknow extends AppCompatActivity {

    Button fromdate;
    Button todate;
    int year_x,month_x,date_x,hour_x,min_x;
    int year_y,month_y,date_y,hour_y,min_y;
    static  final  int DIALOG_ID=0,DIALOG_ID2=1,DIALOG_ID3=2,DIALOG_ID4=3;

    Button btnfilter;
    String[] title;
    String[] desc;
    Bitmap[] bitmaps;
    URL url = null;
    String IMAGE_URL = "image";

    Calendar cal;
    Toolbar toolbar;
  ListView listView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    FragmentTransaction fragmentTransaction;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booknow);
        cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        date_x = cal.get(Calendar.DAY_OF_MONTH);
        year_y = cal.get(Calendar.YEAR);
        month_y = cal.get(Calendar.MONTH);
        date_y = cal.get(Calendar.DAY_OF_MONTH);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fromdate=(Button)findViewById(R.id.btnStartDate);
        todate = (Button)findViewById(R.id.btnEndDate);
        btnfilter = (Button)findViewById(R.id.btnfilter);

        fromdate.setText(date_x+"/"+month_x+"/"+year_x+" - "+(cal.get(Calendar.HOUR)+1)+":00");
        todate.setText(date_x+"/"+month_x+"/"+year_x+" - "+(cal.get(Calendar.HOUR)+4)+":00");

        listView = (ListView)findViewById(R.id.bookBikes) ;
        showdialogOnButtonClick();
        getUrls();
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        drawerToggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, new BlankFragment());
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("Blank fragment");
        navigationView=(NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home_id:
                        fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new BlankFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Blank Fragment");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.message_id:
                        setContentView(R.layout.activity_booknow);
                        getSupportActionBar().setTitle("Home ");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.myaccount_id:
                        fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new Terms_cond());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("terms and condition");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                  /* case R.id.setting_id:
                        fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new BlankFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Blank Fragment");
                        item.setChecked(true);
                        break;
                    case R.id.wallet_id:
                        fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new BlankFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Blank Fragment");
                        item.setChecked(true);
                        break;
                    case R.id.refer_id:
                        fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new BlankFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Blank Fragment");
                        item.setChecked(true);
                        break;*/


                }



                return false;
            }
        });
    }
    private Bitmap getImage(JSONObject jo){
        URL url = null;
        Bitmap image = null;
        try {
            url = new URL(jo.getString(IMAGE_URL));
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (Exception e) {
            Toast.makeText(Booknow.this,"Error",Toast.LENGTH_LONG).show();
        }
        return image;
    }

    private void getUrls() {

        class GetUrls extends AsyncTask<String,Void,Boolean> {

            private ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Booknow.this,"Loading...","Please wait...",true,true);
            }


            @Override
            protected Boolean doInBackground(String... params) {

                Looper.prepare();
                try{
                    String startdate = ""+year_x+month_x+date_x+hour_x;
                    String enddate = ""+year_y+month_y+date_y+hour_y;

                    String link = "https://2andfour.000webhostapp.com/test.php?stdate=2017071411&eddate=2017071510";

                    //URL url = new URL(link);
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();
                    request.setURI(new URI(link));
                    HttpResponse response = client.execute(request);
                    int status = response.getStatusLine().getStatusCode();
                    if(status==200){
                        HttpEntity entity = response.getEntity();
                        String data = EntityUtils.toString(entity);
                        JSONObject jsonObject = new JSONObject(data);
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        bitmaps = new Bitmap[jsonArray.length()];
                        title = new String[jsonArray.length()];
                        desc = new String[jsonArray.length()];
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            //url = new URL(object.getString("image"));
                            //bitmaps[i]= BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            bitmaps[i] = getImage(object);
                            title[i] = object.getString("title");
                            desc[i] = object.getString("desc");
                        }
                    }
                }
                catch (Exception e){
                    Toast.makeText(Booknow.this,"Error",Toast.LENGTH_LONG).show();
                }

                return false;
            }

            @Override
            protected void onPostExecute(Boolean aboolean) {
                super.onPostExecute(aboolean);
                CustomList customlist = new CustomList(Booknow.this,bitmaps,title,desc);
                listView.setAdapter(customlist);
                loading.dismiss();
            }
        }
        GetUrls gu = new GetUrls();
        gu.execute();
    }

    public void showdialogOnButtonClick(){
        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID3);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==DIALOG_ID)
            return new DatePickerDialog(this, dpicklistner, year_x,month_x,date_x);
        if(id==DIALOG_ID2)
            return new TimePickerDialog(this, tpicklistner , hour_x,min_x,false);
        if(id==DIALOG_ID3)
            return new DatePickerDialog(this, dpicklistner1, year_y,month_y,date_y);
        if(id==DIALOG_ID4)
            return new TimePickerDialog(this, tpicklistner1 , hour_y,min_y,false);
        return null;
    }
    private TimePickerDialog.OnTimeSetListener tpicklistner = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour_x = hourOfDay;
            min_x = minute;
            fromdate.setText(date_x+"/"+month_x+"/"+year_x+" - "+hour_x+":"+min_x);
        }
    };
    private TimePickerDialog.OnTimeSetListener tpicklistner1 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour_y = hourOfDay;
            min_y = minute;
            todate.setText(date_y+"/"+month_y+"/"+year_y+" - "+hour_y+":"+min_y);
        }
    };

    private  DatePickerDialog.OnDateSetListener dpicklistner= new  DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x= year;
            month_x=month;
            date_x=dayOfMonth;
            showDialog(DIALOG_ID2);
        }


    };
    private  DatePickerDialog.OnDateSetListener dpicklistner1= new  DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_y= year;
            month_y=month;
            date_y=dayOfMonth;
            showDialog(DIALOG_ID4);
        }
    };
}
