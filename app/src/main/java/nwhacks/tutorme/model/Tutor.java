package nwhacks.tutorme.model;

import android.location.Location;

import com.firebase.client.Firebase;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;

import java.util.UUID;

/**
 * Created by Stephen on 2016-02-27.
 */
public class Tutor {
    private String fullName;
    private String email;
    private String[] subjects;
    private String rate;
    private Location location;
    private UUID ID;

    public Tutor(String fullName, String email, String[] subjects, String rate, Location location)
    {
        this.fullName = fullName;
        this.email = email;
        this.subjects = subjects;
        this.rate = rate;
        this.location = location;
        this.ID = UUID.randomUUID();
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



    public static void saveToFirebase(GeoFire geoFire,Firebase db, Tutor tutor, Location location){
        Firebase tutorStore = db.child("tutors").child(tutor.getFullName());
        tutorStore.setValue(tutor);
        geoFire.setLocation(tutor.getFullName(), new GeoLocation(location.getLatitude(), location.getLongitude()));
    }



}
