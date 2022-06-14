package domain;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

public class PostEntity {

    private Long id;
    private Date created;
    private Date updated;
    private String content;
    //  private Long writerId;              //Было
    private WriterEntity writerEntity;      //Стало
    private List<LabelEntity> labels;


    public PostEntity(Long id, String content, Date created, Date updated, List<LabelEntity> labels, WriterEntity writer) {
        this.content = content;
        this.created = created;
        this.updated = updated;
        this.labels = labels;
        this.id = id;
        this.writerEntity = writer;
    }

    public PostEntity(String content, Date created, Date updated, List<LabelEntity> labels, WriterEntity writer) {
        this(null, content, created, updated, labels, writer);
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

    public void setWriterEntity(WriterEntity writerEntity) {
        this.writerEntity = writerEntity;
    }

    public Long getWriterId(){return writerEntity.getId();}


    @Override
    public String toString(){
        return content;
    }
}
