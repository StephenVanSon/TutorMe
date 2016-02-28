package nwhacks.tutorme.model;

import com.firebase.client.Firebase;

/**
 * Created by Stephen on 2016-02-27.
 */
public class Student {
    private String email;
    private String name;

    public Student(String email, String name)
    {
        this.email = email;
        this.name = name;


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

    public static void saveToFirebase(Firebase db, String username, Student student)
    {
        Firebase studentStore = db.child("students").child(username);
        studentStore.setValue(student);

    }
}
