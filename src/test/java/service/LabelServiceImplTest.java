package service;

import domain.LabelEntity;
import domain.PostEntity;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import repository.impl.JDBCLabelRepositoryImpl;
import service.impl.LabelServiceImpl;
import java.sql.SQLException;
import java.util.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static utils.OtherUtil.*;


public class LabelServiceImplTest {

    private JDBCLabelRepositoryImpl labelRepo = mock(JDBCLabelRepositoryImpl.class);

    LabelServiceImpl labelService = new LabelServiceImpl(labelRepo);
    List<LabelEntity> labels = getLabels();


    @Test
    public void findAll_Should_Return_List_Of_Labels() throws SQLException {
        when(labelRepo.findAll()).thenReturn(labels);
        assertEquals(labelService.findAll(), labels);
    }

    @Test
    public void findById_should_Return_One_Label() throws SQLException {

        when(labelRepo.findById(getLabel1().getId())).thenReturn(getLabel1());
        assertEquals(labelService.findById(getLabel1().getId()), getLabel1());
    }

    @Test
    public void save_Should_Add_New_Label() throws SQLException {

        List<LabelEntity> labels = getLabels();

        LabelEntity label = new LabelEntity(5555555555L, "label5");
        Answer<LabelEntity> ans = new Answer<LabelEntity>() {
            @Override
            public LabelEntity answer(InvocationOnMock invocationOnMock) throws Throwable {
                labels.add(invocationOnMock.getArgument(0));
                return null;
            }
        };

        when(labelRepo.save(label)).then(ans);
        labelService.save(label);
        assertEquals(5, labels.size());
    }

    @Test
    public void update_Should_Update_Item() throws SQLException {

        Answer<Void> ans = new Answer<>(){
            @Override
            public Void answer(InvocationOnMock invocationOnMock){
                LabelEntity label = invocationOnMock.getArgument(0);
                getLabel1().setName(label.getName());
                return null;
            }
        };

        assertEquals(getLabel1().getName(), "label1");
        when(labelRepo.update(any(LabelEntity.class))).then(ans);
        labelService.update(new LabelEntity("pickles"));
        assertEquals(getLabel1().getName(), "pickles");
    }

    @Test
    public void findAssociatedLabels_Should_Return_Associated_Labels() throws SQLException {

        PostEntity post = getPost();

        when(labelRepo.findAssociatedLabels(999763798L)).thenReturn(post.getLabels());
        List<LabelEntity> labels2 = labelService.findAssociatedLabels(post);
        assertEquals(labels, labels2);
    }


    @Test
    public void updatePostLabels_Should_Update_Labels() throws SQLException {

        PostEntity postInDatabase = getPostBefore();
        PostEntity postWithUpdate = getPostAfter();
        List<LabelEntity> labelsBefore = getLabelsBefore();
        List<LabelEntity> labelsAfter1 = getLabelsAfter1();
        List<LabelEntity> labelsAfter2 = new ArrayList<>();

        Answer<Void> ans1 = new Answer<>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                List<LabelEntity> newLabels = invocationOnMock.getArgument(0);
                List<LabelEntity> oldLabels = postInDatabase.getLabels();
                List<LabelEntity> x = new ArrayList<>();

                for(LabelEntity l : oldLabels){
                    if(newLabels.contains(l)) {
                        x.add(l);
                    }
                }
                postInDatabase.setLabels(x);
                return null;
            }
        };

        Answer<Void> ans2 = new Answer<>(){

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                PostEntity p = invocationOnMock.getArgument(0);
                List<LabelEntity> newLabels = p.getLabels();
                List<LabelEntity> oldLabels = postInDatabase.getLabels();
                for(LabelEntity l : newLabels){
                    if(!oldLabels.contains(l)) {
                        oldLabels.add(l);
                    }
                }
                postInDatabase.setLabels(oldLabels);
                return null;
            }
        };

        Answer<Void> ans3 = new Answer<>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                List<LabelEntity> labels = postInDatabase.getLabels();
                labels.clear();
                postInDatabase.setLabels(labels);
                return null;
            }
        };

        doAnswer(ans1).when(labelRepo).detachLabelsById(postWithUpdate.getLabels(), postWithUpdate.getId());
        doAnswer(ans2).when(labelRepo).attachNewLabelsToPost(postWithUpdate);
        doAnswer(ans3).when(labelRepo).detachAllLabels(anyLong());

        assertEquals(labelsBefore, postInDatabase.getLabels());

        labelService.updatePostLabels(postWithUpdate);
        assertEquals(labelsAfter1, postInDatabase.getLabels());

        postWithUpdate.setLabels(labelsAfter2);
        labelService.updatePostLabels(postWithUpdate);
        assertEquals(labelsAfter2, postInDatabase.getLabels());
    }
}