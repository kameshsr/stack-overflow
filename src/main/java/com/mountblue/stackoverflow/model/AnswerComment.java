package com.mountblue.stackoverflow.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "answer_comment")
public class AnswerComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String content;

    private String userName;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "answer_id", nullable = true)
    private Answer answer;

    public AnswerComment() {
    }

    public AnswerComment(int id, String content, String userName, LocalDateTime createdAt, Answer answer) {
        this.id = id;
        this.content = content;
        this.userName = userName;
        this.createdAt = createdAt;
        this.answer = answer;
    }

    public AnswerComment(String content, String userName, LocalDateTime createdAt, Answer answer) {
        this.content = content;
        this.userName = userName;
        this.createdAt = createdAt;
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

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "QuestionComment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", userName='" + userName + '\'' +
                ", createdAt=" + createdAt +
                ", answer=" + answer +
                '}';
    }
}
