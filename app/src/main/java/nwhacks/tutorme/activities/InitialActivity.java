package nwhacks.tutorme.activities;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

import nwhacks.tutorme.activities.MapsActivity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
import nwhacks.tutorme.R;
import nwhacks.tutorme.model.Student;
import nwhacks.tutorme.model.Tutor;
import nwhacks.tutorme.utils.GPSTracker;

public class InitialActivity extends AppCompatActivity {


    Firebase rootReference;
    GeoFire geoFire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Firebase.setAndroidContext(this);
        rootReference = new Firebase("https://brilliant-inferno-9747.firebaseio.com/web/data");
        geoFire = new GeoFire(rootReference);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        Firebase.setAndroidContext(this);
        //firebase database
        //final Firebase rootReference = new Firebase("https://brilliant-inferno-9747.firebaseio.com/web/data");


        //GeoFire geoFire = new GeoFire(rootReference);
        GPSTracker gpsTracker = new GPSTracker(getApplicationContext());

        //get the users location
        Location loc = gpsTracker.getLocation(getApplicationContext());

        if(loc != null) {
            //query the database for tutors within a 5 kilometer radius
            GeoQuery dataQuery = geoFire.queryAtLocation(new GeoLocation(loc.getLatitude(), loc.getLongitude()), 5);


            dataQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
                @Override
                public void onKeyEntered(String key, final GeoLocation geoLocation) {
                    //plot things on the map as they come around
                    Firebase fb = rootReference.child("tutors").equalTo(key).getRef();
                    fb.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Map<String, Object> vals = (Map<String, Object>) dataSnapshot.getValue();
                            Collection<Object> thisData = vals.values();
                            for (Object data : thisData) {
                                HashMap dataCasted = (HashMap) data;
                                String email = (String) dataCasted.get("email");
                                String fullName = (String) dataCasted.get("fullName");
                                String rate = (String) dataCasted.get("rate");

                                ArrayList<String> subjects = (ArrayList<String>) dataCasted.get("subjects");
                                String[] subjectsArr = new String[subjects.size()];
                                subjectsArr = subjects.toArray(subjectsArr);

                                HashMap<String, Object> locationMap = (HashMap<String, Object>) dataCasted.get("location");

                                Tutor tutor;
                                if (locationMap != null) {
                                    double latitude = (double) locationMap.get("latitude");
                                    double longitude = (double) locationMap.get("longitude");
                                    Location tutorLoc = new Location("");
                                    tutorLoc.setLatitude(latitude);
                                    tutorLoc.setLongitude(longitude);

                                    tutor = new Tutor(fullName, email, subjectsArr, rate, tutorLoc);
                                } else {
                                    Location tutorLoc = new Location("");
                                    tutorLoc.setLongitude(geoLocation.longitude);
                                    tutorLoc.setLatitude(geoLocation.latitude);
                                    tutor = new Tutor(fullName, email, subjectsArr, rate, tutorLoc);

                                }

                                Tutor.addToTutorStore(tutor);

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
                    Log.e("FirebaseGeoqueryError", "an error occurred: " + firebaseError);
                }
            });


        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please turn on location to access TutorMe", Toast.LENGTH_LONG);


        }
    }



    //switch anyway
    public void onLoginClick(View view)
    {
        EditText passwordField = (EditText) findViewById(R.id.initial_password);
        EditText emailField = (EditText) findViewById(R.id.initial_email);
        rootReference.authWithPassword(emailField.getText().toString(), passwordField.getText().toString(), new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
               Toast.makeText(getApplicationContext(), "Error logging in", Toast.LENGTH_LONG);
            }
        });
    }

    public void onSignUpAsStudentClick(View view)
    {
        Intent intent = new Intent(this, StudentActivity.class);
        startActivity(intent);

    }

    public void onSignUpAsTutorClick(View view)
    {
        Intent intent = new Intent(this, TutorActivity.class);
        startActivity(intent);
    }



}
