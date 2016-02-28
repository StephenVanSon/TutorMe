package nwhacks.tutorme.model;

import com.firebase.client.Firebase;

<<<<<<< HEAD
=======
import java.util.UUID;

>>>>>>> 0a9b76263e70031df6dbc16e06f4dbbabd4581b1
/**
 * Created by Stephen on 2016-02-27.
 */
public class Student {
    private String email;
    private String name;
<<<<<<< HEAD
=======
    private UUID ID;
>>>>>>> 0a9b76263e70031df6dbc16e06f4dbbabd4581b1

    public Student(String email, String name)
    {
        this.email = email;
        this.name = name;

<<<<<<< HEAD

=======
>>>>>>> 0a9b76263e70031df6dbc16e06f4dbbabd4581b1
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

<<<<<<< HEAD
    public static void saveToFirebase(Firebase db, String username, Student student)
    {
        Firebase studentStore = db.child("students").child(username);
=======



    public static void saveToFirebase(Firebase db, Student student)
    {
        Firebase studentStore = db.child("students").child(student.getName());
>>>>>>> 0a9b76263e70031df6dbc16e06f4dbbabd4581b1
        studentStore.setValue(student);

    }
}
