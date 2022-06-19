package domain;

import java.sql.Date;
import java.util.List;

public class PostEntity {

    private Long id;
    private Date created;
    private Date updated;
    private String content;
    private Long writerId;
    private List<LabelEntity> labels;


    public PostEntity(Long id, String content, Date created, Date updated, List<LabelEntity> labels, Long writerId) {
        this.content = content;
        this.created = created;
        this.updated = updated;
        this.labels = labels;
        this.id = id;
        this.writerId = writerId;
    }

    public PostEntity(String content, Date created, Date updated, List<LabelEntity> labels, Long writerId) {
        this(null, content, created, updated, labels, writerId);
    }

    public PostEntity(Long id, String content, Date created, Date updated) {
        this(id, content, created, updated, null, null);
    }

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

    public void setLabels(List<LabelEntity> labels) {
        this.labels = labels;
    }

    public List<LabelEntity> getLabels() {
        return labels;
    }

    public void setWriterId(Long writerId) {
        this.writerId = writerId;
    }

    public Long getWriterId(){
        return writerId;
    }


    @Override
    public String toString(){
        return content;
    }
}
