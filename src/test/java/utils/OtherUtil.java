package utils;


import domain.LabelEntity;
import domain.PostEntity;
import domain.WriterEntity;

import java.util.ArrayList;
import java.util.List;

public class OtherUtil {

    private static LabelEntity label1 = new LabelEntity(1111111111L, "label1");
    private static LabelEntity label2 = new LabelEntity(2222222222L, "label2");
    private static LabelEntity label3 = new LabelEntity(3333333333L, "label3");
    private static LabelEntity label4 = new LabelEntity(4444444444L, "label4");

    public static List<LabelEntity> getLabels(){
        List<LabelEntity> labels = new ArrayList<>();
            labels.add(label1);
            labels.add(label2);
            labels.add(label3);
            labels.add(label4);

        return labels;
    }

    static public List<LabelEntity> getLabelsBefore(){
        List<LabelEntity> labelsBefore = new ArrayList<>();
        labelsBefore.add(label1);
        labelsBefore.add(label2);
        labelsBefore.add(label4);
        return labelsBefore;
    }

    public static List<LabelEntity> getLabelsAfter1(){
        List<LabelEntity> labelsAfter1 = new ArrayList<>();
        labelsAfter1.add(label1);
        labelsAfter1.add(label4);
        labelsAfter1.add(label3);
        return labelsAfter1;
    }

    private static WriterEntity writer = new WriterEntity(11111111L,"Володя", "Зеленский");

    public static PostEntity getPost() {
        return post;
    }

    public static void setPost(PostEntity post) {
        OtherUtil.post = post;
    }

    private static PostEntity post = new PostEntity(999763798L,"о рыбках", DateUtil.getDate(), DateUtil.getDate(), getLabels(), writer.getId());
    private static PostEntity postBefore = new PostEntity(999763798L,"о рыбках", DateUtil.getDate(), DateUtil.getDate(), getLabelsBefore(), writer.getId());
    private static PostEntity postAfter = new PostEntity(999763798L,"о рыбках", DateUtil.getDate(), DateUtil.getDate(), getLabelsAfter1(), writer.getId());



    public static LabelEntity getLabel1() {
        return label1;
    }

    public static void setLabel1(LabelEntity label1) {
        OtherUtil.label1 = label1;
    }

    public static LabelEntity getLabel2() {
        return label2;
    }

    public static void setLabel2(LabelEntity label2) {
        OtherUtil.label2 = label2;
    }

    public static LabelEntity getLabel3() {
        return label3;
    }

    public static void setLabel3(LabelEntity label3) {
        OtherUtil.label3 = label3;
    }

    public static LabelEntity getLabel4() {
        return label4;
    }

    public static void setLabel4(LabelEntity label4) {
        OtherUtil.label4 = label4;
    }


    public static WriterEntity getWriter() {
        return writer;
    }

    public static void setWriter(WriterEntity writer) {
        OtherUtil.writer = writer;
    }

    public static PostEntity getPostBefore() {
        return postBefore;
    }

    public static void setPostBefore(PostEntity postBefore) {
        OtherUtil.postBefore = postBefore;
    }

    public static PostEntity getPostAfter() {
        return postAfter;
    }

    public static void setPostAfter(PostEntity postAfter) {
        OtherUtil.postAfter = postAfter;
    }
}
