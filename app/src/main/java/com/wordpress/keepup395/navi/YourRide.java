package com.wordpress.keepup395.navi;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Swarna on 03-08-2017.
 */

public class YourRide extends ArrayAdapter<String> {
    Activity context;
    Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay;
    int mTime;
    String checkdecodestartdate = "";
    long checkStart;
    private String[] bikename;
    private String[] cost;
    private String[] startdate;
    private String[] enddate;
    private String[] decodestartdate;

    public YourRide(Activity context, String[] bikename, String[] cost, String[] startdate, String[] enddate, String[] decodestartdate) {
        super(context, 0, bikename);
        this.context = context;
        this.bikename = bikename;
        this.cost = cost;
        this.startdate = startdate;
        this.enddate = enddate;
        this.decodestartdate = decodestartdate;


        if (mMonth < 9) {
            checkdecodestartdate = "" + mYear + "0" + (mMonth + 1);
        } else {
            checkdecodestartdate = "" + mYear + (mMonth + 1);
        }
        mDay = c.get(Calendar.DAY_OF_MONTH);
        if (mDay < 10) {
            checkdecodestartdate = checkdecodestartdate + "0" + mDay;
        } else {
            checkdecodestartdate = checkdecodestartdate + "" + mDay;
        }
        mTime = c.get(Calendar.HOUR_OF_DAY);
        if (mTime < 10) {
            checkdecodestartdate = checkdecodestartdate + "0" + mTime;
        } else {
            checkdecodestartdate = checkdecodestartdate + mTime;
        }
        checkStart = Long.parseLong(checkdecodestartdate);

    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.yourridelayout, null);

        TextView tvbikename = (TextView) rowView.findViewById(R.id.bikename);
        TextView tvstartdate = (TextView) rowView.findViewById(R.id.startdate);
        TextView tvenddate = (TextView) rowView.findViewById(R.id.enddate);
        TextView tvcost = (TextView) rowView.findViewById(R.id.cost);
        Button btnCancel = (Button) rowView.findViewById(R.id.btnCancel);

        btnCancel.setTag(position);
        long decodeStart = Long.parseLong(decodestartdate[position]);
        if (decodeStart <= checkStart) {
            btnCancel.setEnabled(false);
            btnCancel.setClickable(false);
            btnCancel.setBackgroundColor(0xffbdc3c7);
            btnCancel.setTextColor(0xffecf0f1);
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*try {
                    Intent call = new Intent(Intent.ACTION_CALL);
                    call.setData(Uri.parse("tel:+919777533892"));
                    call.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(call);
                }catch (ActivityNotFoundException e)
                {
                    Log.e("Calling a phone number", "call failed", e);
                }*/
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9777533892"));
                context.startActivity(intent);
            }
        });
        tvbikename.setText(bikename[position]);
        tvstartdate.setText("Start Date & Time : " + startdate[position]);
        tvcost.setText("Cost : Rs " + cost[position]);
        tvenddate.setText("End Date & Time : " + enddate[position]);
        //imageView.setImageBitmap(Bitmap.createBitmap(bitmaps[position]));
        return rowView;
    }
}