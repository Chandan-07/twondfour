package com.wordpress.keepup395.navi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Policy extends AppCompatActivity {
    String cancel = "- If the ride is cancelled by the customer before 5 hours of the booking, 50% of the fare amount will be reimbursed .\n" +
            "- If the cancelation is done within 5 hours, fare will not be refunded.\n" +
            "- If the ride cancelled by 2&four due to unavailability of vehicle, 100%  refund will be made\n" +
            "- All refund will take less than 24 hours to be completed .";
    String schedule = "- All the re-scheduling has to be made at least 1 day before the pickup date and time";
    String extension = "- If the rider wants to extend the booking hours then extension charge will be applicable.\n\n" +
            "Extension charge being :\n" + "Additional R.s 20/- per hour for every vehicle for extension period.\n" + "Additional R.s 200/- per day for every vehicle for the extension period.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);

        TextView textCancel = (TextView) findViewById(R.id.textPolicyContent);
        TextView textSchedule = (TextView) findViewById(R.id.textReScedulingContent);
        TextView textExtension = (TextView) findViewById(R.id.textExtensionPolicyContent);

        textCancel.setText(cancel);
        textSchedule.setText(schedule);
        textExtension.setText(extension);
    }
}
