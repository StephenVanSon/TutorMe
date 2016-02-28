package nwhacks.tutorme.activities;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
<<<<<<< HEAD

import com.firebase.client.Firebase;

import nwhacks.tutorme.activities.MapsActivity;
=======
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

>>>>>>> 0a9b76263e70031df6dbc16e06f4dbbabd4581b1
import nwhacks.tutorme.R;
import nwhacks.tutorme.model.Tutor;
import nwhacks.tutorme.utils.GPSTracker;

public class InitialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        Firebase.setAndroidContext(this);
        //firebase database
<<<<<<< HEAD
        Firebase rootReference = new Firebase("https://brilliant-inferno-9747.firebaseio.com/web/data");

        //hardcoded tutor
        Tutor tutor = new Tutor("John Smith", "johnsmith@johnsmith.com", new String[] {"math", "science"}, "$35/hr");
        Intent hello = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(hello);
=======
        final Firebase rootReference = new Firebase("https://brilliant-inferno-9747.firebaseio.com/web/data");


        GeoFire geoFire = new GeoFire(rootReference);
        GPSTracker gpsTracker = new GPSTracker(getApplicationContext());

        //get the users location
        Location loc = gpsTracker.getLocation(getApplicationContext());
        //hardcoded tutor
        Tutor tutor = new Tutor("John Smith", "johnsmith@johnsmith.com", new String[] {"math", "science"}, "$35/hr", loc);

        Tutor.saveToFirebase(geoFire, rootReference, tutor, loc);

        GeoQuery dataQuery = geoFire.queryAtLocation(new GeoLocation(loc.getLatitude(), loc.getLongitude()), 2);


        dataQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation geoLocation) {
                //plot things on the map as they come around
                Firebase fb = rootReference.child("tutors").equalTo(key).getRef();
                fb.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, Object> vals = (Map<String, Object>)dataSnapshot.getValue();
                        Collection<Object> thisData = vals.values();
                        for(Object data : thisData){
                            HashMap dataCasted = (HashMap) data;
                            String email = (String) dataCasted.get("email");
                            String fullName = (String) dataCasted.get("fullName");
                            String rate = (String) dataCasted.get("rate");
                            ArrayList<String> subjects = (ArrayList<String>) dataCasted.get("subjects");



                        }

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }

            @Override
            public void onKeyExited(String s) {
                String test = s;
            }

            @Override
            public void onKeyMoved(String s, GeoLocation geoLocation) {
                String test = s;
            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(FirebaseError firebaseError) {
                Log.e("FirebaseGeoqueryError","an error occurred: " + firebaseError);
            }
        });




>>>>>>> 0a9b76263e70031df6dbc16e06f4dbbabd4581b1

    }




    //to be called after signup/login
    public void switchToMaps(){
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);

    }


}
