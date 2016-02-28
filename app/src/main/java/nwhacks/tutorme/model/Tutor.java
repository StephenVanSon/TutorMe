package nwhacks.tutorme.model;

import com.firebase.client.Firebase;

/**
 * Created by Stephen on 2016-02-27.
 */
public class Tutor {
    private String fullName;
    private String email;
    private String[] subjects;
    private String rate;

    public Tutor(String fullName, String email, String[] subjects, String rate)
    {
        this.fullName = fullName;
        this.email = email;
        this.subjects = subjects;
        this.rate = rate;


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


    public static void saveToFirebase(Firebase db, String username, Tutor tutor){
        Firebase tutorStore = db.child("tutors").child(username);
        tutorStore.setValue(tutor);
    }
}
