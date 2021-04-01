package com.mountblue.stackoverflow.model;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length=10485760)
    private String content;

    private LocalDateTime createdAt;

    private String userName;

    private String tag;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "question_id", nullable = true)
    private Question question;

    public Answer() {
    }

    public Answer(int id, String content, LocalDateTime createdAt, String userName, Question question,String tag) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.userName = userName;
        this.question = question;
        this.tag=tag;
    }

    public Answer(String content, LocalDateTime createdAt, String userName, Question question,String tag) {
        this.content = content;
        this.createdAt = createdAt;
        this.userName = userName;
        this.question = question;
        this.tag=tag;
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
