package com.mountblue.stackoverflow.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "question_comment")
public class QuestionComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String content;

    private String userName;

    @CreationTimestamp
    private LocalDateTime createdAt;



    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "question_id", nullable = true)
    private Question question;

    public QuestionComment() {
    }

    public QuestionComment(int id, String content, String userName, LocalDateTime createdAt, Question question) {
        this.id = id;
        this.content = content;
        this.userName = userName;
        this.createdAt = createdAt;
        this.question = question;
    }

    public QuestionComment(String content, String userName, LocalDateTime createdAt, Question question) {
        this.content = content;
        this.userName = userName;
        this.createdAt = createdAt;
        this.question = question;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "QuestionComment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", userName='" + userName + '\'' +
                ", createdAt=" + createdAt +
                ", question=" + question +
                '}';
    }
}
