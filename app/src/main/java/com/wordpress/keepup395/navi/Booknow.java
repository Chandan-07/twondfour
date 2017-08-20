package com.wordpress.keepup395.navi;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

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
import java.util.Calendar;
import java.util.List;

public class Booknow extends AppCompatActivity {

    Button fromdate;
    DatabaseReference mref;
    Button todate;
    int year_x, month_x, date_x, hour_x, checkDay, checkMonth;
    int year_y, month_y, date_y;

    String[] title;
    String[] desc;
    String[] bikehourcost;
    String[] bikedaycost;
    String[] bikeid;
    String[] imageString;
    int check = -1;
    int echeck = -1;
    int k = 0;
    Toolbar toolbar;
    ListView listView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigationView;
    String startDate = "";
    String endDate = "";
    String startDateTime = "";
    String endDateTime = "";
    String startTime = "";
    String endTime = "";
    int flag = 0;
    String email = "";
    //String password="";
    String startbtntext = "";
    String endbtntext = "";
    String startCostCalcDate = "";
    String endCostCalcDate = "";
    String link = "http://www.twondfour.com/fetch/filterfetch.php?startdate=" + startDateTime + "&enddate=" + endDateTime;
    String SERVER = "http://www.twondfour.com/fetch/selectall.php";
    ArrayList<String> bookedId;
    String checkEmail = "";
    String id = "";
    long checkenddate = 123;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booknow);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fromdate = (Button) findViewById(R.id.btnStartdate);
        todate = (Button) findViewById(R.id.btnEnddate);

        bookedId = new ArrayList<>();

        fromdate.setText("START DATE");
        todate.setText("END DATE");

        listView = (ListView)findViewById(R.id.bookBikes) ;
        showdialogOnButtonClick();

        Bundle b = getIntent().getExtras();
        email = b.getString("email");

        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        getSupportActionBar().setTitle("Book Now");
        drawerLayout.setDrawerListener(drawerToggle);


        navigationView=(NavigationView)findViewById(R.id.navigation_view);
        navigationView.setCheckedItem(R.id.home_id);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home_id:
                        getSupportActionBar().setTitle("BookNow");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.about_us:
                        Intent k = new Intent(Booknow.this, aboutus.class);
                        startActivity(k);
                        getSupportActionBar().setTitle("About Us");
                        //item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    /*case R.id.feedback:
                        AlertDialog.Builder builder=new AlertDialog.Builder(Booknow.this);
                        View mview=getLayoutInflater().inflate(R.layout.feedback,null);
                        final EditText feededit=(EditText)mview.findViewById(R.id.editTextfed);
                        final TextView feedtext=(TextView)mview.findViewById(R.id.Feedbacktext);
                        Button send=(Button)mview.findViewById(R.id.sendfeed);
                        builder.setView(mview);
                        AlertDialog dialog=builder.create();
                        dialog.show();
                        send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(!feededit.getText().toString().isEmpty()){
                                    Toast.makeText(Booknow.this,"Thank you for your feed back",Toast.LENGTH_SHORT).show();

                                    String feed1 = feededit.getText().toString().trim();
                                    Bundle b = getIntent().getExtras();

                                    String email = b.getString("email");
                                    String id1 = mref.push().getKey();
                                    Feedback feed= new Feedback(id1,email,feed1);
                                    mref.child(id1).setValue(feed);
                                }
                                else {
                                    Toast.makeText(Booknow.this,"Feedback can not be empty",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        //getSupportActionBar().setTitle("Blank Fragment");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;*/

                    case R.id.refer_id:

                        FirebaseAuth.getInstance().signOut();
                        toAct(SignIn.class);
                        finish();
                        break;

                    case R.id.account:
                        Intent intent = new Intent(Booknow.this, MyAccount.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        getSupportActionBar().setTitle("My Account");
                        //item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.terms_cond:
                        Intent intent1 = new Intent(Booknow.this, TermsndCon.class);
                        startActivity(intent1);
                        getSupportActionBar().setTitle("Terms and Condition");
                        //item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.reach_us:
                        Intent intent2 = new Intent(Booknow.this, MapsActivity.class);
                        startActivity(intent2);
                        getSupportActionBar().setTitle("Reach To Us");
                        //item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.policy:
                        Intent intent3 = new Intent(Booknow.this, Policy.class);
                        startActivity(intent3);
                        getSupportActionBar().setTitle("Policy");
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.contact_us:
                        /*try {
                            Intent call = new Intent(Intent.ACTION_CALL);
                            call.setData(Uri.parse("tel:+919777533892"));
                            call.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(call);
                        }catch (ActivityNotFoundException e)
                        {
                            Log.e("Calling a phone number", "call failed", e);
                        }*/
                        Intent jintent = new Intent(Intent.ACTION_DIAL);
                        jintent.setData(Uri.parse("tel:9777533892"));
                        startActivity(jintent);

                }
                return false;
            }
        });
        new FetchAll().execute(SERVER);
    }

    public void toAct(Class<?> clasz) {
        Intent f = new Intent(this, clasz);
        startActivity(f);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    public void showdialogOnButtonClick() {
        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDialog(DIALOG_ID);
                showStartDatePickerDialog();
            }
        });
        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromdate.getText().toString().equals("START DATE")) {
                    Toast.makeText(Booknow.this, "Choose start date first", Toast.LENGTH_LONG).show();
                } else {
                    showEndDatePickerDialog();
                }
            }
        });
    }

    private void showStartDatePickerDialog() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        checkDay = c.get(Calendar.DAY_OF_MONTH);
        checkMonth = c.get(Calendar.MONTH);
        hour_x = c.get(Calendar.HOUR_OF_DAY);
        DatePickerDialog dialog = new DatePickerDialog(Booknow.this,
                new startDateSetListener(), mYear, mMonth, mDay);
        DatePicker datePicker = dialog.getDatePicker();
        long now = c.getTimeInMillis();
        datePicker.setMinDate(now);
        datePicker.setMaxDate(now + (1000 * 60 * 60 * 24 * 20));
        dialog.setTitle("Choose Start Date");
        dialog.show();
    }

    private void showEndDatePickerDialog() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(Booknow.this,
                new endDateSetListener(), mYear, mMonth, mDay);
        DatePicker datePicker = dialog.getDatePicker();
        c.set(year_x, month_x, date_x, 0, 0, 0);
        long now = c.getTimeInMillis();
        datePicker.setMinDate(now);
        datePicker.setMaxDate(now + (1000 * 60 * 60 * 24 * 2));
        dialog.setTitle("Choose End Date");
        dialog.show();
    }

    private void startset() {
        startDateTime = startDateTime + "" + startTime;
        fromdate.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(date_x).append("/").append(month_x + 1).append("/")
                .append(year_x).append("   ").append(startbtntext));
        startCostCalcDate = date_x + "/" + (month_x + 1) + "/" + year_x + " " + startTime + ":00:00";

        AlertDialog.Builder ab = new AlertDialog.Builder(Booknow.this);
        ab.setMessage("Now choose your end date");
        ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showEndDatePickerDialog();
            }
        });
        ab.show();
        //fromdate.setText(""+startDateTime);
    }

    private void timerstart() {
        AlertDialog.Builder ab = new AlertDialog.Builder(Booknow.this);
        ab.setTitle("Choose Start Time");
        LayoutInflater li = Booknow.this.getLayoutInflater();
        View view = li.inflate(R.layout.timelayout, null);
        final Button[] btns = new Button[14];
        btns[0] = (Button) view.findViewById(R.id.btn9);
        btns[1] = (Button) view.findViewById(R.id.btn10);
        btns[2] = (Button) view.findViewById(R.id.btn11);
        btns[3] = (Button) view.findViewById(R.id.btn12);
        btns[4] = (Button) view.findViewById(R.id.btn13);
        btns[5] = (Button) view.findViewById(R.id.btn14);
        btns[6] = (Button) view.findViewById(R.id.btn15);
        btns[7] = (Button) view.findViewById(R.id.btn16);
        btns[8] = (Button) view.findViewById(R.id.btn17);
        btns[9] = (Button) view.findViewById(R.id.btn18);
        btns[10] = (Button) view.findViewById(R.id.btn19);
        btns[11] = (Button) view.findViewById(R.id.btn20);
        btns[12] = (Button) view.findViewById(R.id.btn21);
        btns[13] = (Button) view.findViewById(R.id.btn22);

        if (checkDay == date_x && checkMonth == month_x) {
            int checkhour = hour_x - 8;
            for (int i = 0; i < checkhour; i++) {
                if (i <= 13) {
                    btns[i].setEnabled(false);
                    btns[i].setClickable(false);
                }
            }
        }

        check = -1;
        btns[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = "09";
                if (check >= 0) {
                    btns[check].setBackgroundColor(0xffffffff);
                    btns[check].setTextColor(0xff000000);
                }

                btns[0].setBackgroundColor(0xff4286f4);
                btns[0].setTextColor(0xffffffff);
                check = 0;
            }
        });

        btns[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = "10";
                if (check >= 0) {
                    btns[check].setBackgroundColor(0xffffffff);
                    btns[check].setTextColor(0xff000000);
                }

                btns[1].setBackgroundColor(0xff4286f4);
                btns[1].setTextColor(0xffffffff);
                check = 1;
            }
        });


        btns[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = "11";
                if (check >= 0) {
                    btns[check].setBackgroundColor(0xffffffff);
                    btns[check].setTextColor(0xff000000);
                }

                btns[2].setBackgroundColor(0xff4286f4);
                btns[2].setTextColor(0xffffffff);
                check = 2;
            }
        });


        btns[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = "12";
                if (check >= 0) {
                    btns[check].setBackgroundColor(0xffffffff);
                    btns[check].setTextColor(0xff000000);
                }

                btns[3].setBackgroundColor(0xff4286f4);
                btns[3].setTextColor(0xffffffff);
                check = 3;
            }
        });


        btns[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = "13";
                if (check >= 0) {
                    btns[check].setBackgroundColor(0xffffffff);
                    btns[check].setTextColor(0xff000000);
                }

                btns[4].setBackgroundColor(0xff4286f4);
                btns[4].setTextColor(0xffffffff);
                check = 4;
            }
        });


        btns[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = "14";
                if (check >= 0) {
                    btns[check].setBackgroundColor(0xffffffff);
                    btns[check].setTextColor(0xff000000);
                }

                btns[5].setBackgroundColor(0xff4286f4);
                btns[5].setTextColor(0xffffffff);
                check = 5;
            }
        });


        btns[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = "15";
                if (check >= 0) {
                    btns[check].setBackgroundColor(0xffffffff);
                    btns[check].setTextColor(0xff000000);
                }

                btns[6].setBackgroundColor(0xff4286f4);
                btns[6].setTextColor(0xffffffff);
                check = 6;
            }
        });


        btns[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = "16";
                if (check >= 0) {
                    btns[check].setBackgroundColor(0xffffffff);
                    btns[check].setTextColor(0xff000000);
                }

                btns[7].setBackgroundColor(0xff4286f4);
                btns[7].setTextColor(0xffffffff);
                check = 7;
            }
        });


        btns[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = "17";
                if (check >= 0) {
                    btns[check].setBackgroundColor(0xffffffff);
                    btns[check].setTextColor(0xff000000);
                }

                btns[8].setBackgroundColor(0xff4286f4);
                btns[8].setTextColor(0xffffffff);
                check = 8;
            }
        });


        btns[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = "18";
                if (check >= 0) {
                    btns[check].setBackgroundColor(0xffffffff);
                    btns[check].setTextColor(0xff000000);
                }

                btns[9].setBackgroundColor(0xff4286f4);
                btns[9].setTextColor(0xffffffff);
                check = 9;
            }
        });


        btns[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = "19";
                if (check >= 0) {
                    btns[check].setBackgroundColor(0xffffffff);
                    btns[check].setTextColor(0xff000000);
                }

                btns[10].setBackgroundColor(0xff4286f4);
                btns[10].setTextColor(0xffffffff);
                check = 10;
            }
        });


        btns[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = "20";
                if (check >= 0) {
                    btns[check].setBackgroundColor(0xffffffff);
                    btns[check].setTextColor(0xff000000);
                }

                btns[11].setBackgroundColor(0xff4286f4);
                btns[11].setTextColor(0xffffffff);
                check = 11;
            }
        });


        btns[12].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = "21";
                if (check >= 0) {
                    btns[check].setBackgroundColor(0xffffffff);
                    btns[check].setTextColor(0xff000000);
                }

                btns[12].setBackgroundColor(0xff4286f4);
                btns[12].setTextColor(0xffffffff);
                check = 12;
            }
        });


        btns[13].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = "22";
                if (check >= 0) {
                    btns[check].setBackgroundColor(0xffffffff);
                    btns[check].setTextColor(0xff000000);
                }

                btns[13].setBackgroundColor(0xff4286f4);
                btns[13].setTextColor(0xffffffff);
                check = 13;
            }
        });


        ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (check >= 0) {
                    startbtntext = btns[check].getText().toString();
                    startset();
                }
            }
        });
        ab.setView(view);
        ab.setCancelable(false);
        ab.show();
    }

    private void endset() {
        endDateTime = endDateTime + "" + endTime;
        todate.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(date_y).append("/").append(month_y + 1).append("/")
                .append(year_y).append("   ").append(endbtntext));
        //todate.setText(""+endDateTime);
        endCostCalcDate = date_y + "/" + (month_y + 1) + "/" + year_y + " " + endTime + ":00:00";
        String link2 = "http://www.twondfour.com/fetch/filterfetch.php?startdate=" + startDateTime + "&enddate=" + endDateTime;
        new GetUrls().execute(link2);
    }

    private void timerend() {
        AlertDialog.Builder ab = new AlertDialog.Builder(Booknow.this);
        ab.setTitle("Choose End Time");
        LayoutInflater li = Booknow.this.getLayoutInflater();
        View view = li.inflate(R.layout.timelayout, null);
        final Button[] btns = new Button[14];
        btns[0] = (Button) view.findViewById(R.id.btn9);
        btns[1] = (Button) view.findViewById(R.id.btn10);
        btns[2] = (Button) view.findViewById(R.id.btn11);
        btns[3] = (Button) view.findViewById(R.id.btn12);
        btns[4] = (Button) view.findViewById(R.id.btn13);
        btns[5] = (Button) view.findViewById(R.id.btn14);
        btns[6] = (Button) view.findViewById(R.id.btn15);
        btns[7] = (Button) view.findViewById(R.id.btn16);
        btns[8] = (Button) view.findViewById(R.id.btn17);
        btns[9] = (Button) view.findViewById(R.id.btn18);
        btns[10] = (Button) view.findViewById(R.id.btn19);
        btns[11] = (Button) view.findViewById(R.id.btn20);
        btns[12] = (Button) view.findViewById(R.id.btn21);
        btns[13] = (Button) view.findViewById(R.id.btn22);

        echeck = -1;

        if (startDate.equals(endDate)) {
            //if((check+2)<13){
            for (int i = 0; i <= check + 2; i++) {
                if (i <= 13) {
                    btns[i].setEnabled(false);
                    btns[i].setClickable(false);
                }
            }
            /*}
            else{
                for(int i=0;i<=13;i++){
                    btns[i].setEnabled(false);
                    btns[i].setClickable(false);
                }
            }*/
        }

        btns[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime = "09";
                if (echeck >= 0) {
                    btns[echeck].setBackgroundColor(0xffffffff);
                    btns[echeck].setTextColor(0xff000000);
                }

                btns[0].setBackgroundColor(0xff4286f4);
                btns[0].setTextColor(0xffffffff);
                echeck = 0;
            }
        });

        btns[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime = "10";
                if (echeck >= 0) {
                    btns[echeck].setBackgroundColor(0xffffffff);
                    btns[echeck].setTextColor(0xff000000);
                }

                btns[1].setBackgroundColor(0xff4286f4);
                btns[1].setTextColor(0xffffffff);
                echeck = 1;
            }
        });


        btns[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime = "11";
                if (echeck >= 0) {
                    btns[echeck].setBackgroundColor(0xffffffff);
                    btns[echeck].setTextColor(0xff000000);
                }

                btns[2].setBackgroundColor(0xff4286f4);
                btns[2].setTextColor(0xffffffff);
                echeck = 2;
            }
        });


        btns[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime = "12";
                if (echeck >= 0) {
                    btns[echeck].setBackgroundColor(0xffffffff);
                    btns[echeck].setTextColor(0xff000000);
                }

                btns[3].setBackgroundColor(0xff4286f4);
                btns[3].setTextColor(0xffffffff);
                echeck = 3;
            }
        });


        btns[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime = "13";
                if (echeck >= 0) {
                    btns[echeck].setBackgroundColor(0xffffffff);
                    btns[echeck].setTextColor(0xff000000);
                }

                btns[4].setBackgroundColor(0xff4286f4);
                btns[4].setTextColor(0xffffffff);
                echeck = 4;
            }
        });


        btns[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime = "14";
                if (echeck >= 0) {
                    btns[echeck].setBackgroundColor(0xffffffff);
                    btns[echeck].setTextColor(0xff000000);
                }

                btns[5].setBackgroundColor(0xff4286f4);
                btns[5].setTextColor(0xffffffff);
                echeck = 5;
            }
        });


        btns[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime = "15";
                if (echeck >= 0) {
                    btns[echeck].setBackgroundColor(0xffffffff);
                    btns[echeck].setTextColor(0xff000000);
                }

                btns[6].setBackgroundColor(0xff4286f4);
                btns[6].setTextColor(0xffffffff);
                echeck = 6;
            }
        });


        btns[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime = "16";
                if (echeck >= 0) {
                    btns[echeck].setBackgroundColor(0xffffffff);
                    btns[echeck].setTextColor(0xff000000);
                }

                btns[7].setBackgroundColor(0xff4286f4);
                btns[7].setTextColor(0xffffffff);
                echeck = 7;
            }
        });


        btns[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime = "17";
                if (echeck >= 0) {
                    btns[echeck].setBackgroundColor(0xffffffff);
                    btns[echeck].setTextColor(0xff000000);
                }

                btns[8].setBackgroundColor(0xff4286f4);
                btns[8].setTextColor(0xffffffff);
                echeck = 8;
            }
        });


        btns[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime = "18";
                if (echeck >= 0) {
                    btns[echeck].setBackgroundColor(0xffffffff);
                    btns[echeck].setTextColor(0xff000000);
                }

                btns[9].setBackgroundColor(0xff4286f4);
                btns[9].setTextColor(0xffffffff);
                echeck = 9;
            }
        });


        btns[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime = "19";
                if (echeck >= 0) {
                    btns[echeck].setBackgroundColor(0xffffffff);
                    btns[echeck].setTextColor(0xff000000);
                }

                btns[10].setBackgroundColor(0xff4286f4);
                btns[10].setTextColor(0xffffffff);
                echeck = 10;
            }
        });


        btns[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime = "20";
                if (echeck >= 0) {
                    btns[echeck].setBackgroundColor(0xffffffff);
                    btns[echeck].setTextColor(0xff000000);
                }

                btns[11].setBackgroundColor(0xff4286f4);
                btns[11].setTextColor(0xffffffff);
                echeck = 11;
            }
        });


        btns[12].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime = "21";
                if (echeck >= 0) {
                    btns[echeck].setBackgroundColor(0xffffffff);
                    btns[echeck].setTextColor(0xff000000);
                }

                btns[12].setBackgroundColor(0xff4286f4);
                btns[12].setTextColor(0xffffffff);
                echeck = 12;
            }
        });


        btns[13].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime = "22";
                if (echeck >= 0) {
                    btns[echeck].setBackgroundColor(0xffffffff);
                    btns[echeck].setTextColor(0xff000000);
                }

                btns[13].setBackgroundColor(0xff4286f4);
                btns[13].setTextColor(0xffffffff);
                echeck = 13;
            }
        });


        ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (echeck >= 0) {
                    endbtntext = btns[echeck].getText().toString();
                    endset();
                }
            }
        });
        ab.setView(view);
        ab.setCancelable(false);
        ab.show();
    }

    private class startDateSetListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month_x = month;
            year_x = year;
            Calendar cal = Calendar.getInstance();
            //hour_x = cal.get(Calendar.HOUR_OF_DAY);
            if (month_x < 9) {
                startDate = "" + year_x + "0" + (month_x + 1);
            } else {
                startDate = "" + year_x + (month_x + 1);
            }
            date_x = dayOfMonth;
            if (date_x < 10) {
                startDate = startDate + "0" + date_x;
            } else {
                startDate = startDate + "" + date_x;
            }
            startDateTime = startDate;
            timerstart();
        }
    }

    private class endDateSetListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_y = year;
            month_y = month;
            if (month_y < 9) {
                endDate = "" + year_y + "0" + (month_y + 1);
            } else {
                endDate = "" + year_y + (month_y + 1);
            }
            date_y = dayOfMonth;
            if (date_y < 10) {
                endDate = endDate + "0" + date_y;
            } else {
                endDate = endDate + "" + date_y;
            }
            endDateTime = endDate;
            timerend();
        }
    }

    class GetUrls extends AsyncTask<String, Void, Boolean> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Booknow.this,"Loading...","Please wait...",true,true);
            }


            @Override
            protected Boolean doInBackground(String... params) {

                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                try{
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();
                    request.setURI(new URI(params[0]));
                    HttpResponse response = client.execute(request);
                    int status = response.getStatusLine().getStatusCode();
                    if(status==200){
                        HttpEntity entity = response.getEntity();
                        String data = EntityUtils.toString(entity);
                        JSONObject jsonObject = new JSONObject(data);
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            imageString[i] = object.getString("bike_image");
                            title[i] = object.getString("bike_name");
                            desc[i] = object.getString("bike_desc");
                            bikehourcost[i] = object.getString("bike_hour_cost");
                            bikedaycost[i] = object.getString("bike_day_cost");
                            bikeid[i] = object.getString("id");
                            k = i;
                        }
                    }
                    return false;
                }
                catch (Exception e){
                    Toast.makeText(Booknow.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean aboolean) {
                super.onPostExecute(aboolean);
                new Booked().execute();
            }
    }

    class FetchAll extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(Booknow.this, "Loading...", "Please wait...", true, true);
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
                    title = new String[jsonArray.length()];
                    desc = new String[jsonArray.length()];
                    bikehourcost = new String[jsonArray.length()];
                    bikedaycost = new String[jsonArray.length()];
                    bikeid = new String[jsonArray.length()];
                    imageString = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        imageString[i] = object.getString("bike_image");
                        title[i] = object.getString("bike_name");
                        desc[i] = object.getString("bike_desc");
                        bikehourcost[i] = object.getString("bike_hour_cost");
                        bikedaycost[i] = object.getString("bike_day_cost");
                        bikeid[i] = object.getString("id");
                    }
                }
                return false;
            } catch (Exception e) {
                Toast.makeText(Booknow.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            String btnStart = fromdate.getText().toString();
            String btnEnd = todate.getText().toString();
            CustomList customlist = new CustomList(Booknow.this, title, bikehourcost, bikedaycost, desc, btnStart, btnEnd, email, startDateTime, endDateTime, startCostCalcDate, endCostCalcDate, bikeid, imageString, bookedId);
            listView.setAdapter(customlist);
            //loading.dismiss();
            new RemoveRideCompleteUser().execute();
        }
    }

    class Booked extends AsyncTask<String, Void, Boolean> {
        String link2 = "http://www.twondfour.com/fetch/filterFetchBooked.php?startdate=" + startDateTime + "&enddate=" + endDateTime;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bookedId = new ArrayList<>();
        }

        @Override
        protected Boolean doInBackground(String... params) {

            if (Looper.myLooper() == null) {
                Looper.prepare();
            }
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link2));
                HttpResponse response = client.execute(request);
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    k = k + 1;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        imageString[i + k] = object.getString("bike_image");
                        title[i + k] = object.getString("bike_name");
                        desc[i + k] = object.getString("bike_desc");
                        bikehourcost[i + k] = object.getString("bike_hour_cost");
                        bikedaycost[i + k] = object.getString("bike_day_cost");
                        bikeid[i + k] = object.getString("id");
                        bookedId.add(bikeid[i + k]);
                    }
                }
                return false;
            } catch (Exception e) {
                Toast.makeText(Booknow.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aboolean) {
            super.onPostExecute(aboolean);
            String btnStart = fromdate.getText().toString();
            String btnEnd = todate.getText().toString();
            CustomList customlist = new CustomList(Booknow.this, title, bikehourcost, bikedaycost, desc, btnStart, btnEnd, email, startDateTime, endDateTime, startCostCalcDate, endCostCalcDate, bikeid, imageString, bookedId);
            listView.setAdapter(customlist);
            Toast.makeText(Booknow.this, "You can Book Now", Toast.LENGTH_LONG).show();
            loading.dismiss();
        }
    }

    class RemoveRideCompleteUser extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            String checkdecodestartdate = "";
            long today;
            Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            if (mMonth < 9) {
                checkdecodestartdate = "" + mYear + "0" + (mMonth + 1);
            } else {
                checkdecodestartdate = "" + mYear + (mMonth + 1);
            }
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            if (mDay < 10) {
                checkdecodestartdate = checkdecodestartdate + "0" + mDay;
            } else {
                checkdecodestartdate = checkdecodestartdate + "" + mDay;
            }
            int hour_x = c.get(Calendar.HOUR_OF_DAY);
            if (hour_x < 10) {
                checkdecodestartdate = checkdecodestartdate + "0" + hour_x;
            } else {
                checkdecodestartdate = checkdecodestartdate + hour_x;
            }
            today = Long.parseLong(checkdecodestartdate);

            //Toast.makeText(Booknow.this,""+today,Toast.LENGTH_LONG).show();
            //Toast.makeText(Booknow.this,""+checkenddate+"  "+today,Toast.LENGTH_LONG).show();
            if (checkenddate != 123) {
                if (checkenddate < today) {
                    new DeleteUser().execute();
                }
            }
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
                    id = object.getString("id");
                    checkenddate = Long.parseLong(object.getString("enddate"));
                }
            } catch (Exception e) {
                Toast.makeText(Booknow.this, "Error", Toast.LENGTH_LONG).show();
            }
            return null;
        }
    }

    class DeleteUser extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            //loading.dismiss();
            AlertDialog.Builder ab = new AlertDialog.Builder(Booknow.this);
            LayoutInflater li = Booknow.this.getLayoutInflater();
            View v = li.inflate(R.layout.feedback, null);

            final RatingBar ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
            final EditText editText = (EditText) v.findViewById(R.id.editTextfed);
            //ab.setTitle("Feedback Time!!!");
            //ab.setMessage("Hope you like our previous ride\nSee you soon again");
            ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    float totalStars = ratingBar.getNumStars();
                    float selectedStars = ratingBar.getRating();
                    String stars = selectedStars + "/" + totalStars;
                    String feedback = editText.getText().toString().trim();
                    if (!TextUtils.isEmpty(feedback)) {
                        new Feedback().execute(email, stars, feedback);
                    }
                }
            });
            ab.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            ab.setView(v);
            ab.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            if (Looper.myLooper() == null) {
                Looper.prepare();
            }
            try {
                String url = "http://www.twondfour.com/fetch/deleteUsersBook.php?id=" + id;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(url));
                HttpResponse response = client.execute(request);
                int status = response.getStatusLine().getStatusCode();
            } catch (Exception e) {
                Toast.makeText(Booknow.this, "Error", Toast.LENGTH_LONG).show();
            }
            return null;
        }
    }

    class Feedback extends AsyncTask<String, Void, Boolean> {
        String SERVER_URL = "http://www.twondfour.com/fetch/feedback.php";

        ProgressDialog progressDialogFeedback = new ProgressDialog(Booknow.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialogFeedback.setMessage("Please wait...");
            progressDialogFeedback.show();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressDialogFeedback.dismiss();
        }

        @Override
        protected Boolean doInBackground(String... strings) {

            if (Looper.myLooper() == null) {
                Looper.prepare();
            }
            String email = strings[0];
            String stars = strings[1];
            String feedback = strings[2];
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("email_id", email));
            nameValuePairs.add(new BasicNameValuePair("rating", stars));
            nameValuePairs.add(new BasicNameValuePair("feedback", feedback));
            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(SERVER_URL);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = client.execute(httpPost);
                HttpEntity entity = response.getEntity();

                Log.e("pass 1", "connection success");
            } catch (Exception e) {
                Toast.makeText(Booknow.this, "Error" + e, Toast.LENGTH_SHORT).show();
            }
            return null;
        }
    }
}