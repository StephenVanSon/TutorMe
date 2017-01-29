package nwhacks.tutormeupdated.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

import nwhacks.tutormeupdated.Database.DbConnection;
import nwhacks.tutormeupdated.Database.TutorConnection;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.FirebaseError;
import com.firebase.geofire.GeoFire;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import nwhacks.tutormeupdated.R;
import nwhacks.tutormeupdated.model.Tutor;

public class InitialActivity extends AppCompatActivity  implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {


    Firebase rootReference;
    Location mLastLocation;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Firebase.setAndroidContext(this);

        DbConnection.initDBConnection();
        rootReference = DbConnection.getRootReference();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        Firebase.setAndroidContext(this);





        if(mGoogleApiClient == null){
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();



        }

        //attempt to getlast location if its null
        if(mLastLocation == null){
            if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

                ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  }, 1);
            }

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        }


        if(mLastLocation != null && !Tutor.isTutorStorePopulated()) {
            TutorConnection.PopulateTutorsFromDB(mLastLocation);
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
                intent.putExtra("locLat",mLastLocation.getLatitude());
                intent.putExtra("locLong", mLastLocation.getLongitude());
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

    @Override
    public void onConnected(Bundle connectionHint){
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  }, 1);
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(mLastLocation != null)
            TutorConnection.PopulateTutorsFromDB(mLastLocation);
    }


    @Override
    public void onConnectionSuspended(int suspended){
        int test = 5;
    }

    @Override
    public void onConnectionFailed(ConnectionResult result){
        int tesst =4;
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



}
