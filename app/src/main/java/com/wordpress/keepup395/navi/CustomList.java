package com.wordpress.keepup395.navi;

/**
 * Created by user on 19-07-2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CustomList extends ArrayAdapter<String> {
    Activity context;
    private String[] title;
    private String[] desc;
    //private Bitmap[] bitmaps;
    private String[] bikehourcost;
    private String[] bikedaycost;
    private String[] bikeid;
    private String[] imageString;
    private String btnStart = "";
    private String btnEnd = "";
    private String email = "";
    private String password = "";
    private String ustart = "";
    private String uend = "";
    private String startCostDate = "";
    private String endCostDate = "";
    private ArrayList<String> bookedId;

    public CustomList(Activity context,/*Bitmap[] bitmaps,*/String[] title, String[] bikehourcost, String[] bikedaycost, String[] desc, String btnStart, String btnEnd, String email, String ustart, String uend, String startCostDate, String endCostDate, String[] bikeid, String[] imageString, ArrayList<String> bookedId) {
        super(context,0,title);
        this.context = context;
        //this.bitmaps = bitmaps;
        this.title = title;
        this.imageString = imageString;
        //this.password=password;
        this.desc = desc;
        this.bikehourcost = bikehourcost;
        this.bikedaycost = bikedaycost;
        this.btnStart = btnStart;
        this.btnEnd = btnEnd;
        this.email = email;
        this.ustart = ustart;
        this.uend = uend;
        this.startCostDate = startCostDate;
        this.endCostDate = endCostDate;
        this.bikeid = bikeid;
        this.bookedId = bookedId;
    }
    public View getView(final int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.customlayout,null);

        TextView tvTitle = (TextView)rowView.findViewById(R.id.titleFinal);
        TextView tvDesc = (TextView)rowView.findViewById(R.id.textDescFinal);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageViewFinal);
        TextView tvbikehourcost = (TextView) rowView.findViewById(R.id.bike_hour_cost);

        final Button btnBook = (Button) rowView.findViewById(R.id.btnBookFinal);
        btnBook.setTag(position);
        if (bookedId.contains(bikeid[position])) {
            btnBook.setClickable(false);
            btnBook.setEnabled(false);
            btnBook.setBackgroundColor(0xffbdc3c7);
            btnBook.setTextColor(0xffecf0f1);
            btnBook.setText("Booked");
        }
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStart.equals("START DATE") || btnEnd.equals("END DATE")) {
                    Toast.makeText(context, "Choose Start/End Date", Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(context, finalobook.class);

                    i.putExtra("title", title[position]);
                    i.putExtra("desc", desc[position]);
                    i.putExtra("cost", costCalculator(startCostDate, endCostDate, bikehourcost[position], bikedaycost[position]));
                    //String image = BitMapToString(bitmaps[position]);
                    i.putExtra("image", imageString[position]);
                    i.putExtra("email", email);
                    //i.putExtra("password",password);
                    i.putExtra("ustart", ustart);
                    i.putExtra("uend", uend);
                    i.putExtra("btnstart", btnStart);
                    i.putExtra("btnend", btnEnd);
                    i.putExtra("id", bikeid[position]);
                    context.startActivity(i);
                }
            }
        });
        /*imageView.setTag(position);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,""+position,Toast.LENGTH_LONG).show();
                AlertDialog.Builder ab = new AlertDialog.Builder(context);
                LayoutInflater li = context.getLayoutInflater();
                View view = li.inflate(R.layout.listviewonclick,null);
                TextView tvBikeName = (TextView)view.findViewById(R.id.bike_name);
                TextView tvBikeCC = (TextView)view.findViewById(R.id.bike_cc);
                ImageView bikeImage = (ImageView)view.findViewById(R.id.bike_image);
                tvBikeName.setText(title[position]);
                tvBikeCC.setText(desc[position]+" CC");
                Picasso.with(context).load(imageString[position]).resize(300,300).into(bikeImage);
                ab.setView(view);
                ab.show();
            }
        });*/
        tvTitle.setText(title[position]);
        tvDesc.setText(desc[position] + " cc");
        tvbikehourcost.setText("Rs " + bikehourcost[position] + "/hr ");
        //imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmaps[position],250,250,false));
        //imageView.setImageBitmap(Bitmap.createBitmap(bitmaps[position]));
        //Picasso.with(context).load(imageString[position]).into(imageView);
        Picasso.with(context)
                .load(imageString[position])
                .placeholder(R.drawable.loading)
                .into(imageView);
        return rowView;
    }

    public String costCalculator(String startdate, String enddate, String hourcost, String daycost) {
        SimpleDateFormat format = new SimpleDateFormat("DD/mm/yyyy HH:mm:ss");

        String cost = "";
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(startdate);
            d2 = format.parse(enddate);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            long daycostcal = Integer.parseInt(daycost) * diffDays;
            long hourcostcal = Integer.parseInt(hourcost) * diffHours;
            long costlong = daycostcal + hourcostcal;
            cost = "" + costlong;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return (cost);
    }

    /*public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }*/
}

