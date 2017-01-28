package nwhacks.tutorme.model;

import com.firebase.client.Firebase;
import android.location.Location;

import com.firebase.client.Firebase;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Stephen on 2016-02-27.
 */
public class Tutor implements User {
    private String fullName;
    private String email;
    private String[] subjects;
    private String rate;
    private Location location;
    private static HashSet<Tutor> tutorStore;

    public Tutor(String fullName, String email, String[] subjects, String rate, Location location)
    {
        this.fullName = fullName;
        this.email = email;
        this.subjects = subjects;
        this.rate = rate;
        this.location = location;

    }

    public static HashSet<Tutor> getTutorStore(){
        if(tutorStore == null)
            tutorStore = new HashSet<Tutor>();

        return tutorStore;

    }

    public static void addToTutorStore(Tutor tutor)
    {
        HashSet<Tutor> store = getTutorStore();
        if(store.contains(tutor))
            return;
        store.add(tutor);

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

    public Location getLocation(){

        return this.location;
    }


    public static void saveToFirebase(GeoFire geoFire,Firebase db, Tutor tutor, Location location){
        if(location != null) {
            Firebase tutorStore = db.child("tutors").child(tutor.getFullName());
            tutorStore.setValue(tutor);
            geoFire.setLocation(tutor.getFullName(), new GeoLocation(location.getLatitude(), location.getLongitude()));
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tutor tutor = (Tutor) o;

        if (!getFullName().equals(tutor.getFullName())) return false;
        if (!getEmail().equals(tutor.getEmail())) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(getSubjects(), tutor.getSubjects())) return false;
        return getRate().equals(tutor.getRate());

    }

    @Override
    public int hashCode() {
        int result = getFullName().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + Arrays.hashCode(getSubjects());
        result = 31 * result + getRate().hashCode();
        return result;
    }
}
