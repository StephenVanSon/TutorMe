package nwhacks.tutorme.model;

<<<<<<< HEAD
import com.firebase.client.Firebase;
=======
import android.location.Location;

import com.firebase.client.Firebase;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;

import java.util.UUID;
>>>>>>> 0a9b76263e70031df6dbc16e06f4dbbabd4581b1

/**
 * Created by Stephen on 2016-02-27.
 */
public class Tutor {
    private String fullName;
    private String email;
    private String[] subjects;
    private String rate;
<<<<<<< HEAD

    public Tutor(String fullName, String email, String[] subjects, String rate)
=======
    private Location location;
    private UUID ID;

    public Tutor(String fullName, String email, String[] subjects, String rate, Location location)
>>>>>>> 0a9b76263e70031df6dbc16e06f4dbbabd4581b1
    {
        this.fullName = fullName;
        this.email = email;
        this.subjects = subjects;
        this.rate = rate;
<<<<<<< HEAD


=======
        this.location = location;
        this.ID = UUID.randomUUID();
>>>>>>> 0a9b76263e70031df6dbc16e06f4dbbabd4581b1
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getSubjects() {
        return subjects;
    }

    public void setSubjects(String[] subjects) {
        this.subjects = subjects;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }


<<<<<<< HEAD
    public static void saveToFirebase(Firebase db, String username, Tutor tutor){
        Firebase tutorStore = db.child("tutors").child(username);
        tutorStore.setValue(tutor);
    }
=======

    public static void saveToFirebase(GeoFire geoFire,Firebase db, Tutor tutor, Location location){
        Firebase tutorStore = db.child("tutors").child(tutor.getFullName());
        tutorStore.setValue(tutor);
        geoFire.setLocation(tutor.getFullName(), new GeoLocation(location.getLatitude(), location.getLongitude()));
    }



>>>>>>> 0a9b76263e70031df6dbc16e06f4dbbabd4581b1
}
