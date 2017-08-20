package com.wordpress.keepup395.navi;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    double latitude;
    double longitude;
    String SERVER_URL = "http://www.twondfour.com/fetch/googlemap.php";
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //new FetchCoordinates().execute();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng twondfour = new LatLng(20.3533443, 85.8368205);
        mMap.addMarker(new MarkerOptions().position(twondfour).title(" 2&Four"));
        /*mMap.moveCamera(CameraUpdateFactory.newLatLng(twondfour));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));*/

        //LatLng coordinate = new LatLng(Latitude, Latitude);
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(twondfour, 14.0f);
        mMap.animateCamera(yourLocation);
    }

    class FetchCoordinates extends AsyncTask<String, Void, Boolean> {

        ProgressDialog loading = new ProgressDialog(MapsActivity.this);

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            loading.setMessage("Loading");
            loading.show();
            super.onPostExecute(aBoolean);
        }

        @Override
        protected void onPreExecute() {
            loading.dismiss();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            if (Looper.myLooper() == null) {
                Looper.prepare();
            }
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(SERVER_URL));
                HttpResponse response = client.execute(request);
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    JSONObject object = jsonArray.getJSONObject(0);
                    latitude = Double.parseDouble(object.getString("lat"));
                    longitude = Double.parseDouble(object.getString("long"));
                    //gender = object.getString("gender");
                    //birthday = object.getString("birthday");

                }
                return false;
            } catch (Exception e) {
                Toast.makeText(MapsActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
            return false;
        }
    }
}
