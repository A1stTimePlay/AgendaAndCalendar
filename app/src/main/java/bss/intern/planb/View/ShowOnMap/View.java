package bss.intern.planb.View.ShowOnMap;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import bss.intern.planb.R;

public class View extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latitude;
    private double longitude;
    private String name;
    private String note;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private String address;
    private int color;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("latitude", 0);
        longitude = intent.getDoubleExtra("longitude", 0);
        name = intent.getStringExtra("title");
        note = intent.getStringExtra("note");
        startDate = intent.getStringExtra("start date");
        startTime = intent.getStringExtra("start time");
        endDate = intent.getStringExtra("end date");
        endTime = intent.getStringExtra("end time");
        address = intent.getStringExtra("address");
        color = intent.getIntExtra("color", -1);
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
        LatLng location = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(location).title(name)
                .snippet(note + "\n" + startDate + " - " + startTime + "\n" + endDate + " - " + endTime + "\n" + address)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bss));
        mMap.addMarker(markerOptions);
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public android.view.View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public android.view.View getInfoContents(Marker marker) {
                LinearLayout info = new LinearLayout(View.this);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(View.this);
                title.setTextColor(color);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(View.this);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}
