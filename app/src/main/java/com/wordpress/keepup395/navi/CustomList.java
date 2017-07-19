package com.wordpress.keepup395.navi;

/**
 * Created by user on 19-07-2017.
 */
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class CustomList extends ArrayAdapter<String> {
    Activity context;
    private String[] title;
    private String[] desc;
    private Bitmap[] bitmaps;
    public CustomList(Activity context, Bitmap[] bitmaps,String[] title,String[] desc) {
        super(context,0,title);
        this.context = context;
        this.bitmaps = bitmaps;
        this.title = title;
        this.desc = desc;
    }
    public View getView(final int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.customlayout,null);

        TextView tvTitle = (TextView)rowView.findViewById(R.id.titleFinal);
        TextView tvDesc = (TextView)rowView.findViewById(R.id.textDescFinal);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageViewFinal);

        Button btnBook = (Button)rowView.findViewById(R.id.btnBookFinal);
        btnBook.setTag(position);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Clicked "+title[position],Toast.LENGTH_LONG).show();
            }
        });
        tvTitle.setText(title[position]);
        tvDesc.setText(desc[position]);
        imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmaps[position],100,100,false));
        //imageView.setImageBitmap(Bitmap.createBitmap(bitmaps[position]));
        return rowView;
    }
}

