package hu.ait.android.saaforumapp.data;

import java.io.Serializable;

public class Comment implements Serializable {
    private String uid;
    private String body;
    private String flair;
    private String author;

    public Comment() {
    }

    public Comment(String uid, String body, String author) {
        this.uid = uid;
        this.body = body;
        this.author = author;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFlair() {
        return flair;
    }

    public void setFlair(String flair) {
        this.flair = flair;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
