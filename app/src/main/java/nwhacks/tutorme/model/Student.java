package nwhacks.tutorme.model;

import android.location.Location;

import com.firebase.client.Firebase;

/**
 * Created by Stephen on 2016-02-27.
 */
public class Student implements User {
    private String email;
    private String name;
    private Location location;

    public Student(String email, String name, Location location) {
        this.email = email;
        this.name = name;
        this.location = location;

    }

    public Location getLocation(){
        return this.location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void saveToFirebase(Firebase db, Student student)
    {
            Firebase studentStore = db.child("students").child(student.getName());
            studentStore.setValue(student);

    }
}
