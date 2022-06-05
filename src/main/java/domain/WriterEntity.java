package domain;


import java.util.ArrayList;
import java.util.List;

public class WriterEntity {

    public WriterEntity(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = new ArrayList<>();
        this.id = (long) Math.abs(firstName.hashCode() + lastName.hashCode() + posts.hashCode() + Math.random());
    }


    public WriterEntity(String firstName, String lastName, List<PostEntity> posts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
        this.id = (long) Math.abs(firstName.hashCode() + lastName.hashCode() + posts.hashCode() + Math.random());
    }


    public WriterEntity(Long id, String firstName, String lastName,  List<PostEntity> posts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.posts = posts;
    }

    private long id;
    private String firstName;
    private String lastName;
    private List<PostEntity> posts;


    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<PostEntity> getPosts() {
        return posts;
    }
}

