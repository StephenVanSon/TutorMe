package nwhacks.tutorme.activities;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashSet;

import nwhacks.tutorme.R;
import nwhacks.tutorme.model.Tutor;
import nwhacks.tutorme.utils.GPSTracker;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    Location loc;

    MapFragment mMapFragment = MapFragment.newInstance();
    FragmentTransaction fragmentTransaction =
            getFragmentManager().beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Bundle extras = getIntent().getExtras();

        double lat = extras.getDouble("locLat");
        double locLong = extras.getDouble("locLong");
        loc = new Location("");
        loc.setLatitude(lat);
        loc.setLongitude(locLong);

    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng coords = new LatLng(loc.getLatitude(), loc.getLongitude());
        CameraUpdate myLoc = CameraUpdateFactory.newLatLngZoom(coords, 13);
        map.moveCamera(myLoc);


        GoogleMap.InfoWindowAdapter customAdapter = new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                LinearLayout info = new LinearLayout(getApplicationContext());
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(getApplicationContext());
                title.setTextColor(Color.BLACK);

                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(getApplicationContext());
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;

            }
        };

        map.setInfoWindowAdapter(customAdapter);

        HashSet<Tutor> allTutors = Tutor.getTutorStore();
        for(Tutor tutor : allTutors){
            Location loc = tutor.getLocation();

            map.addMarker(new MarkerOptions()
                            .position(new LatLng(loc.getLatitude(), loc.getLongitude()))
                            .title(tutor.getFullName())
                            .snippet(tutor.getEmail() + "\n" +
                                            "Subjects: "
                                            + tutor.getSubjects()[0]
                                            + ", " + tutor.getSubjects()[1]
                                            + ", " + tutor.getSubjects()[2]
                                            + "\n"
                                            + "Rate: " + "$" + tutor.getRate() + "/hr"
                            )
            );


        }




    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://nwhacks.tutorme/http/host/path")
        );
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://nwhacks.tutorme/http/host/path")
        );
    }
}
