package nwhacks.tutorme.activities;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.geofire.GeoFire;

import java.util.Map;

import nwhacks.tutorme.R;
import nwhacks.tutorme.model.Tutor;
import nwhacks.tutorme.utils.GPSTracker;

public class TutorActivity extends AppCompatActivity {

    Firebase firebase;
    GeoFire geofire;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Firebase.setAndroidContext(this);
        firebase = new Firebase("https://brilliant-inferno-9747.firebaseio.com/web/data");
        geofire = new GeoFire(firebase);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);
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
        GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
        Location location = gpsTracker.location;

        Tutor tutor = new Tutor(name, email, subjects, rate, location);
        Tutor.saveToFirebase(geofire, firebase, tutor, location);

        firebase.createUser(email, passwordField.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
               Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
               startActivity(intent);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "There was an error signing up, please try again.", Toast.LENGTH_LONG);
            }
        });






    }
}
