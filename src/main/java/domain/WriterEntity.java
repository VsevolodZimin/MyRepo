package domain;


import java.util.ArrayList;
import java.util.List;

public class WriterEntity {

    private long id;
    private String firstName;
    private String lastName;


    private List<PostEntity> posts;


    public WriterEntity(String firstName, String lastName) {
        this(null, firstName, lastName, null);
    }

    public WriterEntity(Long id, String firstName, String lastName){
        this(id, firstName, lastName, null);
    }

    public WriterEntity(String firstName, String lastName, List<PostEntity> posts) {
        this(null, firstName, lastName, posts);
    }

    public WriterEntity(Long id, String firstName, String lastName, List<PostEntity> posts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.posts = posts;
    }

    public long getId() {return id; }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<PostEntity> getPosts() {
        return posts;
    }

    public void setPosts(List<PostEntity> posts) {
        this.posts = posts;
    }
}

