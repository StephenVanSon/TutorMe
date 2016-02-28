package nwhacks.tutorme.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

import nwhacks.tutorme.R;
import nwhacks.tutorme.model.Student;

public class StudentActivity extends AppCompatActivity {
    Firebase firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        firebase = new Firebase("https://brilliant-inferno-9747.firebaseio.com/web/data");
        setContentView(R.layout.activity_student);
    }


    public void onGoToMapClick(View view){
        EditText nameField = (EditText) findViewById(R.id.student_name);
        EditText emailField =  (EditText) findViewById(R.id.student_email);
        EditText passwordField = (EditText) findViewById(R.id.student_password);

        String name = nameField.getText().toString();
        String email = emailField.getText().toString();
        Student student = new Student(email, name);
        Student.saveToFirebase(firebase, student);



        firebase.createUser(email, passwordField.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "Error signing up, please try again.", Toast.LENGTH_LONG);
            }
        });





    }
}
