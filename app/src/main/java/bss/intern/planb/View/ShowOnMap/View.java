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

import java.util.List;

import bss.intern.planb.Database.AgendaDatabase;
import bss.intern.planb.Database.AgendaEvent;
import bss.intern.planb.Presenter.ShowOnMap.Presenter;
import bss.intern.planb.R;

public class View extends FragmentActivity implements OnMapReadyCallback, IView {

    private GoogleMap mMap;
    private double latitude;
    private double longitude;
    private int flag;
    Presenter presenter;

    private List<AgendaEvent> agendaEventList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("latitude", 0);
        longitude = intent.getDoubleExtra("longitude", 0);
        flag = intent.getIntExtra("FLAG", -1);

        AgendaDatabase db = AgendaDatabase.getINSTANCE(this);
        presenter = new Presenter(this, db.agendaEventDao());
        presenter.loadAll();

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

        for (AgendaEvent agendaEvent : agendaEventList) {
            LatLng location = new LatLng(agendaEvent.getLatitude(), agendaEvent.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(location).title(agendaEvent.getName())
                    .snippet(agendaEvent.getNote() + "\n" + agendaEvent.startDateToString() + "\n" + agendaEvent.endDateToString() + "\n" + agendaEvent.getLocation())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bss));
            mMap.addMarker(markerOptions);
        }

        if (flag != bss.intern.planb.View.Home.View.FLAG_EDIT) {
            LatLng location = new LatLng(latitude, longitude);
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(location)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bss));
            mMap.addMarker(markerOptions);
        }

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
                title.setTextColor(Color.BLACK);
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

        LatLng currentLocation = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
    }

    @Override
    public void loadPlaceList(List<AgendaEvent> agendaEventList) {
        this.agendaEventList = agendaEventList;
    }
}
