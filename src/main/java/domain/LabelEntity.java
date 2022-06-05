package domain;

public class LabelEntity {

    public LabelEntity(String name){
        this.name = name;
        this.id = Math.abs((long) name.hashCode());
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

    private Long id;
    private String name;

    @Override
    public String toString(){
        return name;
    }


}