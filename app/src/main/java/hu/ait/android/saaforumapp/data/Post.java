package hu.ait.android.saaforumapp.data;

import com.google.android.youtube.player.YouTubePlayerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Post implements Serializable {
    private String uid;
    private String author;
    private String title;
    private String body;
    private String imgUrl;
    private List<Comment> comments = new ArrayList<Comment>();

    public List<Comment> getComments() {
        return comments;
    }


    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Post(String uid, String author, String title, String body, String imgUrl) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
        this.imgUrl = imgUrl;
    }

    public Post() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
