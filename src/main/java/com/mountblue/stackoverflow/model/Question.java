package com.mountblue.stackoverflow.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
public class Question {

    @Id
    private int id;

    @Column(length=10485760)
    private String content;

    private LocalDateTime createdAt;

    private String userName;

    private String title;

    private String tags;

    @OneToMany(mappedBy = "question",
            cascade = {CascadeType.REMOVE})

    List<QuestionComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "question",
            cascade = {CascadeType.REMOVE})

    List<Answer> answers = new ArrayList<>();

    public Question() {
    }

    public Question(int id, String content, LocalDateTime createdAt, String userName) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.userName = userName;
    }

    public Question(String content, LocalDateTime createdAt, String userName) {
        this.content = content;
        this.createdAt = createdAt;
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<QuestionComment> getComments() {
        return comments;
    }

    public void setComments(List<QuestionComment> comments) {
        this.comments = comments;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", userName='" + userName + '\'' +
                ", comments=" + comments +
                '}';
    }
}
