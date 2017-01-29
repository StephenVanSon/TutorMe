package nwhacks.tutormeupdated.activities;

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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.Map;

import nwhacks.tutormeupdated.R;
import nwhacks.tutormeupdated.model.Student;

public class StudentActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    Firebase firebase;
    Location mLastLocation;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        firebase = new Firebase("https://brilliant-inferno-9747.firebaseio.com/web/data");
        if(mGoogleApiClient == null){
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

        }

        setContentView(R.layout.activity_student);
    }


    public void onGoToMapClick(View view){
        EditText nameField = (EditText) findViewById(R.id.student_name);
        EditText emailField =  (EditText) findViewById(R.id.student_email);
        EditText passwordField = (EditText) findViewById(R.id.student_password);

        String name = nameField.getText().toString();
        String email = emailField.getText().toString();

        //attempt to getlast location if its null
        if(mLastLocation == null){
            if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

                ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  }, 1);
            }

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        }

        if(mLastLocation == null) return;
        final Student student = new Student(email, name, mLastLocation);
        Student.saveToFirebase(firebase, student);



        firebase.createUser(email, passwordField.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("locLat", student.getLocation().getLatitude());
                intent.putExtra("locLong", student.getLocation().getLongitude());

                startActivity(intent);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "Error signing up, please try again.", Toast.LENGTH_LONG);
            }
        });

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


    @Override
    public void onConnected(Bundle connectionHint){
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  }, 1);
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }


    @Override
    public void onConnectionSuspended(int suspended){
        int test = 5;
    }

    @Override
    public void onConnectionFailed(ConnectionResult result){

        int test = 5;
    }
}
