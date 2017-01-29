package nwhacks.tutorme.Database;

import android.location.Location;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import nwhacks.tutorme.model.Tutor;

/**
 * Created by Stephen-PC on 1/28/2017.
 */

public class TutorConnection extends DbConnection{

    public static void PopulateTutorsFromDB(Location location){
        //query the database for tutors within a 5 kilometer radius
        GeoQuery dataQuery = getGeoFire().queryAtLocation(new GeoLocation(location.getLatitude(), location.getLongitude()), 5);


        dataQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, final GeoLocation geoLocation) {
                //plot things on the map as they come around
                Firebase fb = getRootReference().child("tutors").equalTo(key).getRef();
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
            }

            @Override
            public void onKeyMoved(String s, GeoLocation geoLocation) {
            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(FirebaseError firebaseError) {

            }
        });
    }
}
