package domain;

public class LabelEntity {

    private Long id;
    private String name;

    public LabelEntity(String name){
        this(null, name);
    }

    public LabelEntity(Long id , String name){
        this.name = name;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return name;
    }


}