package bg.jug.microprofile.hol.authors;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import java.io.Serializable;
import java.io.StringReader;

/**
 * Created by Dmitry Alexandrov on 27.02.18.
 */
public class Author implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String bio;
    private int salary;

    public Author() {
        // empty constructor
    }

    public Author(String firstName, String lastName, String email, String bio, int salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.bio = bio
        ;
        this.salary = salary;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;

        Author author = (Author) o;

        if (bio != author.bio) return false;
        if (salary != author.salary) return false;
        if (!firstName.equals(author.firstName)) return false;
        if (!lastName.equals(author.lastName)) return false;
        return email.equals(author.email);

    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + salary;
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", bio=" + bio  + '\'' +
                ", salary=" + salary +
                '}';
    }

    public JsonObject toJson(){
        JsonObjectBuilder result = Json.createObjectBuilder();
        result.add("lastName",getLastName())
                .add("firstName",getFirstName())
                .add("email",getEmail())
                .add("salary",getSalary())
                .add("bio",getBio());
        return result.build();
    }


    public static Author fromJson(String json){
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject authorObject = reader.readObject();
        reader.close();
        return new Author(authorObject.getString("firstName"),authorObject.getString("lastName"),authorObject.getString("email"),authorObject.getString("bio"),Integer.valueOf(authorObject.getString("salary")));
    }
}
