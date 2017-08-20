package com.wordpress.keepup395.navi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class TermsndCon extends AppCompatActivity {
    TextView textView;

    String longText = "•\tWhile renting the bike, Provisional drop-off time will be specified and final drop-off the will be specified when the vehicle is returned to 2& Four.\n\n" +
            "•\t2& Four provides one helmet per motorcycle as complimentary service and it has to be returned without any damage during the drop. (30rs/day per extra helmet will be charged).\n\n" +
            "•\tThe customer will be responsible for all the penalties, fine, (during the renting period)\n\n" +
            "•\tIn case of damage to the two-wheeler on rent due to accident/mishandling/carelessness:\n\n" +
            "A.\tThe customer has to pay 100% of the accidental repair cost (estimated by the authorized bike service agency) along with the daily tariff until the bike is ready for renting again.\n\n" +
            "B.\tCustomers have to inform 2& Four within 2 hours and within 48 hours to police station.\n\n" +
            "•\tIn case of minor damages to the helmet, the customer is liable to pay Rs.200/helmet and in case of major damage or loss of the helmet, the customer is liable to pay the market value of the helmet.\n\n" +
            "•\tIn case of theft, the customer is liable to pay, in full, the market rate of the two-wheeler/Helmet.\n\n" +
            "•\tThe two-wheeler needs to be returned as mentioned at the specified provisional Date and Time. A delay of more than 15 minutes without intimating 2& Four will attract a penalty of hourly charges.\n\n" +
            "•\tA Trip extension request is to be made at least 1 hour before the provisional drop-off time.\n\n" +
            "•\tCharges for Trip extension: - The standard rates will be applicable for extensions.\n\n" +
            "•\tPhotocopy of the following documents has to be submitted by the customers to avail the rental service.\n\n" +
            "a)\tDriving License\n\n" +
            "b)\tAadhaar Card/Voter Id/Passport\n\n" +
            "c)\tCollege Id/Company Id\n\n" +
            "•\t2& Four is not responsible for any criminal activity carried out in association with rented vehicle (during the renting period).\n\n" +
            "•\tIn case of any legal dispute or conflict between 2& Four and its customers arising from the services of 2& Four, the sole and exclusive jurisdiction lies with the Courts of Bhubaneswar, India.\n\n" +
            "•\tThe customer is liable to pay 2& Four reasonable attorney fees and bear the cost of legal proceedings relating to this agreement, including appeals.\n\n" +
            "•\tThe Rental fare does not include fuel costs and the toll taxes to be paid.\n\n" +
            "•\tCustomer has to ensure that the rented vehicle is used in a skillful and proper manner and duly driven by a person that bears a valid driving license.\n\n" +
            "•\t2& Four may immediately end the Agreement if 2& Four become aware or suspect that you have, or any Approved Driver has breached these terms and conditions.\n\n" +
            "•\tAny person(s) who is not a party to the Rental Agreement cannot enforce any of our responsibilities under the Rental Agreement.\n\n" +
            "•\tThe Vehicle should not be used for racing, off-roading, speed testing, driving instruction.\n\n" +
            "•\tThe Vehicle should not be used for any illegal purpose, or in an unreasonable nor negligent manner\n\n" +
            "•\tCustomer will not sell, rent or dispose of the Vehicle or any of its parts or Accessories\n\n" +
            "•\tCustomer will not attempt to nor give anyone any legal rights over the Vehicle\n\n" +
            "•\tCustomer should ensure that no alterations are made to the motor vehicle or any component removed unless it is immediately replaced by the same component or by one of the same like, make and model or an improved or advanced version.\n\n" +
            "•\tCustomer should wear a Helmet and carry a Valid Driving License issued by the Government of India during the tenure of the bike rental.\n\n" +
            "•\tPrior to taking any vehicle, the customer must document any pre-existing damage and have acknowledged it as well. In case of any concerns, the customer may take photos at the start of the reservation and mail it to twondfour@gmail.com. Otherwise, the customer may be held liable for any damage reported after the reservation.\n\n" +
            "•\t2& Four is not responsible for any item left in the vehicle. 2& Four may agree to keep them for you to collect within a reasonable time\n\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termsnd_con);
        TextView textView = (TextView) findViewById(R.id.textterm);
        textView.setText(longText);
    }
}
