package domain;

import java.sql.Date;
import java.util.List;

public class PostEntity {

    public PostEntity(Long id, String content, Date created, Date updated, List<LabelEntity> labels, Long writerId) {
        this.content = content;
        this.created = created;
        this.updated = updated;
        this.labels = labels;
        this.id = id;
        this.writerId = writerId;
    }

    public PostEntity(String content, Date created, Date updated, List<LabelEntity> labels) {
        this.content = content;
        this.created = created;
        this.updated = updated;
        this.labels = labels;
        this.id = Math.abs(content.hashCode() + created.hashCode() + updated.hashCode() + labels.hashCode() + (long) (Math.random() * 10000000));
    }


    private String content;
    private Date created;
    private Date updated;
    private List<LabelEntity> labels;
    private Long id;
    private Long writerId;


    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    public List<LabelEntity> getLabels() {
        return labels;
    }

    public Long getWriterId(){return writerId;}

    @Override
    public String toString(){
        return content;
    }
}
