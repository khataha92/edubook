package DataModels;

import android.support.v7.widget.RecyclerView;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import Enums.HolderType;
import Interfaces.PostFactory;

public class Post implements PostFactory{

    int id;

    @SerializedName("created_at")
    String createdDate;

    @SerializedName("likes_count")
    int likeCount;

    @SerializedName("comments_count")
    int commentCount;

    List<Comment> comments;

    int liked;

    private transient List<PostFactory> postList;

    private transient RecyclerView recyclerView;

    public void setPostList(List<PostFactory> postList) {

        this.postList = postList;

    }

    public List<PostFactory> getPostList() {

        return postList;

    }

    public List<Comment> getComments() {

        return comments;

    }

    public void setRecyclerView(RecyclerView recyclerView) {

        this.recyclerView = recyclerView;

    }

    public RecyclerView getRecyclerView() {

        return recyclerView;

    }

    String age;

    @SerializedName("notes")
    Note note;

    Event event;

    List<Attachment> attachments = new ArrayList<>();

    User creator;

    Poll poll;

    @SerializedName("assignments")
    Assignment assignment;

    @SerializedName("first_comment")
    Comment firstComment;

    public void setPoll(Poll poll) {

        this.poll = poll;

    }

    public List<Attachment> getAttachments() {

        return attachments;

    }

    public Poll getPoll() {

        return poll;

    }

    public int getId() {

        return id;

    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Comment getFirstComment() {
        return firstComment;
    }

    public void setFirstComment(Comment firstComment) {
        this.firstComment = firstComment;
    }

    @Override
    public GenericViewHolderDataContainer getPostFactory() {

        GenericViewHolderDataContainer container = new GenericViewHolderDataContainer();

        if(poll != null){

            container.setType(HolderType.POLL);

        }

        else if(note != null){

            container.setType(HolderType.NOTE);

        }

        else if(assignment != null){

            container.setType(HolderType.ASSIGNMENT);

        }

        else{

            container.setType(HolderType.EVENT);

        }

        container.setValue(this);

        return container;

    }
}
