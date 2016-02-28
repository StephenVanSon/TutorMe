package nwhacks.tutorme.activities;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.client.Firebase;

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
        Firebase rootReference = new Firebase("https://brilliant-inferno-9747.firebaseio.com/web/data");

        //hardcoded tutor
        Tutor tutor = new Tutor("John Smith", "johnsmith@johnsmith.com", new String[] {"math", "science"}, "$35/hr");

        GPSTracker gpsTracker = new GPSTracker(getApplicationContext());

        //get the users location
        Location loc = gpsTracker.getLocation(getApplicationContext());



    }




    //to be called after signup/login
    public void switchToMaps(){
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);

    }


}
