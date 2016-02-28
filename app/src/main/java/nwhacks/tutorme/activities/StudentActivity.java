package nwhacks.tutorme.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.Firebase;

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

        String name = nameField.getText().toString();
        String email = emailField.getText().toString();

        Student student = new Student(email, name);
        Student.saveToFirebase(firebase, student);

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);



    }
}
