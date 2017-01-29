package nwhacks.tutorme.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.geofire.GeoFire;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;

import java.util.Map;

import nwhacks.tutorme.Database.TutorConnection;
import nwhacks.tutorme.R;
import nwhacks.tutorme.model.Tutor;
import nwhacks.tutorme.utils.GPSTracker;

public class TutorActivity extends AppCompatActivity implements ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    Firebase firebase;
    GeoFire geofire;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Firebase.setAndroidContext(this);
        firebase = new Firebase("https://brilliant-inferno-9747.firebaseio.com/web/data");
        geofire = new GeoFire(firebase);
        if(mGoogleApiClient == null){
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);
    }

    @Override
    protected void onStart(){
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop(){
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    public void onGoToMapsClick(View view)
    {
        EditText usernameField = (EditText) findViewById(R.id.tutor_name);
        EditText emailField = (EditText) findViewById(R.id.tutor_email);
        EditText subject1Field = (EditText) findViewById(R.id.tutor_subject1);
        EditText subject2Field = (EditText) findViewById(R.id.tutor_subject2);
        EditText subject3Field = (EditText) findViewById(R.id.tutor_subject3);
        EditText rateField = (EditText) findViewById(R.id.tutor_rate);
        EditText passwordField = (EditText) findViewById(R.id.tutor_password);

        String name = usernameField.getText().toString();
        String email = emailField.getText().toString();
        String[] subjects = new String[]
         {
                 subject1Field.getText().toString(),
                 subject2Field.getText().toString(),
                 subject3Field.getText().toString()

        };


        String rate = rateField.getText().toString();


        //attempt to getlast location if its null
        if(mLastLocation == null){
            if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

                ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  }, 1);
            }

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        }

        if(mLastLocation == null) return;



        final Tutor tutor = new Tutor(name, email, subjects, rate, mLastLocation);
        Tutor.saveToFirebase(geofire, firebase, tutor, mLastLocation);

        firebase.createUser(email, passwordField.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
               Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
               intent.putExtra("locLat", tutor.getLocation().getLatitude());
               intent.putExtra("locLong", tutor.getLocation().getLongitude());


               startActivity(intent);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), firebaseError.getMessage(), Toast.LENGTH_LONG);
            }
        });

    }

    @Override
    public void onConnected(Bundle connectionHint){
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  }, 1);
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }


    @Override
    public void onConnectionSuspended(int suspended){

    }

    @Override
    public void onConnectionFailed(ConnectionResult result){

    }
}
